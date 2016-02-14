package bq_npc_integration.client.gui.tasks;

import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import bq_npc_integration.tasks.TaskNpcMail;

public class GuiTaskNpcMail extends GuiEmbedded
{
	TaskNpcMail task;
	
	public GuiTaskNpcMail(TaskNpcMail task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.task = task;
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
	}
}
