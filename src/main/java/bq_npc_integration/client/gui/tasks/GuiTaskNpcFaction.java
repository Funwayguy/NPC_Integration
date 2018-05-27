package bq_npc_integration.client.gui.tasks;

import betterquesting.api2.utils.QuestTranslation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import bq_npc_integration.storage.NpcFactionDB;
import bq_npc_integration.tasks.TaskNpcFaction;
import noppes.npcs.controllers.data.Faction;

public class GuiTaskNpcFaction extends GuiElement implements IGuiEmbedded
{
	private final TaskNpcFaction task;
	private final Minecraft mc;
	
	private final int posX;
	private final int posY;
	private final int sizeX;
	private final int sizeY;
	
	public GuiTaskNpcFaction(TaskNpcFaction task, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.task = task;
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX + sizeX/2, posY + sizeY/2, 0F);
		GlStateManager.scale(2F, 2F, 1F);
		
		Faction fact = NpcFactionDB.INSTANCE.getFaction(task.factionID);
		String factName = TextFormatting.BOLD + (fact != null? fact.getName() : "?");
		int points = task.getUsersProgress(QuestingAPI.getQuestingUUID(mc.player));
		
		String factPrefix = TextFormatting.BOLD + QuestTranslation.translate("bq_npc_integration.gui.faction_name", "");
		int tw0 = mc.fontRenderer.getStringWidth(factPrefix);
		int tw1 = mc.fontRenderer.getStringWidth(factName);
		mc.fontRenderer.drawString(factName, -tw1/2, -12, getTextColor(), false);
		mc.fontRenderer.drawString(factPrefix, -tw0/2, -24, getTextColor(), false);
		
		String txt = TextFormatting.BOLD.toString() + points + " " + TextFormatting.RESET.toString() + task.operation.GetText() + " " + task.target;
		
		if(task.operation.checkValues(points, task.target))
		{
			txt = TextFormatting.GREEN + txt;
		} else
		{
			txt = TextFormatting.RED + txt;
		}
		
		int tw2 = mc.fontRenderer.getStringWidth(txt);
		mc.fontRenderer.drawString(txt, -tw2/2, 1, getTextColor(), false);
		
		GlStateManager.popMatrix();
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
