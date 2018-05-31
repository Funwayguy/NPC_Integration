package bq_npc_integration.storage;

import java.util.Map.Entry;
import betterquesting.api.misc.IDataSync;
import betterquesting.api2.storage.BigDatabase;
import betterquesting.api2.storage.DBEntry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.QuestController;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Quest;

public class NpcQuestDB extends BigDatabase<Quest> implements IDataSync
{
	public static final NpcQuestDB INSTANCE = new NpcQuestDB();
	
	public void loadDatabase()
	{
		this.reset();
		
		for(Entry<Integer, Quest> entry : QuestController.instance.quests.entrySet())
		{
			this.add(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public void readPacket(NBTTagCompound tag)
	{
		readFromNBT(tag.getTagList("npcQuests", 10));
	}
	
	@Override
	public QuestingPacket getSyncPacket()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setTag("npcQuests", writeToNBT(new NBTTagList()));
		return new QuestingPacket(NpcPacketType.SYNC_QUESTS.GetLocation(), tags);
	}
	
	private void readFromNBT(NBTTagList tags)
	{
		this.reset();
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Quest q = new Quest(null);
			q.readNBT(tags.getCompoundTagAt(i));
			this.add(q.id, q);
		}
	}
	
	private NBTTagList writeToNBT(NBTTagList tags)
	{
		for(DBEntry<Quest> q : getEntries())
		{
			tags.appendTag(q.getValue().writeToNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
