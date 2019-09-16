package bq_npc_integration.storage;

import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.Faction;

import java.util.Map.Entry;

public class NpcFactionDB extends SimpleDatabase<Faction>
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
	
	public void readFromNBT(NBTTagList tags)
	{
		this.reset();
		
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Faction f = new Faction();
			f.readNBT(tags.getCompoundTagAt(i));
			add(f.id, f);
		}
	}
	
	public NBTTagList writeToNBT(NBTTagList tags)
	{
		for(DBEntry<Faction> f : getEntries())
		{
			tags.appendTag(f.getValue().writeNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
