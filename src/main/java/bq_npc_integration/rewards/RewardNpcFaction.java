package bq_npc_integration.rewards;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import com.google.gson.JsonObject;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.rewards.RewardBase;
import betterquesting.utils.JsonHelper;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcFaction;
import bq_npc_integration.core.BQ_NPCs;

public class RewardNpcFaction extends RewardBase
{
	public int factionID = 0;
	public int value = 10;
	public boolean relative = false;
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".reward.faction";
	}
	
	@Override
	public boolean canClaim(EntityPlayer player, NBTTagCompound choiceData)
	{
		return true;
	}
	
	@Override
	public void Claim(EntityPlayer player, NBTTagCompound choiceData)
	{
		PlayerData pData = PlayerDataController.instance.getPlayerData(player);
		
		if(pData == null || !pData.factionData.factionData.containsKey(factionID))
		{
			return;
		}
		
		if(relative)
		{
			pData.factionData.increasePoints(factionID, value);
		} else
		{
			pData.factionData.factionData.put(factionID, value);
		}
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
		factionID = JsonHelper.GetNumber(json, "factionID", 0).intValue();
		value = JsonHelper.GetNumber(json, "value", 1).intValue();
		relative = JsonHelper.GetBoolean(json, "relative", true);
	}
	
	@Override
	public void writeToJson(JsonObject json)
	{
		json.addProperty("factionID", factionID);
		json.addProperty("value", value);
		json.addProperty("relative", relative);
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiRewardNpcFaction(this, screen, posX, posY, sizeX, sizeY);
	}
	
}
