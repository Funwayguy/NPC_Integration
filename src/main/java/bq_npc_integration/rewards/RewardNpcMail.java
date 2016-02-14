package bq_npc_integration.rewards;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerMail;
import org.apache.logging.log4j.Level;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.rewards.RewardBase;
import betterquesting.utils.NBTConverter;
import bq_npc_integration.client.gui.rewards.GuiRewardNpcMail;
import bq_npc_integration.core.BQ_NPCs;
import com.google.gson.JsonObject;

public class RewardNpcMail extends RewardBase
{
	public PlayerMail mail = new PlayerMail();
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".reward.mail";
	}
	
	@Override
	public boolean canClaim(EntityPlayer player, NBTTagCompound choiceData)
	{
		return true;
	}
	
	@Override
	public void Claim(EntityPlayer player, NBTTagCompound choiceData)
	{
		if(mail.isValid())
		{
			PlayerDataController.instance.addPlayerMessage(player.getCommandSenderName(), mail.copy());
		} else
		{
			BQ_NPCs.logger.log(Level.ERROR, "Tried to claim an invalid mail reward!");
		}
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
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
	public void writeToJson(JsonObject json)
	{
		NBTConverter.NBTtoJSON_Compound(mail.writeNBT(), json);
		/*json.addProperty("sender", sender);
		json.addProperty("message", message);
		json.addProperty("subject", subject);
		json.add("item1", JsonHelper.ItemStackToJson(attachments[0], new JsonObject()));
		json.add("item2", JsonHelper.ItemStackToJson(attachments[1], new JsonObject()));
		json.add("item3", JsonHelper.ItemStackToJson(attachments[2], new JsonObject()));
		json.add("item4", JsonHelper.ItemStackToJson(attachments[3], new JsonObject()));*/
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiRewardNpcMail(this, screen, posX, posY, sizeX, sizeY);
	}
}
