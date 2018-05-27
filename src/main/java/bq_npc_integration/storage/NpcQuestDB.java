package bq_npc_integration.storage;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.QuestController;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Quest;

public class NpcQuestDB
{
	public static final NpcQuestDB INSTANCE = new NpcQuestDB();
	
	private final HashMap<Integer, Quest> npcQuests = new HashMap<>();
	
	private NpcQuestDB()
	{
	}
	
	public Quest getQuest(int id)
	{
		return this.npcQuests.get(id);
	}
	
	public void reset()
	{
		this.npcQuests.clear();
	}
	
	public void loadDatabase()
	{
		this.reset();
		this.npcQuests.putAll(QuestController.instance.quests);
	}
	
	public void UpdateClients()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags = writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(new QuestingPacket(NpcPacketType.SYNC_QUESTS.GetLocation(), tags));
	}
	
	public void SendDatabase(EntityPlayerMP player)
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags = writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(new QuestingPacket(NpcPacketType.SYNC_QUESTS.GetLocation(), tags), player);
	}
	
	public void readFromNBT(NBTTagCompound tags)
	{
		this.reset();
		NBTTagList list = tags.getTagList("npcQuests", 10);
		for(int i = 0; i < list.tagCount(); i++)
		{
			Quest q = new Quest(null);
			q.readNBT(list.getCompoundTagAt(i));
			npcQuests.put(q.id, q);
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound tags)
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
		
		return tags;
	}
}
