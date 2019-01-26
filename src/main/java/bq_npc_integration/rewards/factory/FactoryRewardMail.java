package bq_npc_integration.rewards.factory;

import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.RewardNpcMail;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryRewardMail implements IFactory<RewardNpcMail>
{
	public static final FactoryRewardMail INSTANCE = new FactoryRewardMail();
	
	private final ResourceLocation ID = new ResourceLocation(BQ_NPCs.MODID, "npc_mail");
	
	private FactoryRewardMail()
	{
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}

	@Override
	public RewardNpcMail createNew()
	{
		return new RewardNpcMail();
	}

	@Override
	public RewardNpcMail loadFromNBT(NBTTagCompound json)
	{
		RewardNpcMail task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
