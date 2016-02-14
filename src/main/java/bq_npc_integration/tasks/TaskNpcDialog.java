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
import bq_npc_integration.client.gui.tasks.GuiTaskNpcDialog;
import bq_npc_integration.core.BQ_NPCs;
import com.google.gson.JsonObject;

public class TaskNpcDialog extends TaskBase
{
	public int npcDialogID = -1;
	public String desc = "Talk to an NPC";
	
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
		
		if(pData == null)
		{
			return;
		}
		
		if(pData.dialogData.dialogsRead.contains(npcDialogID))
		{
			this.completeUsers.add(player.getUniqueID());
		}
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.dialog";
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
	public void writeToJson(JsonObject json)
	{
		super.writeToJson(json);
		
		json.addProperty("npcDialogID", npcDialogID);
		json.addProperty("description", desc);
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
		super.readFromJson(json);
		
		npcDialogID = JsonHelper.GetNumber(json, "npcDialogID", -1).intValue();
		desc = JsonHelper.GetString(json, "description", "Talk to an NPC");
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiTaskNpcDialog(this, screen, posX, posY, sizeX, sizeY);
	}
}
