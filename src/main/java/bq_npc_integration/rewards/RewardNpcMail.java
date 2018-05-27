package bq_npc_integration.rewards;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.PlayerMail;
import org.apache.logging.log4j.Level;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcMail;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardMail;

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
	public void readFromNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		mail = new PlayerMail();
		mail.readNBT(json);
		
		if(mail.message.hasNoTags())
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
	public NBTTagCompound writeToNBT(NBTTagCompound json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		json.merge(mail.writeNBT());
		
		return json;
	}
	
	@Override
	public IGuiEmbedded getRewardGui(int posX, int posY, int sizeX, int sizeY, IQuest quest)
	{
		return new GuiRewardNpcMail(this, posX, posY, sizeX, sizeY);
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
