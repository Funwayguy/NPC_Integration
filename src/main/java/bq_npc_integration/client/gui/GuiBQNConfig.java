package bq_npc_integration.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.handlers.ConfigHandler;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBQNConfig extends GuiConfig
{
	@SuppressWarnings({"rawtypes", "unchecked"})
	public GuiBQNConfig(GuiScreen parent)
	{
		super(parent, new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), BQ_NPCs.MODID, false, false, BQ_NPCs.NAME);
	}
}
