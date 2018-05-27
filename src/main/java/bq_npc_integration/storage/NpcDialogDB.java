package bq_npc_integration.storage;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.DialogController;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.network.NpcPacketType;
import noppes.npcs.controllers.data.Dialog;

public class NpcDialogDB
{
	public static final NpcDialogDB INSTANCE = new NpcDialogDB();
	
	private final HashMap<Integer, Dialog> npcDialog = new HashMap<>();
	
	private NpcDialogDB()
	{
	}
	
	public Dialog getDialog(int id)
	{
		return this.npcDialog.get(id);
	}
	
	public void reset()
	{
		this.npcDialog.clear();
	}
	
	public void loadDatabase()
	{
		this.reset();
		this.npcDialog.putAll(DialogController.instance.dialogs);
	}
	
	public void UpdateCients()
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(new QuestingPacket(NpcPacketType.SYNC_DIALOG.GetLocation(), tags));
	}
	
	public void SendDatabase(EntityPlayerMP player)
	{
		NBTTagCompound tags = new NBTTagCompound();
		writeToNBT(tags);
		QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayer(new QuestingPacket(NpcPacketType.SYNC_DIALOG.GetLocation(), tags), player);
	}
	
	public void readFromNBT(NBTTagCompound tags)
	{
		this.reset();
		NBTTagList list = tags.getTagList("npcDialogs", 10);
		for(int i = 0; i < list.tagCount(); i++)
		{
			Dialog d = new Dialog(null);
			d.readNBT(list.getCompoundTagAt(i));
			npcDialog.put(d.id, d);
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound tags)
	{
		NBTTagList list = new NBTTagList();
		
		for(Dialog d : npcDialog.values())
		{
			if(d == null)
			{
				continue;
			}
			
			NBTTagCompound fTag = new NBTTagCompound();
			d.writeToNBT(fTag);
			list.appendTag(fTag);
		}
		
		tags.setTag("npcDialogs", list);
		
		return tags;
	}
}
