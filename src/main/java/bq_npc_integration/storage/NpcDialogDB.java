package bq_npc_integration.storage;

import java.util.Map.Entry;
import betterquesting.api.misc.IDataSync;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.storage.SimpleDatabase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.DialogController;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Dialog;

public class NpcDialogDB extends SimpleDatabase<Dialog> implements IDataSync
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
	
	@Override
	public void readPacket(NBTTagCompound tag)
	{
		readFromNBT(tag.getTagList("npcDialogs", 10));
	}
	
	@Override
	public QuestingPacket getSyncPacket()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setTag("npcDialogs", writeToNBT(new NBTTagList()));
		return new QuestingPacket(NpcPacketType.SYNC_DIALOG.GetLocation(), tags);
	}
	
	private void readFromNBT(NBTTagList tags)
	{
		this.reset();
		
		for(int i = 0; i < tags.tagCount(); i++)
		{
			Dialog d = new Dialog(null);
			d.readNBT(tags.getCompoundTagAt(i));
			add(d.id, d);
		}
	}
	
	private NBTTagList writeToNBT(NBTTagList tags)
	{
		NBTTagList list = new NBTTagList();
		
		for(DBEntry<Dialog> d : getEntries())
		{
			list.appendTag(d.getValue().writeToNBT(new NBTTagCompound()));
		}
		
		return tags;
	}
}
