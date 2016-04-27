package bq_npc_integration.core.proxies;

import bq_npc_integration.NpcQuestDB;
import bq_npc_integration.client.gui.UpdateNotification;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.network.PacketNpc;
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
		FMLCommonHandler.instance().bus().register(new NpcQuestDB());
		
		BQ_NPCs.instance.network.registerMessage(PacketNpc.HandleServer.class, PacketNpc.class, 0, Side.SERVER);
	}

	public void registerThemes()
	{
	}
}
