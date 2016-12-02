package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import org.apache.logging.log4j.Level;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api.utils.JsonHelper;
import bq_npc_integration.client.gui.tasks.GuiTaskNpcFaction;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.factory.FactoryTaskFaction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TaskNpcFaction implements ITask
{
	private ArrayList<UUID> completeUsers = new ArrayList<UUID>();
	
	public int factionID = 0;
	public int target = 10;
	public PointOperation operation = PointOperation.MORE_OR_EQUAL;
	
	@Override
	public ResourceLocation getFactoryID()
	{
		return FactoryTaskFaction.INSTANCE.getRegistryName();
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.faction";
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
	public void update(EntityPlayer player, IQuest quest)
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
		
		PlayerData pData = PlayerDataController.instance.getDataFromUsername(player.getServer(), player.getGameProfile().getName());
		
		if(pData == null || !pData.factionData.factionData.containsKey(factionID))
		{
			return;
		}
		
		int points = pData.factionData.getFactionPoints(factionID);
		
		boolean flag = false;
		
		switch(operation)
		{
			case EQUAL:
				flag = points == target;
				break;
			case LESS_THAN:
				flag = points < target;
				break;
			case MORE_THAN:
				flag = points > target;
				break;
			case LESS_OR_EQUAL:
				flag = points <= target;
				break;
			case MORE_OR_EQUAL:
				flag = points >= target;
				break;
		}
		
		if(flag)
		{
			setComplete(QuestingAPI.getQuestingUUID(player));
		}
	}
	
	@Override
	public void readFromJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType == EnumSaveType.PROGRESS)
		{
			readFromJson_Progress(json);
			return;
		} else if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		factionID = JsonHelper.GetNumber(json, "factionID", 0).intValue();
		target = JsonHelper.GetNumber(json, "target", 1).intValue();
		operation = PointOperation.valueOf(JsonHelper.GetString(json, "operation", "MORE_OR_EQUAL").toUpperCase());
		operation = operation != null? operation : PointOperation.MORE_OR_EQUAL;
	}
	
	private void readFromJson_Progress(JsonObject json)
	{
		completeUsers = new ArrayList<UUID>();
		for(JsonElement entry : JsonHelper.GetArray(json, "completeUsers"))
		{
			if(entry == null || !entry.isJsonPrimitive())
			{
				continue;
			}
			
			try
			{
				completeUsers.add(UUID.fromString(entry.getAsString()));
			} catch(Exception e)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Unable to load UUID for task", e);
			}
		}
	}
	
	@Override
	public JsonObject writeToJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType == EnumSaveType.PROGRESS)
		{
			return writeToJson_Progress(json);
		} else if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		json.addProperty("factionID", factionID);
		json.addProperty("target", target);
		json.addProperty("operation", operation.name());
		
		return json;
	}
	
	private JsonObject writeToJson_Progress(JsonObject json)
	{
		JsonArray jArray = new JsonArray();
		for(UUID uuid : completeUsers)
		{
			jArray.add(new JsonPrimitive(uuid.toString()));
		}
		json.add("completeUsers", jArray);
		
		return json;
	}
	
	@Override
	public IGuiEmbedded getTaskGui(int posX, int posY, int sizeX, int sizeY, IQuest quest)
	{
		return new GuiTaskNpcFaction(this, posX, posY, sizeX, sizeY);
	}
	
	public static enum PointOperation
	{
		EQUAL("="),
		LESS_THAN("<"),
		MORE_THAN(">"),
		LESS_OR_EQUAL("<="),
		MORE_OR_EQUAL(">=");
		
		String text = "";
		PointOperation(String text)
		{
			this.text = text;
		}
		
		public String GetText()
		{
			return text;
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
