package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.UUID;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.tasks.TaskBase;
import bq_npc_integration.core.BQ_NPCs;

public class TaskNpcFaction extends TaskBase
{
	
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.faction";
	}
	
	@Override
	public void ResetProgress(UUID uuid)
	{
		this.completeUsers.remove(uuid);
	}
	
	@Override
	public void ResetAllProgress()
	{
		this.completeUsers = new ArrayList<UUID>();
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return null;
	}
	
}
