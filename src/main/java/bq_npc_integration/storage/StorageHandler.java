package bq_npc_integration.storage;

import bq_npc_integration.network.NetNpcSync;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class StorageHandler
{
    public static final StorageHandler INSTANCE = new StorageHandler();
    
	private boolean loaded = false;
	
	public void unloadDatabases()
	{
		NpcQuestDB.INSTANCE.reset();
		NpcDialogDB.INSTANCE.reset();
		NpcFactionDB.INSTANCE.reset();
		
		loaded = false;
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(event.player.world.isRemote || !(event.player instanceof EntityPlayerMP)) return;
		
		if(!loaded)
		{
			NpcQuestDB.INSTANCE.loadDatabase();
			NpcDialogDB.INSTANCE.loadDatabase();
			NpcFactionDB.INSTANCE.loadDatabase();
			loaded = true;
		}
		
		EntityPlayerMP player = (EntityPlayerMP)event.player;
        
        NetNpcSync.sendSync(player, 0);
        NetNpcSync.sendSync(player, 1);
        NetNpcSync.sendSync(player, 2);
	}
	
	public static void reloadDatabases()
	{
		NpcQuestDB.INSTANCE.loadDatabase();
		NpcDialogDB.INSTANCE.loadDatabase();
		NpcFactionDB.INSTANCE.loadDatabase();
        
        NetNpcSync.sendSync(null, 0);
        NetNpcSync.sendSync(null, 1);
        NetNpcSync.sendSync(null, 2);
	}
}
