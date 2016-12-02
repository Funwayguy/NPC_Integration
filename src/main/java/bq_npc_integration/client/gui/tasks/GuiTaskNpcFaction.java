package bq_npc_integration.client.gui.tasks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import bq_npc_integration.tasks.TaskNpcFaction;

public class GuiTaskNpcFaction extends GuiElement implements IGuiEmbedded
{
	private final TaskNpcFaction task;
	private final Minecraft mc;
	
	private int posX = 0;
	private int posY = 0;
	
	public GuiTaskNpcFaction(TaskNpcFaction task, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.task = task;
		
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		Faction fact = FactionController.getInstance().getFaction(task.factionID);
		String factName = fact != null? I18n.format("bq_npc_integration.gui.faction_name", fact.name) : "?";
		int points = -1;
		
		PlayerData pData = PlayerDataController.instance.getPlayerData(mc.thePlayer);
		
		if(pData == null || !pData.factionData.factionData.containsKey(task.factionID))
		{
			points = -1;
		} else
		{
			points = pData.factionData.getFactionPoints(task.factionID);
		}
		
		mc.fontRenderer.drawString(factName, posX, posY, getTextColor(), false);
		mc.fontRenderer.drawString(points + " " + task.operation.GetText() + " " + task.target, posX, posY + 12, getTextColor(), false);
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
