package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.QuestDatabase;
import betterquesting.quests.tasks.TaskBase;
import betterquesting.utils.JsonHelper;
import bq_npc_integration.client.gui.tasks.GuiTaskNpcFaction;
import bq_npc_integration.core.BQ_NPCs;
import com.google.gson.JsonObject;

public class TaskNpcFaction extends TaskBase
{
	public int factionID = 0;
	public int target = 10;
	public PointOperation operation = PointOperation.MORE_OR_EQUAL;
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.faction";
	}
	
	@Override
	public void Update(EntityPlayer player)
	{
		if(player.ticksExisted%60 != 0 || QuestDatabase.editMode)
		{
			return;
		}
		
		Detect(player);
	}
	
	@Override
	public void Detect(EntityPlayer player)
	{
		if(isComplete(player.getUniqueID()))
		{
			return;
		}
		
		PlayerData pData = PlayerDataController.instance.getPlayerData(player);
		
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
			this.completeUsers.add(player.getUniqueID());
		}
	}
	
	@Override
	public void ResetProgress(UUID uuid)
	{
		this.completeUsers.remove(uuid);
	}
	
	@Override
	public void ResetAllProgress()
	{
		this.completeUsers = new ArrayList<UUID>();
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
		super.readFromJson(json);
		
		factionID = JsonHelper.GetNumber(json, "factionID", 0).intValue();
		target = JsonHelper.GetNumber(json, "target", 1).intValue();
		operation = PointOperation.valueOf(JsonHelper.GetString(json, "operation", "MORE_OR_EQUAL").toUpperCase());
		operation = operation != null? operation : PointOperation.MORE_OR_EQUAL;
	}
	
	@Override
	public void writeToJson(JsonObject json)
	{
		super.writeToJson(json);
		
		json.addProperty("factionID", factionID);
		json.addProperty("target", target);
		json.addProperty("operation", operation.name());
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiTaskNpcFaction(this, screen, posX, posY, sizeX, sizeY);
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
}
