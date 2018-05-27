package bq_npc_integration.client.gui.tasks;

import betterquesting.api2.utils.QuestTranslation;
import net.minecraft.client.Minecraft;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.lists.GuiScrollingText;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import bq_npc_integration.storage.NpcDialogDB;
import bq_npc_integration.tasks.TaskNpcDialog;
import noppes.npcs.controllers.data.Dialog;

public class GuiTaskNpcDialog extends GuiElement implements IGuiEmbedded
{
	private final TaskNpcDialog task;
	private final Minecraft mc;
	
	private final int posX;
	private final int posY;
	
	private Dialog dialog;
	private GuiScrollingText desc;
	
	public GuiTaskNpcDialog(TaskNpcDialog task, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.task = task;
		
		this.posX = posX;
		this.posY = posY;
		
		this.desc = new GuiScrollingText(mc, posX, posY +24, sizeX, sizeY - 24, task.desc);
		
		this.dialog = NpcDialogDB.INSTANCE.getDialog(task.npcDialogID);
	}

	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		if(dialog == null)
		{
			return;
		}
		
		mc.fontRenderer.drawString(QuestTranslation.translate("bq_npc_integration.gui.dialog", dialog.title), posX, posY, getTextColor());
		String txt = task.isComplete(QuestingAPI.getQuestingUUID(mc.player))? QuestTranslation.translate("betterquesting.tooltip.complete") : QuestTranslation.translate("betterquesting.tooltip.incomplete");
		mc.fontRenderer.drawString(QuestTranslation.translate("bq_npc_integration.gui.status", txt), posX, posY + 10, getTextColor());
		desc.drawBackground(mx, my, partialTick);
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
