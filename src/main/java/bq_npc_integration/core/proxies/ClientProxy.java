package bq_npc_integration.core.proxies;

import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.network.PacketStandard;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		
    	BQ_NPCs.instance.network.registerMessage(PacketStandard.HandlerClient.class, PacketStandard.class, 0, Side.CLIENT);
	}
	
	@Override
	public void registerThemes()
	{
	}
}
