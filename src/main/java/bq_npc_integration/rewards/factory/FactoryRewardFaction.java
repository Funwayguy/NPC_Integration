package bq_npc_integration.rewards.factory;

import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.RewardNpcFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryRewardFaction implements IFactory<RewardNpcFaction>
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
	public RewardNpcFaction loadFromNBT(NBTTagCompound json)
	{
		RewardNpcFaction task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
