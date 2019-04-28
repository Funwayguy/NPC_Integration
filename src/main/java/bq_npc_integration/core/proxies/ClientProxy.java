package bq_npc_integration.core.proxies;

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
	}
	
	@Override
	public void registerExpansion()
	{
		super.registerExpansion();
	}
}
