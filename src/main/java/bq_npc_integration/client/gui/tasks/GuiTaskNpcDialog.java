package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.resources.I18n;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.gui.misc.GuiScrollingText;
import betterquesting.client.themes.ThemeRegistry;
import bq_npc_integration.tasks.TaskNpcDialog;

public class GuiTaskNpcDialog extends GuiEmbedded
{
	TaskNpcDialog task;
	Dialog dialog;
	GuiScrollingText desc;
	
	public GuiTaskNpcDialog(TaskNpcDialog task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.task = task;
		this.desc = new GuiScrollingText(screen, sizeX, sizeY - 24, posY + 24, posX, task.desc);
		this.dialog = DialogController.instance.dialogs.get(task.npcDialogID);
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		if(dialog == null)
		{
			return;
		}
		
		screen.mc.fontRenderer.drawString("Dialog: " + dialog.title, posX, posY, ThemeRegistry.curTheme().textColor().getRGB());
		String txt = task.isComplete(screen.mc.thePlayer.getUniqueID())? I18n.format("betterquesting.tooltip.complete") : I18n.format("betterquesting.tooltip.incomplete");
		screen.mc.fontRenderer.drawString("Status: " + txt, posX, posY + 10, ThemeRegistry.curTheme().textColor().getRGB());
		desc.drawScreen(mx, my, partialTick);
	}
}
