package bq_npc_integration.rewards;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerDataController;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcFaction;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import noppes.npcs.controllers.data.PlayerData;

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
		PlayerData pData = PlayerDataController.instance.getDataFromUsername(player.getServer(), player.getGameProfile().getName());
		
		if(pData == null || !pData.factionData.factionData.containsKey(factionID))
		{
			return;
		}
		
		if(relative)
		{
			pData.factionData.increasePoints(player, factionID, value);
		} else
		{
			pData.factionData.factionData.put(factionID, value);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		factionID = !json.hasKey("factionID", 99) ? 0 : json.getInteger("factionID");
		value = !json.hasKey("value", 99) ? 1 : json.getInteger("value");
		relative = !json.hasKey("relative") || json.getBoolean("relative");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		json.setInteger("factionID", factionID);
		json.setInteger("value", value);
		json.setBoolean("relative", relative);
		
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
