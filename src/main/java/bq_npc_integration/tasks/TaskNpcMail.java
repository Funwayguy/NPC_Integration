package bq_npc_integration.tasks;

import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.QuestDatabase;
import betterquesting.quests.tasks.TaskBase;
import bq_npc_integration.client.gui.tasks.GuiTaskNpcMail;
import bq_npc_integration.core.BQ_NPCs;

public class TaskNpcMail extends TaskBase
{
	@Override
	public String getUnlocalisedName()
	{
		return BQ_NPCs.MODID + ".task.mail";
	}
	
	@Override
	public void Update(EntityPlayer player)
	{
		if(player.ticksExisted%100 != 0 || QuestDatabase.editMode)
		{
			return;
		}
		
		Detect(player);
	}
	
	@Override
	public void Detect(EntityPlayer player)
	{
		
	}
	
	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiTaskNpcMail(this, screen, posX, posY, sizeX, sizeY);
	}
	
	@Override // Not necessary to override anymore but kept for backwards compatibility
	public void ResetProgress(UUID uuid)
	{
		completeUsers.remove(uuid);
	}
	
	@Override // Not necessary to override anymore but kept for backwards compatibility
	public void ResetAllProgress()
	{
		completeUsers = new ArrayList<UUID>();
	}
}
