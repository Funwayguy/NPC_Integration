package bq_npc_integration.core.proxies;

import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.questing.rewards.IReward;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import betterquesting.api2.registry.IRegistry;
import bq_npc_integration.network.NetNpcSync;
import bq_npc_integration.rewards.factory.FactoryRewardFaction;
import bq_npc_integration.rewards.factory.FactoryRewardMail;
import bq_npc_integration.storage.StorageHandler;
import bq_npc_integration.tasks.factory.FactoryTaskDialog;
import bq_npc_integration.tasks.factory.FactoryTaskFaction;
import bq_npc_integration.tasks.factory.FactoryTaskQuest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public boolean isClient()
	{
		return false;
	}
	
	public void registerHandlers()
	{
		MinecraftForge.EVENT_BUS.register(StorageHandler.INSTANCE);
  
		NetNpcSync.registerHandler();
        
        IRegistry<IFactoryData<ITask, NBTTagCompound>, ITask> tskReg = QuestingAPI.getAPI(ApiReference.TASK_REG);
		tskReg.register(FactoryTaskQuest.INSTANCE);
		tskReg.register(FactoryTaskDialog.INSTANCE);
		tskReg.register(FactoryTaskFaction.INSTANCE);
		
        IRegistry<IFactoryData<IReward, NBTTagCompound>, IReward> rwdReg = QuestingAPI.getAPI(ApiReference.REWARD_REG);
		rwdReg.register(FactoryRewardMail.INSTANCE);
		rwdReg.register(FactoryRewardFaction.INSTANCE);
	}

	public void registerExpansion()
	{
	}
}
