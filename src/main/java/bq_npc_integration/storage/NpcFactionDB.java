package bq_npc_integration.storage;

import betterquesting.api.misc.IDataSync;
import betterquesting.api.network.QuestingPacket;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import bq_npc_integration.network.NpcPacketType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;

import java.util.Map.Entry;

public class NpcFactionDB extends SimpleDatabase<Faction> implements IDataSync
{
	public static final NpcFactionDB INSTANCE = new NpcFactionDB();
	
	public void loadDatabase()
	{
		this.reset();
		
		System.out.println("Loading " + FactionController.getInstance().factions.size() + " factions...");
		
		for(Entry<Integer, Faction> entry : FactionController.getInstance().factions.entrySet())
		{
			add(entry.getKey(), entry.getValue());
		}
	}
	
	public void readPacket(NBTTagCompound tag)
	{
	    System.out.println("Reading faction packet: " + tag);
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
		for(DBEntry<Faction> f : getEntries())
		{
		    NBTTagCompound fTag = new NBTTagCompound();
		    f.getValue().writeNBT(fTag);
			tags.appendTag(fTag);
		}
		
		return tags;
	}
}
