package bq_npc_integration.rewards;

import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import bq_npc_integration.client.gui.rewards.PanelRewardFaction;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.PlayerDataController;
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
	    if(player.getServer() == null) return;
	    
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
	public void readFromNBT(NBTTagCompound json)
	{
		factionID = !json.hasKey("factionID", 99) ? 0 : json.getInteger("factionID");
		value = !json.hasKey("value", 99) ? 1 : json.getInteger("value");
		relative = !json.hasKey("relative") || json.getBoolean("relative");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json)
	{
		json.setInteger("factionID", factionID);
		json.setInteger("value", value);
		json.setBoolean("relative", relative);
		
		return json;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public IGuiPanel getRewardGui(IGuiRect rect, IQuest quest)
	{
		return new PanelRewardFaction(rect, quest, this);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public GuiScreen getRewardEditor(GuiScreen parent, IQuest quest)
	{
		return null;
	}
	
}
