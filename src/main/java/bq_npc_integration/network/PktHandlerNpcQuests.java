package bq_npc_integration.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import betterquesting.network.handlers.PktHandler;
import bq_npc_integration.NpcQuestDB;

public class PktHandlerNpcQuests extends PktHandler
{
	@Override
	public void handleClient(NBTTagCompound data)
	{
		NpcQuestDB.readFromNBT(data);
	}
	
	@Override
	public void handleServer(EntityPlayerMP sender, NBTTagCompound data)
	{
	}
}
