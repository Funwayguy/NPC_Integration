package bq_npc_integration.tasks;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.cache.QuestCache;
import betterquesting.api2.storage.DBEntry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EventHandler
{
    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        if(!(event.entityLiving instanceof EntityPlayer) || event.entityLiving.worldObj.isRemote || event.entityLiving.ticksExisted % 20 != 0 || QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE)) return;
        
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        QuestCache qc = (QuestCache)player.getExtendedProperties(QuestCache.LOC_QUEST_CACHE.toString());
		if(qc == null) return;
		
		for(DBEntry<IQuest> entry : QuestingAPI.getAPI(ApiReference.QUEST_DB).bulkLookup(qc.getActiveQuests()))
		{
		    for(DBEntry<ITask> task : entry.getValue().getTasks().getEntries())
            {
                if(task.getValue() instanceof ITaskTickable)
                {
                    ((ITaskTickable)task.getValue()).tickTask(entry.getValue(), player);
                }
            }
		}
    }
}
