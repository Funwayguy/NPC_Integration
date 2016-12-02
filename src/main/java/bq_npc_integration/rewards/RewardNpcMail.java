package bq_npc_integration.rewards;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerMail;
import org.apache.logging.log4j.Level;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.rewards.IReward;
import betterquesting.api.utils.NBTConverter;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcMail;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.factory.FactoryRewardMail;
import com.google.gson.JsonObject;

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
	public void readFromJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return;
		}
		
		mail = new PlayerMail();
		mail.readNBT(NBTConverter.JSONtoNBT_Object(json, new NBTTagCompound()));
		
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
		
		/*sender = JsonHelper.GetString(json, "sender", "Anonymous");
		message = JsonHelper.GetString(json, "message", "");
		subject = JsonHelper.GetString(json, "subject", "");
		attachments[0] = JsonHelper.JsonToItemStack(JsonHelper.GetObject(json, "item1"));
		attachments[1] = JsonHelper.JsonToItemStack(JsonHelper.GetObject(json, "item2"));
		attachments[2] = JsonHelper.JsonToItemStack(JsonHelper.GetObject(json, "item3"));
		attachments[3] = JsonHelper.JsonToItemStack(JsonHelper.GetObject(json, "item4"));*/
	}
	
	@Override
	public JsonObject writeToJson(JsonObject json, EnumSaveType saveType)
	{
		if(saveType != EnumSaveType.CONFIG)
		{
			return json;
		}
		
		NBTConverter.NBTtoJSON_Compound(mail.writeNBT(), json);
		
		return json;
		
		/*json.addProperty("sender", sender);
		json.addProperty("message", message);
		json.addProperty("subject", subject);
		json.add("item1", JsonHelper.ItemStackToJson(attachments[0], new JsonObject()));
		json.add("item2", JsonHelper.ItemStackToJson(attachments[1], new JsonObject()));
		json.add("item3", JsonHelper.ItemStackToJson(attachments[2], new JsonObject()));
		json.add("item4", JsonHelper.ItemStackToJson(attachments[3], new JsonObject()));*/
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
