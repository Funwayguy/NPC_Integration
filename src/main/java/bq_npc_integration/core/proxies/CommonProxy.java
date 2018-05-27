package bq_npc_integration.core.proxies;

import net.minecraftforge.common.MinecraftForge;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import bq_npc_integration.network.PktHandlerNpcDialogs;
import bq_npc_integration.network.PktHandlerNpcFactions;
import bq_npc_integration.network.PktHandlerNpcQuests;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import bq_npc_integration.rewards.factory.FactoryRewardMail;
import bq_npc_integration.storage.StorageHandler;
import bq_npc_integration.tasks.factory.FactoryTaskDialog;
import bq_npc_integration.tasks.factory.FactoryTaskFaction;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new StorageHandler());
	}

	public void registerExpansion()
	{
		QuestingAPI.getAPI(ApiReference.PACKET_REG).registerHandler(new PktHandlerNpcQuests());
		QuestingAPI.getAPI(ApiReference.PACKET_REG).registerHandler(new PktHandlerNpcDialogs());
		QuestingAPI.getAPI(ApiReference.PACKET_REG).registerHandler(new PktHandlerNpcFactions());
    	
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskQuest.INSTANCE);
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskDialog.INSTANCE);
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskFaction.INSTANCE);
		
		QuestingAPI.getAPI(ApiReference.REWARD_REG).registerReward(FactoryRewardMail.INSTANCE);
		QuestingAPI.getAPI(ApiReference.REWARD_REG).registerReward(FactoryRewardFaction.INSTANCE);
	}
}
