package bq_npc_integration.rewards;

import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import bq_npc_integration.client.gui.rewards.PanelRewardMail;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardMail;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.PlayerMail;
import org.apache.logging.log4j.Level;

public class RewardNpcMail implements IReward
{
	public PlayerMail mail = new PlayerMail();
	
	@Override
	public ResourceLocation getFactoryID()
	{
		return FactoryRewardMail.INSTANCE.getRegistryName();
	}
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".reward.mail";
	}
	
	@Override
	public boolean canClaim(EntityPlayer player, IQuest quest)
	{
		return true;
	}
	
	@Override
	public void claimReward(EntityPlayer player, IQuest quest)
	{
		if(mail.isValid())
		{
			PlayerDataController.instance.addPlayerMessage(player.getServer(), player.getGameProfile().getName(), mail.copy());
		} else
		{
			BQ_NPCs.logger.log(Level.ERROR, "Tried to claim an invalid mail reward!");
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound json)
	{
		mail = new PlayerMail();
		mail.readNBT(json);
		
		if(mail.message.isEmpty())
		{
			NBTTagList pages = new NBTTagList();
			pages.appendTag(new NBTTagString("404: Reward could not be found!"));
			mail.message.setTag("pages", pages);
		}
		
		if(mail.subject.isEmpty())
		{
			mail.subject = "Reward";
		}
		
		if(mail.sender.isEmpty())
		{
			mail.sender = "Anonymous";
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound json)
	{
		json.merge(mail.writeNBT());
		
		return json;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public IGuiPanel getRewardGui(IGuiRect rect, IQuest quest)
	{
		return new PanelRewardMail(rect, quest, this);
	}
 
	@Override
    @SideOnly(Side.CLIENT)
	public GuiScreen getRewardEditor(GuiScreen parent, IQuest quest)
	{
		return null;
	}
}
