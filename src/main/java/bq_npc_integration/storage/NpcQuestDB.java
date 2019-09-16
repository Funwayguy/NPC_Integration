package bq_npc_integration.storage;

import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;

import java.util.Map.Entry;

public class NpcQuestDB extends SimpleDatabase<Quest>
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
	
	public void readFromNBT(NBTTagList tags)
	{
		this.reset();
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Quest q = new Quest(null);
			q.readNBT(tags.getCompoundTagAt(i));
			this.add(q.id, q);
		}
	}
	
	public NBTTagList writeToNBT(NBTTagList tags)
	{
		for(DBEntry<Quest> q : getEntries())
		{
			tags.appendTag(q.getValue().writeToNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
