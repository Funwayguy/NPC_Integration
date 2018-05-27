package bq_npc_integration.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import betterquesting.api.network.IPacketHandler;
import bq_npc_integration.storage.NpcFactionDB;

public class PktHandlerNpcFactions implements IPacketHandler
{
	private final ResourceLocation ID;
	
	public PktHandlerNpcFactions()
	{
		this.ID = NpcPacketType.SYNC_FACTIONS.GetLocation();
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}
	
	@Override
	public void handleClient(NBTTagCompound data)
	{
		NpcFactionDB.INSTANCE.readFromNBT(data);
	}
	
	@Override
	public void handleServer(NBTTagCompound data, EntityPlayerMP sender)
	{
	}
	
}
