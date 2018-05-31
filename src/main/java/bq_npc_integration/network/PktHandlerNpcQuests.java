package bq_npc_integration.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import betterquesting.api.network.IPacketHandler;
import bq_npc_integration.storage.NpcQuestDB;

public class PktHandlerNpcQuests implements IPacketHandler
{
	private final ResourceLocation ID;
	
	public PktHandlerNpcQuests()
	{
		this.ID = NpcPacketType.SYNC_QUESTS.GetLocation();
	}

	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}
	
	@Override
	public void handleClient(NBTTagCompound data)
	{
		NpcQuestDB.INSTANCE.readPacket(data);
	}
	
	@Override
	public void handleServer(NBTTagCompound data, EntityPlayerMP sender)
	{
	}
}
