package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import noppes.npcs.controllers.Quest;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.lists.GuiScrollingText;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import bq_npc_integration.NpcQuestDB;
import bq_npc_integration.tasks.TaskNpcQuest;

public class GuiTaskNpcQuest extends GuiElement implements IGuiEmbedded
{
	private final TaskNpcQuest task;
	private final Minecraft mc;
	
	private int posX = 0;
	private int posY = 0;
	
	private Quest npcQuest;
	private GuiScrollingText textScroll;
	
	public GuiTaskNpcQuest(TaskNpcQuest task, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.task = task;
		
		this.posX = posX;
		this.posY = posY;
		
		this.npcQuest = NpcQuestDB.npcQuests.get(task.npcQuestID);
		this.textScroll = new GuiScrollingText(mc, posX, posY + 24, sizeX, sizeY - 24, npcQuest == null? "" : npcQuest.logText);
	}

	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		mc.fontRenderer.drawString(I18n.format("bq_npc_integration.gui.quest", npcQuest == null? "?" : npcQuest.title), posX, posY, getTextColor());
		String txt = task.isComplete(mc.thePlayer.getUniqueID())? (EnumChatFormatting.GREEN + I18n.format("betterquesting.tooltip.complete")) : (EnumChatFormatting.RED + I18n.format("betterquesting.tooltip.incomplete"));
		mc.fontRenderer.drawString(I18n.format("bq_npc_integration.gui.status", txt), posX, posY + 10, getTextColor());
		textScroll.drawBackground(mx, my, partialTick);
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
		textScroll.onMouseScroll(mx, my, scroll);
	}

	@Override
	public void onKeyTyped(char c, int keyCode)
	{
	}
}
