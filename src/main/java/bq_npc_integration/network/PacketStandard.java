package bq_npc_integration.network;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.Level;
import bq_npc_integration.core.BQ_NPCs;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketStandard implements IMessage
{
	NBTTagCompound tags = new NBTTagCompound();
	
	public PacketStandard()
	{
	}
	
	public PacketStandard(NBTTagCompound payload)
	{
		tags = payload;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		if(BQ_NPCs.proxy.isClient() && Minecraft.getMinecraft().thePlayer != null)
		{
			tags.setString("Sender", Minecraft.getMinecraft().thePlayer.getUniqueID().toString());
			tags.setInteger("Dimension", Minecraft.getMinecraft().thePlayer.dimension);
		}
		
		ByteBufUtils.writeTag(buf, tags);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketStandard,IMessage>
	{
		@Override
		public IMessage onMessage(PacketStandard message, MessageContext ctx)
		{
			if(message == null || message.tags == null)
			{
				BQ_NPCs.logger.log(Level.ERROR, "A critical NPE error occured during while handling a BQ Standard packet server side", new NullPointerException());
				return null;
			}
			
			int ID = !message.tags.hasKey("ID")? -1 : message.tags.getInteger("ID");
			
			if(ID < 0)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Recieved a packet server side with an invalid ID", new NullPointerException());
				return null;
			}
			
			EntityPlayer player = null;
			
			if(message.tags.hasKey("Sender"))
			{
				try
				{
					WorldServer world = MinecraftServer.getServer().worldServerForDimension(message.tags.getInteger("Dimension"));
					player = world.func_152378_a(UUID.fromString(message.tags.getString("Sender")));
				} catch(Exception e)
				{
					
				}
			}
			
			// Process packet
			
			return null;
		}
	}
	
	public static class HandlerClient implements IMessageHandler<PacketStandard,IMessage>
	{
		@Override
		public IMessage onMessage(PacketStandard message, MessageContext ctx)
		{
			if(message == null || message.tags == null)
			{
				BQ_NPCs.logger.log(Level.ERROR, "A critical NPE error occured during while handling a BQ Standard packet client side", new NullPointerException());
				return null;
			}
			
			int ID = !message.tags.hasKey("ID")? -1 : message.tags.getInteger("ID");
			
			if(ID < 0)
			{
				BQ_NPCs.logger.log(Level.ERROR, "Recieved a packet client side with an invalid ID", new NullPointerException());
				return null;
			}
			
			// Process packet
			
			return null;
		}
	}
}
