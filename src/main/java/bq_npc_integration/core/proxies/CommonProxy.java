package bq_npc_integration.core.proxies;

import bq_npc_integration.client.gui.UpdateNotification;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.network.PacketStandard;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		FMLCommonHandler.instance().bus().register(new UpdateNotification());
    	
    	BQ_NPCs.instance.network.registerMessage(PacketStandard.HandlerServer.class, PacketStandard.class, 1, Side.SERVER);
	}

	public void registerThemes()
	{
	}
}
