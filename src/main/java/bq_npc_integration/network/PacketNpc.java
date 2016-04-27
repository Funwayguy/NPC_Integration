package bq_npc_integration.network;

import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;
import betterquesting.core.BetterQuesting;
import betterquesting.network.PktHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketNpc implements IMessage
{
	NBTTagCompound tags = new NBTTagCompound();
	
	public PacketNpc()
	{
	}
	
	private PacketNpc(NBTTagCompound tags)
	{
		this.tags = tags;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tags = ByteBufUtils.readTag(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, tags);
	}
	
	public static class HandleServer implements IMessageHandler<PacketNpc, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNpc message, MessageContext ctx)
		{
			if(message == null || message.tags == null)
			{
				BetterQuesting.logger.log(Level.ERROR, "A critical NPE error occured during while handling a BetterQuesting packet server side", new NullPointerException());
				return null;
			}
			
			int ID = !message.tags.hasKey("ID")? -1 : message.tags.getInteger("ID");
			
			if(ID < 0 || ID >= PacketNPCType.values().length)
			{
				BetterQuesting.logger.log(Level.WARN, "Recieved a packet server side with an invalid ID");
				return null;
			}
			
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			
			PacketNPCType dataType = PacketNPCType.values()[ID];
			PktHandler handler = pktHandlers.get(dataType);
			
			if(handler != null)
			{
				return handler.handleServer(player, message.tags);
			} else
			{
				BetterQuesting.logger.log(Level.ERROR, "Unable to find valid packet handler for data type: " + dataType.toString());
				return null;
			}
		}
	}
	
	public static class HandleClient implements IMessageHandler<PacketNpc, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNpc message, MessageContext ctx)
		{
			if(message == null || message.tags == null)
			{
				BetterQuesting.logger.log(Level.ERROR, "A critical NPE error occured during while handling a BetterQuesting packet client side", new NullPointerException());
				return null;
			}
			
			int ID = !message.tags.hasKey("ID")? -1 : message.tags.getInteger("ID");
			
			if(ID < 0 || ID >= PacketNPCType.values().length)
			{
				BetterQuesting.logger.log(Level.WARN, "Recieved a packet client side with an invalid ID");
				return null;
			}
			
			PacketNPCType dataType = PacketNPCType.values()[ID];
			PktHandler handler = pktHandlers.get(dataType);
			
			if(handler != null)
			{
				return handler.handleClient(message.tags);
			} else
			{
				BetterQuesting.logger.log(Level.ERROR, "Unable to find valid packet handler for data type: " + dataType.toString());
				return null;
			}
		}
	}
	
	static HashMap<PacketNPCType, PktHandler> pktHandlers = new HashMap<PacketNPCType, PktHandler>();
	
	static
	{
		pktHandlers.put(PacketNPCType.SYNC_QUESTS, 	new PktHandlerNpcQuests());
		//pktHandlers.put(PacketNPCType.SYNC_DIALOG, 	new PktHandlerNpcDialogs());
	}
	
	public enum PacketNPCType
	{
		SYNC_QUESTS,
		SYNC_DIALOG;
		
		public PacketNpc makePacket(NBTTagCompound payload)
		{
			payload.setInteger("ID", this.ordinal()); // Ensure this is set correctly
			return new PacketNpc(payload);
		}
	}
}
