package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import noppes.npcs.controllers.Quest;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.gui.misc.GuiScrollingText;
import betterquesting.client.themes.ThemeRegistry;
import bq_npc_integration.NpcQuestDB;
import bq_npc_integration.tasks.TaskNpcQuest;

public class GuiTaskNpcQuest extends GuiEmbedded
{
	TaskNpcQuest task;
	Quest npcQuest;
	GuiScrollingText textScroll;
	
	public GuiTaskNpcQuest(TaskNpcQuest task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.task = task;
		this.npcQuest = NpcQuestDB.npcQuests.get(task.npcQuestID);
		textScroll = new GuiScrollingText(screen, sizeX, sizeY - 24, posY + 24, posX, npcQuest == null? "" : npcQuest.logText);
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		screen.mc.fontRenderer.drawString(StatCollector.translateToLocalFormatted("bq_npc_integration.gui.quest", npcQuest == null? "?" : npcQuest.title), posX, posY, ThemeRegistry.curTheme().textColor().getRGB());
		String txt = task.isComplete(screen.mc.thePlayer.getUniqueID())? (EnumChatFormatting.GREEN + I18n.format("betterquesting.tooltip.complete")) : (EnumChatFormatting.RED + I18n.format("betterquesting.tooltip.incomplete"));
		screen.mc.fontRenderer.drawString(StatCollector.translateToLocalFormatted("bq_npc_integration.gui.status", txt), posX, posY + 10, ThemeRegistry.curTheme().textColor().getRGB());
		textScroll.drawScreen(mx, my, partialTick);
	}
}
