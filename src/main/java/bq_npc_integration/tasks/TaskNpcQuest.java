package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import betterquesting.api.questing.tasks.ITickableTask;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerQuestController;
import org.apache.logging.log4j.Level;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import bq_npc_integration.client.gui.tasks.GuiTaskNpcQuest;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.storage.NpcQuestDB;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;

public class TaskNpcQuest implements ITask, ITickableTask
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
	public void updateTask(EntityPlayer player, IQuest quest)
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
			NpcQuestDB.INSTANCE.UpdateClients();
		}
	}
	
	@Override
	public IGuiEmbedded getTaskGui(int posX, int posY, int sizeX, int sizeY, IQuest quest)
	{
		return new GuiTaskNpcQuest(this, posX, posY, sizeX, sizeY);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType == EnumSaveType.PROGRESS)
		{
			return writeToNBT_Progress(json);
		} else if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		json.setInteger("npcQuestID", npcQuestID);
		
		return json;
	}
	
	private NBTTagCompound writeToNBT_Progress(NBTTagCompound json)
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
	public void readFromNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType == EnumSaveType.PROGRESS)
		{
			readFromNBT_Progress(json);
			return;
		} else if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		npcQuestID = !json.hasKey("npcQuestID", 99) ? -1 : json.getInteger("npcQuestID");
	}
	
	private void readFromNBT_Progress(NBTTagCompound json)
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
	public IJsonDoc getDocumentation()
	{
		return null;
	}

	@Override
	public GuiScreen getTaskEditor(GuiScreen parent, IQuest quest)
	{
		return null;
	}	
}
