package bq_npc_integration.client.gui.rewards;

import betterquesting.api2.utils.QuestTranslation;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import noppes.npcs.CustomItems;
import org.lwjgl.opengl.GL11;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.utils.RenderUtils;
import bq_npc_integration.rewards.RewardNpcMail;

public class GuiRewardNpcMail extends GuiElement implements IGuiEmbedded
{
	private final ItemStack mailbox = new ItemStack(CustomItems.mailbox);
	private final RewardNpcMail reward;
	private final Minecraft mc;
	
	private final int posX;
	private final int posY;
	private final int sizeX;
	private final int sizeY;
	
	public GuiRewardNpcMail(RewardNpcMail reward, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.reward = reward;
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glScalef(2F, 2F, 2F);
		RenderUtils.RenderItemStack(mc, mailbox, (posX + sizeX/2 - 16)/2, (posY + sizeY/2 - 32)/2, "");
		GL11.glPopMatrix();
		
		String txt = QuestTranslation.translate("bq_npc_integration.gui.mail", reward.mail.sender);
		mc.fontRenderer.drawString(txt, posX + sizeX/2 - mc.fontRenderer.getStringWidth(txt)/2, posY + sizeY/2 + 4, getTextColor(), true);
	}

	@Override
	public void drawForeground(int mx, int my, float partialTick)
	{
	}

	@Override
	public void onMouseClick(int mx, int my, int click)
	{
	}

	@Override
	public void onMouseScroll(int mx, int my, int scroll)
	{
	}

	@Override
	public void onKeyTyped(char c, int keyCode)
	{
	}
}
