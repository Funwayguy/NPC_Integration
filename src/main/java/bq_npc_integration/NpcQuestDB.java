package bq_npc_integration;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.world.WorldEvent;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestController;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class NpcQuestDB
{
	public static HashMap<Integer, Quest> npcQuests = new HashMap<Integer, Quest>();
	
	public static void UpdateClients()
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(new QuestingPacket(NpcPacketType.SYNC_QUESTS.GetLocation(), tags));
	}
	
	public static void SendDatabase(EntityPlayerMP player)
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(new QuestingPacket(NpcPacketType.SYNC_QUESTS.GetLocation(), tags), player);
	}
	
	public static void readFromNBT(NBTTagCompound tags)
	{
		npcQuests = new HashMap<Integer, Quest>();
		NBTTagList list = tags.getTagList("npcQuests", 10);
		for(int i = 0; i < list.tagCount(); i++)
		{
			Quest q = new Quest();
			q.readNBT(list.getCompoundTagAt(i));
			npcQuests.put(q.id, q);
		}
	}
	
	public static void writeToNBT(NBTTagCompound tags)
	{
		NBTTagList list = new NBTTagList();
		for(Quest q : QuestController.instance.quests.values())
		{
			if(q == null)
			{
				continue;
			}
			
			NBTTagCompound t = new NBTTagCompound();
			q.writeToNBT(t);
			list.appendTag(t);
		}
		tags.setTag("npcQuests", list);
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP)
		{
			SendDatabase((EntityPlayerMP)event.player);
		}
	}
	
	boolean loaded = false;
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if(event.world.isRemote || loaded)
		{
			return;
		}
		
		// Probably not necessary server side but just in case
		for(Quest q : QuestController.instance.quests.values())
		{
			if(q == null)
			{
				continue;
			}
			
			npcQuests.put(q.id, q);
		}
		
		loaded = true;
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if(!event.world.isRemote && !MinecraftServer.getServer().isServerRunning())
		{
			loaded = false;
			npcQuests.clear();
		}
	}
}
