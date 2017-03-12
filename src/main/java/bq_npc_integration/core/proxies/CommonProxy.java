package bq_npc_integration.core.proxies;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import bq_npc_integration.NpcQuestDB;
import bq_npc_integration.network.PktHandlerNpcQuests;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import bq_npc_integration.rewards.factory.FactoryRewardMail;
import bq_npc_integration.tasks.factory.FactoryTaskDialog;
import bq_npc_integration.tasks.factory.FactoryTaskFaction;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		FMLCommonHandler.instance().bus().register(new NpcQuestDB());
	}

	public void registerExpansion()
	{
		QuestingAPI.getAPI(ApiReference.PACKET_REG).registerHandler(new PktHandlerNpcQuests());
    	
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskQuest.INSTANCE);
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskDialog.INSTANCE);
		QuestingAPI.getAPI(ApiReference.TASK_REG).registerTask(FactoryTaskFaction.INSTANCE);
		
		QuestingAPI.getAPI(ApiReference.REWARD_REG).registerReward(FactoryRewardMail.INSTANCE);
		QuestingAPI.getAPI(ApiReference.REWARD_REG).registerReward(FactoryRewardFaction.INSTANCE);
	}
}
