package bq_npc_integration.rewards.factory;

import betterquesting.api.questing.rewards.IReward;
import betterquesting.api2.registry.IFactoryData;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.RewardNpcFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryRewardFaction implements IFactoryData<IReward,NBTTagCompound>
{
	public static final FactoryRewardFaction INSTANCE = new FactoryRewardFaction();
	
	private final ResourceLocation ID = new ResourceLocation(BQ_NPCs.MODID, "npc_faction");
	
	private FactoryRewardFaction()
	{
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}

	@Override
	public RewardNpcFaction createNew()
	{
		return new RewardNpcFaction();
	}

	@Override
	public RewardNpcFaction loadFromData(NBTTagCompound json)
	{
		RewardNpcFaction task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
