package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.resources.I18n;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.themes.ThemeRegistry;
import bq_npc_integration.tasks.TaskNpcFaction;

public class GuiTaskFaction extends GuiEmbedded
{
	TaskNpcFaction task;
	
	public GuiTaskFaction(TaskNpcFaction task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.task = task;
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		Faction fact = FactionController.getInstance().getFaction(task.factionID);
		String factName = fact != null? I18n.format("bq_npc_integration.gui.faction_name", fact.name) : "?";
		int points = -1;
		
		PlayerData pData = PlayerDataController.instance.getPlayerData(screen.mc.thePlayer);
		
		if(pData == null || !pData.factionData.factionData.containsKey(task.factionID))
		{
			points = -1;
		} else
		{
			points = pData.factionData.getFactionPoints(task.factionID);
		}
		
		screen.mc.fontRenderer.drawString(factName, posX, posY, ThemeRegistry.curTheme().textColor().getRGB(), false);
		screen.mc.fontRenderer.drawString(points + " " + task.operation.GetText() + " " + task.target, posX, posY + 12, ThemeRegistry.curTheme().textColor().getRGB(), false);
	}
	
}
