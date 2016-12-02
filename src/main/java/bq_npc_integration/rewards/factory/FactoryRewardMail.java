package bq_npc_integration.rewards.factory;

import net.minecraft.util.ResourceLocation;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.rewards.RewardNpcFaction;
import com.google.gson.JsonObject;

public class FactoryRewardMail implements IFactory<RewardNpcFaction>
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
