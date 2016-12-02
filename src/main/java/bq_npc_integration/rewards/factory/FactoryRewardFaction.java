package bq_npc_integration.rewards.factory;

import net.minecraft.util.ResourceLocation;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.RewardNpcFaction;
import com.google.gson.JsonObject;

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
	public RewardNpcFaction loadFromJson(JsonObject json)
	{
		RewardNpcFaction task = createNew();
		task.readFromJson(json, EnumSaveType.CONFIG);
		return task;
	}
}
