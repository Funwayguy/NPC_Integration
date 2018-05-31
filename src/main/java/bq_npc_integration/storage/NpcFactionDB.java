package bq_npc_integration.storage;

import java.util.Map.Entry;
import betterquesting.api.misc.IDataSync;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.FactionController;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Faction;

public class NpcFactionDB extends SimpleDatabase<Faction> implements IDataSync
{
	public static final NpcFactionDB INSTANCE = new NpcFactionDB();
	
	public void loadDatabase()
	{
		this.reset();
		
		for(Entry<Integer, Faction> entry : FactionController.instance.factions.entrySet())
		{
			add(entry.getKey(), entry.getValue());
		}
	}
	
	public void readPacket(NBTTagCompound tag)
	{
		readFromNBT(tag.getTagList("npcFactions", 10));
	}
	
	@Override
	public QuestingPacket getSyncPacket()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setTag("npcFactions", writeToNBT(new NBTTagList()));
		return new QuestingPacket(NpcPacketType.SYNC_FACTIONS.GetLocation(), tags);
	}
	
	private void readFromNBT(NBTTagList tags)
	{
		this.reset();
		
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Faction f = new Faction();
			f.readNBT(tags.getCompoundTagAt(i));
			add(f.id, f);
		}
	}
	
	private NBTTagList writeToNBT(NBTTagList tags)
	{
		NBTTagList list = new NBTTagList();
		
		for(DBEntry<Faction> f : getEntries())
		{
			list.appendTag(f.getValue().writeNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
