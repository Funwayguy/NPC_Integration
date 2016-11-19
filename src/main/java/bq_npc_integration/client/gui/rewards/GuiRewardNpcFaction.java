package bq_npc_integration.client.gui.rewards;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import org.lwjgl.opengl.GL11;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.themes.ThemeRegistry;
import bq_npc_integration.rewards.RewardNpcFaction;

public class GuiRewardNpcFaction extends GuiEmbedded
{
	RewardNpcFaction reward;
	
	public GuiRewardNpcFaction(RewardNpcFaction reward, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		super(screen, posX, posY, sizeX, sizeY);
		this.reward = reward;
	}

	@Override
	public void drawGui(int mx, int my, float partialTick)
	{
		Faction fact = FactionController.getInstance().getFaction(reward.factionID);
		String factName = fact != null? I18n.format("bq_npc_integration.gui.faction_name", fact.name) : "?";
		String txt2 = "" + TextFormatting.BOLD;
		
		if(!reward.relative)
		{
			txt2 += "= " + reward.value;
		} else if(reward.value >= 0)
		{
			txt2 += TextFormatting.GREEN + "+ " + Math.abs(reward.value);
		} else
		{
			txt2 += TextFormatting.RED + "- " + Math.abs(reward.value);
		}
		
		GL11.glPushMatrix();
		GL11.glScalef(1.5F, 1.5F, 1F);
		screen.mc.fontRendererObj.drawString(factName, (int)((posX + sizeX/2 - screen.mc.fontRendererObj.getStringWidth(factName)/1.5F)/1.5F), (int)((posY + sizeY/2 - 16)/1.5F), ThemeRegistry.curTheme().textColor().getRGB(), false);
		screen.mc.fontRendererObj.drawString(txt2, (int)((posX + sizeX/2 - screen.mc.fontRendererObj.getStringWidth(txt2)/1.5F)/1.5F), (int)((posY + sizeY/2)/1.5F), ThemeRegistry.curTheme().textColor().getRGB(), false);
		GL11.glPopMatrix();
	}
}
