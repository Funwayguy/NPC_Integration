package bq_npc_integration.tasks;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import bq_npc_integration.client.gui.tasks.PanelTaskQuest;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.storage.NpcQuestDB;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerQuestController;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskNpcQuest implements ITaskTickable
{
	private final List<UUID> completeUsers = new ArrayList<>();
	
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
		if(!completeUsers.contains(uuid))
		{
			completeUsers.add(uuid);
		}
	}

	@Override
	public void resetUser(UUID uuid)
	{
		completeUsers.remove(uuid);
	}

	@Override
	public void resetAll()
	{
		completeUsers.clear();
	}
	
	@Override
	public void tickTask(IQuest quest, EntityPlayer player)
	{
		if(player.ticksExisted%60 != 0 || QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE))
		{
			return;
		}
		
		detect(player, quest);
	}
	
	@Override
	public void detect(EntityPlayer player, IQuest quest)
	{
		if(isComplete(QuestingAPI.getQuestingUUID(player)))
		{
			return;
		}
		
		if(PlayerQuestController.isQuestFinished(player, npcQuestID))
		{
			setComplete(QuestingAPI.getQuestingUUID(player));
			QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(NpcQuestDB.INSTANCE.getSyncPacket());
		}
	}
	
	@Override
	public IGuiPanel getTaskGui(IGuiRect rect, IQuest quest)
	{
		return new PanelTaskQuest(rect, quest, this);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json)
	{
		json.setInteger("npcQuestID", npcQuestID);
		
		return json;
	}
	
	@Override
	public NBTTagCompound writeProgressToNBT(NBTTagCompound json, List<UUID> users)
	{
		NBTTagList jArray = new NBTTagList();
		for(UUID uuid : completeUsers)
		{
			jArray.appendTag(new NBTTagString(uuid.toString()));
		}
		json.setTag("completeUsers", jArray);
		
		return json;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound json)
	{
		npcQuestID = !json.hasKey("npcQuestID", 99) ? -1 : json.getInteger("npcQuestID");
	}
	
	@Override
	public void readProgressFromNBT(NBTTagCompound json, boolean merge)
	{
		completeUsers.clear();
		NBTTagList cList = json.getTagList("completeUsers", 8);
		for(int i = 0; i < cList.tagCount(); i++)
		{
			NBTBase entry = cList.get(i);
			
			if(entry.getId() != 8)
			{
				continue;
			}
			
			try
			{
				completeUsers.add(UUID.fromString(((NBTTagString)entry).getString()));
			} catch(Exception e)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Unable to load UUID for task", e);
			}
		}
	}

	@Override
	public GuiScreen getTaskEditor(GuiScreen parent, IQuest quest)
	{
		return null;
	}	
}
