package bq_npc_integration.rewards;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import betterquesting.api.utils.JsonHelper;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcFaction;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import com.google.gson.JsonObject;

public class RewardNpcFaction implements IReward
{
	public int factionID = 0;
	public int value = 10;
	public boolean relative = false;
	
	@Override
	public ResourceLocation getFactoryID()
	{
		return FactoryRewardFaction.INSTANCE.getRegistryName();
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".reward.faction";
	}
	
	@Override
	public boolean canClaim(EntityPlayer player, IQuest quest)
	{
		return true;
	}
	
	@Override
	public void claimReward(EntityPlayer player, IQuest quest)
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
	public void readFromJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		factionID = JsonHelper.GetNumber(json, "factionID", 0).intValue();
		value = JsonHelper.GetNumber(json, "value", 1).intValue();
		relative = JsonHelper.GetBoolean(json, "relative", true);
	}
	
	@Override
	public JsonObject writeToJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		json.addProperty("factionID", factionID);
		json.addProperty("value", value);
		json.addProperty("relative", relative);
		
		return json;
	}
	
	@Override
	public IGuiEmbedded getRewardGui(int posX, int posY, int sizeX, int sizeY, IQuest quest)
	{
		return new GuiRewardNpcFaction(this, posX, posY, sizeX, sizeY);
	}

	@Override
	public IJsonDoc getDocumentation()
	{
		return null;
	}

	@Override
	public GuiScreen getRewardEditor(GuiScreen parent, IQuest quest)
	{
		return null;
	}
	
}
