package bq_npc_integration.handlers;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import bq_npc_integration.client.gui.GuiBQNConfig;
import cpw.mods.fml.client.IModGuiFactory;

public class ConfigGuiFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance)
	{
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return GuiBQNConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}
}
