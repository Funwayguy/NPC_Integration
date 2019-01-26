package bq_npc_integration.tasks;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.cache.CapabilityProviderQuestCache;
import betterquesting.api2.storage.DBEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event)
    {
        if(!(event.getEntityLiving() instanceof EntityPlayer) || event.getEntityLiving().world.isRemote || event.getEntityLiving().ticksExisted % 20 != 0 || QuestingAPI.getAPI(ApiReference.SETTINGS).getProperty(NativeProps.EDIT_MODE)) return;
        
        EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        betterquesting.api2.cache.QuestCache qc = player.getCapability(CapabilityProviderQuestCache.CAP_QUEST_CACHE, null);
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
