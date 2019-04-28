package bq_npc_integration.storage;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;

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
		if(event.player.worldObj.isRemote || !(event.player instanceof EntityPlayerMP)) return;
		
		if(!loaded)
		{
			NpcQuestDB.INSTANCE.loadDatabase();
			NpcDialogDB.INSTANCE.loadDatabase();
			NpcFactionDB.INSTANCE.loadDatabase();
			
			System.out.println("Loaded quest DB");
			loaded = true;
		}
		
		EntityPlayerMP player = (EntityPlayerMP)event.player;
		
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(NpcQuestDB.INSTANCE.getSyncPacket(), player);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(NpcDialogDB.INSTANCE.getSyncPacket(), player);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(NpcFactionDB.INSTANCE.getSyncPacket(), player);
	}
	
	public static void reloadDatabases()
	{
		NpcQuestDB.INSTANCE.loadDatabase();
		NpcDialogDB.INSTANCE.loadDatabase();
		NpcFactionDB.INSTANCE.loadDatabase();
		
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(NpcQuestDB.INSTANCE.getSyncPacket());
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(NpcDialogDB.INSTANCE.getSyncPacket());
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(NpcFactionDB.INSTANCE.getSyncPacket());
	}
}
