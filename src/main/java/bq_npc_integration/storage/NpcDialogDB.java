package bq_npc_integration.storage;

import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;

import java.util.Map.Entry;

public class NpcDialogDB extends SimpleDatabase<Dialog>
{
	public static final NpcDialogDB INSTANCE = new NpcDialogDB();
	
	public void loadDatabase()
	{
		this.reset();
		
		for(Entry<Integer, Dialog> entry : DialogController.instance.dialogs.entrySet())
		{
			add(entry.getKey(), entry.getValue());
		}
	}
	
	public void readFromNBT(NBTTagList tags)
	{
		this.reset();
		
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Dialog d = new Dialog(null);
			d.readNBT(tags.getCompoundTagAt(i));
			add(d.id, d);
		}
	}
	
	public NBTTagList writeToNBT(NBTTagList tags)
	{
		for(DBEntry<Dialog> d : getEntries())
		{
			tags.appendTag(d.getValue().writeToNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
