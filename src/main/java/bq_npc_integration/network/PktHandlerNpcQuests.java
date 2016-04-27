package bq_npc_integration.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import betterquesting.network.PktHandler;
import bq_npc_integration.NpcQuestDB;

public class PktHandlerNpcQuests extends PktHandler
{
	@Override
	public IMessage handleClient(NBTTagCompound data)
	{
		NpcQuestDB.readFromNBT(data);
		return null;
	}
	
	@Override
	public IMessage handleServer(EntityPlayer sender, NBTTagCompound data)
	{
		return null;
	}
}
