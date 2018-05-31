package bq_npc_integration.client.gui.rewards;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import noppes.npcs.controllers.data.Faction;
import org.lwjgl.opengl.GL11;
import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import bq_npc_integration.rewards.RewardNpcFaction;
import bq_npc_integration.storage.NpcFactionDB;

public class GuiRewardNpcFaction extends GuiElement implements IGuiEmbedded
{
	private final RewardNpcFaction reward;
	private final Minecraft mc;
	
	private final int posX;
	private final int posY;
	private final int sizeX;
	private final int sizeY;
	
	public GuiRewardNpcFaction(RewardNpcFaction reward, int posX, int posY, int sizeX, int sizeY)
	{
		this.mc = Minecraft.getMinecraft();
		this.reward = reward;
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTick)
	{
		Faction fact = NpcFactionDB.INSTANCE.getValue(reward.factionID);
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
		mc.fontRenderer.drawString(factName, (int)((posX + sizeX/2 - mc.fontRenderer.getStringWidth(factName)/1.5F)/1.5F), (int)((posY + sizeY/2 - 16)/1.5F), getTextColor(), false);
		mc.fontRenderer.drawString(txt2, (int)((posX + sizeX/2 - mc.fontRenderer.getStringWidth(txt2)/1.5F)/1.5F), (int)((posY + sizeY/2)/1.5F), getTextColor(), false);
		GL11.glPopMatrix();
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
