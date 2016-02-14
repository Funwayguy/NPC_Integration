package bq_npc_integration.client.gui.rewards;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import org.lwjgl.opengl.GL11;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.themes.ThemeRegistry;
import betterquesting.utils.RenderUtils;
import bq_npc_integration.rewards.RewardNpcMail;

public class GuiRewardNpcMail extends GuiEmbedded
{
	ItemStack mailbox = new ItemStack(CustomItems.mailbox);
	RewardNpcMail task;
	
	public GuiRewardNpcMail(RewardNpcMail task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.task = task;
	}
	
	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glScalef(2F, 2F, 2F);
		RenderUtils.RenderItemStack(screen.mc, mailbox, (posX + sizeX/2 - 16)/2, (posY + sizeY/2 - 32)/2, "");
		GL11.glPopMatrix();
		
		String txt = I18n.format("bq_npc_integration.gui.mail", task.mail.sender);
		screen.mc.fontRenderer.drawString(txt, posX + sizeX/2 - screen.mc.fontRenderer.getStringWidth(txt)/2, posY + sizeY/2 + 4, ThemeRegistry.curTheme().textColor().getRGB(), true);
	}
}
