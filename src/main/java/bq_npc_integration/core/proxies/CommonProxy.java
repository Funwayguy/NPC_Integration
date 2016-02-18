package bq_npc_integration.core.proxies;

import bq_npc_integration.client.gui.UpdateNotification;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		FMLCommonHandler.instance().bus().register(new UpdateNotification());
	}

	public void registerThemes()
	{
	}
}
