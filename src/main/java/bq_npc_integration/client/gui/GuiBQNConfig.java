package bq_npc_integration.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.handlers.ConfigHandler;

@SideOnly(Side.CLIENT)
public class GuiBQNConfig extends GuiConfig
{
	public GuiBQNConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), BQ_NPCs.MODID, false, false, BQ_NPCs.NAME);
	}
}
