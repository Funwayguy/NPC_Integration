package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.resources.I18n;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.gui.misc.GuiScrollingText;
import betterquesting.client.themes.ThemeRegistry;
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
		this.npcQuest = QuestController.instance.quests.get(task.npcQuestID);
		textScroll = new GuiScrollingText(screen, sizeX, sizeY - 24, posY + 24, posX, npcQuest == null? "" : npcQuest.logText);
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		if(npcQuest == null)
		{
			return;
		}
		
		screen.mc.fontRenderer.drawString("Quest: " + npcQuest.title, posX, posY, ThemeRegistry.curTheme().textColor().getRGB());
		String txt = task.isComplete(screen.mc.thePlayer.getUniqueID())? I18n.format("betterquesting.tooltip.complete") : I18n.format("betterquesting.tooltip.incomplete");
		screen.mc.fontRenderer.drawString("Status: " + txt, posX, posY + 10, ThemeRegistry.curTheme().textColor().getRGB());
		textScroll.drawScreen(mx, my, partialTick);
	}
}
