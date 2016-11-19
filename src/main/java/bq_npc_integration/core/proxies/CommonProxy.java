package bq_npc_integration.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import bq_npc_integration.NpcQuestDB;
import bq_npc_integration.client.gui.UpdateNotification;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new UpdateNotification());
		MinecraftForge.EVENT_BUS.register(new NpcQuestDB());
	}

	public void registerThemes()
	{
	}
}
