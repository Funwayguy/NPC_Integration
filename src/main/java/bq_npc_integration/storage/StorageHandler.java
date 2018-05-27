package bq_npc_integration.storage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class StorageHandler
{
	private static boolean loaded = false;
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if(!loaded || event.getWorld().isRemote || event.getWorld().getMinecraftServer().isServerRunning())
		{
			return;
		}
		
		NpcQuestDB.INSTANCE.reset();
		NpcDialogDB.INSTANCE.reset();
		NpcFactionDB.INSTANCE.reset();
		
		loaded = false;
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(event.player.world.isRemote || !(event.player instanceof EntityPlayerMP))
		{
			return;
		}
		
		if(!loaded)
		{
			NpcQuestDB.INSTANCE.loadDatabase();
			NpcDialogDB.INSTANCE.loadDatabase();
			NpcFactionDB.INSTANCE.loadDatabase();
			
			loaded = true;
		}
		
		EntityPlayerMP player = (EntityPlayerMP)event.player;
		
		NpcQuestDB.INSTANCE.SendDatabase(player);
		NpcDialogDB.INSTANCE.SendDatabase(player);
		NpcFactionDB.INSTANCE.SendDatabase(player);
	}
	
	public static void reloadDatabases()
	{
		NpcQuestDB.INSTANCE.loadDatabase();
		NpcDialogDB.INSTANCE.loadDatabase();
		NpcFactionDB.INSTANCE.loadDatabase();
		
		NpcQuestDB.INSTANCE.UpdateClients();
		NpcDialogDB.INSTANCE.UpdateCients();
		NpcFactionDB.INSTANCE.UpdateCients();
	}
}
