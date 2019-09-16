package bq_npc_integration.network;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.network.QuestingPacket;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.storage.NpcDialogDB;
import bq_npc_integration.storage.NpcFactionDB;
import bq_npc_integration.storage.NpcQuestDB;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class NetNpcSync
{
	private static final ResourceLocation ID_NAME = new ResourceLocation("bq_npc_integration:sync_npc_data");
	
	public static void registerHandler()
    {
        if(BQ_NPCs.proxy.isClient())
        {
            QuestingAPI.getAPI(ApiReference.PACKET_REG).registerClientHandler(ID_NAME, NetNpcSync::onClient);
        }
    }
    
    public static void sendSync(@Nullable EntityPlayerMP player, int type)
    {
        if(type < 0 || type > 2) return;
        
        NBTTagCompound payload = new NBTTagCompound();
        payload.setInteger("type", type);
        
        switch(type)
        {
            case 0:
            {
                payload.setTag("data", NpcQuestDB.INSTANCE.writeToNBT(new NBTTagList()));
                break;
            }
            case 1:
            {
                payload.setTag("data", NpcFactionDB.INSTANCE.writeToNBT(new NBTTagList()));
                break;
            }
            case 2:
            {
                payload.setTag("data", NpcDialogDB.INSTANCE.writeToNBT(new NBTTagList()));
                break;
            }
        }
        
        if(player != null)
        {
            QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToPlayers(new QuestingPacket(ID_NAME, payload), player);
        } else
        {
            QuestingAPI.getAPI(ApiReference.PACKET_SENDER).sendToAll(new QuestingPacket(ID_NAME, payload));
        }
    }
	
	private static void onClient(NBTTagCompound message)
	{
	    if(!message.hasKey("type", 99))
        {
            BQ_NPCs.logger.error("Recieved invalid sync type: NULL");
            return;
        }
	    
	    NBTTagList data = message.getTagList("data", 10);
	    int type = message.getInteger("type");
	    
	    switch(type)
        {
            case 0:
            {
                NpcQuestDB.INSTANCE.readFromNBT(data);
                break;
            }
            case 1:
            {
                NpcFactionDB.INSTANCE.readFromNBT(data);
                break;
            }
            case 2:
            {
                NpcDialogDB.INSTANCE.readFromNBT(data);
                break;
            }
            default:
            {
                BQ_NPCs.logger.error("Recieved invalid sync type: " + type);
            }
        }
	}
}
