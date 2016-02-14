package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.controllers.PlayerQuestController;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.QuestDatabase;
import betterquesting.quests.tasks.TaskBase;
import betterquesting.utils.JsonHelper;
import bq_npc_integration.client.gui.tasks.GuiTaskNpcQuest;
import bq_npc_integration.core.BQ_NPCs;
import com.google.gson.JsonObject;

public class TaskNpcQuest extends TaskBase
{
	public int npcQuestID = -1;
	
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
		
		if(PlayerQuestController.isQuestFinished(player, npcQuestID))
		{
			completeUsers.add(player.getUniqueID());
		}
	}
	
	@Override
	public void ResetAllProgress()
	{
		this.completeUsers = new ArrayList<UUID>();
	}
	
	@Override
	public void ResetProgress(UUID uuid)
	{
		this.completeUsers.remove(uuid);
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiTaskNpcQuest(this, screen, posX, posY, sizeX, sizeY);
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.quest";
	}
	
	@Override
	public void writeToJson(JsonObject json)
	{
		super.writeToJson(json);
		
		json.addProperty("npcQuestID", npcQuestID);
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
		super.readFromJson(json);
		
		npcQuestID = JsonHelper.GetNumber(json, "npcQuestID", -1).intValue();
	}		
}
