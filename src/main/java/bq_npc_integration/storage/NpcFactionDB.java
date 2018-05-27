package bq_npc_integration.storage;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.FactionController;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Faction;

public class NpcFactionDB
{
	public static final NpcFactionDB INSTANCE = new NpcFactionDB();
	
	private final HashMap<Integer, Faction> npcFactions = new HashMap<Integer, Faction>();
	
	private NpcFactionDB()
	{
	}
	
	public Faction getFaction(int id)
	{
		return this.npcFactions.get(id);
	}
	
	public void reset()
	{
		this.npcFactions.clear();
	}
	
	public void loadDatabase()
	{
		this.reset();
		
		npcFactions.putAll(FactionController.instance.factions);
	}
	
	public void UpdateCients()
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(new QuestingPacket(NpcPacketType.SYNC_FACTIONS.GetLocation(), tags));
	}
	
	public void SendDatabase(EntityPlayerMP player)
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(new QuestingPacket(NpcPacketType.SYNC_FACTIONS.GetLocation(), tags), player);
	}
	
	public void readFromNBT(NBTTagCompound tags)
	{
		this.reset();
		NBTTagList list = tags.getTagList("npcFactions", 10);
		for(int i = 0; i < list.tagCount(); i++)
		{
			Faction f = new Faction();
			f.readNBT(list.getCompoundTagAt(i));
			npcFactions.put(f.id, f);
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound tags)
	{
		NBTTagList list = new NBTTagList();
		
		for(Faction f : npcFactions.values())
		{
			if(f == null)
			{
				continue;
			}
			
			NBTTagCompound fTag = new NBTTagCompound();
			f.writeNBT(fTag);
			list.appendTag(fTag);
		}
		
		tags.setTag("npcFactions", list);
		
		return tags;
	}
}
