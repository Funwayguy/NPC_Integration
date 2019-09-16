package bq_npc_integration.tasks;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import bq_npc_integration.client.gui.tasks.PanelTaskQuest;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.PlayerQuestController;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.*;

public class TaskNpcQuest implements ITaskTickable
{
	private final Set<UUID> completeUsers = new TreeSet<>();
	
	public int npcQuestID = -1;
	
	@Override
	public ResourceLocation getFactoryID()
	{
		return FactoryTaskQuest.INSTANCE.getRegistryName();
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.quest";
	}
	
	@Override
	public boolean isComplete(UUID uuid)
	{
		return completeUsers.contains(uuid);
	}
	
	@Override
	public void setComplete(UUID uuid)
	{
		completeUsers.add(uuid);
	}

	@Override
	public void resetUser(@Nullable UUID uuid)
	{
	    if(uuid == null)
        {
            completeUsers.clear();
        } else
        {
            completeUsers.remove(uuid);
        }
	}
	
	@Override
	public void tickTask(DBEntry<IQuest> quest, ParticipantInfo pInfo)
	{
		if(pInfo.PLAYER.ticksExisted%60 != 0 || QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE))
		{
			return;
		}
		
		detect(pInfo, quest);
	}
	
	@Override
	public void detect(ParticipantInfo pInfo, DBEntry<IQuest> quest)
	{
		if(isComplete(pInfo.UUID))
		{
			return;
		}
		
		if(PlayerQuestController.isQuestFinished(pInfo.PLAYER, npcQuestID))
		{
			setComplete(pInfo.UUID);
			pInfo.markDirty(Collections.singletonList(quest.getID()));
            //PktHandlerNpcQuests.sendSync(null, 0);
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public IGuiPanel getTaskGui(IGuiRect rect, DBEntry<IQuest> quest)
	{
		return new PanelTaskQuest(rect, this);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json)
	{
		json.setInteger("npcQuestID", npcQuestID);
		
		return json;
	}
	
	@Override
	public NBTTagCompound writeProgressToNBT(NBTTagCompound nbt, @Nullable List<UUID> users)
	{
		NBTTagList jArray = new NBTTagList();
		
		if(users != null)
        {
            users.forEach((uuid) -> {
                if(completeUsers.contains(uuid)) jArray.appendTag(new NBTTagString(uuid.toString()));
            });
        } else
        {
            completeUsers.forEach((uuid) -> jArray.appendTag(new NBTTagString(uuid.toString())));
        }
		
		nbt.setTag("completeUsers", jArray);
		
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound json)
	{
		npcQuestID = !json.hasKey("npcQuestID", 99) ? -1 : json.getInteger("npcQuestID");
	}
	
	@Override
	public void readProgressFromNBT(NBTTagCompound nbt, boolean merge)
	{
		if(!merge) completeUsers.clear();
		NBTTagList cList = nbt.getTagList("completeUsers", 8);
		for(int i = 0; i < cList.tagCount(); i++)
		{
			try
			{
				completeUsers.add(UUID.fromString(cList.getStringTagAt(i)));
			} catch(Exception e)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Unable to load UUID for task", e);
			}
		}
	}

	@Override
    @SideOnly(Side.CLIENT)
	public GuiScreen getTaskEditor(GuiScreen parent, DBEntry<IQuest> quest)
	{
		return null;
	}	
}
