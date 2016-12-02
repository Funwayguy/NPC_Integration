package bq_npc_integration.tasks.factory;

import net.minecraft.util.ResourceLocation;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcFaction;
import com.google.gson.JsonObject;

public class FactoryTaskFaction implements IFactory<TaskNpcFaction>
{
	public static final FactoryTaskFaction INSTANCE = new FactoryTaskFaction();
	
	private final ResourceLocation ID = new ResourceLocation(BQ_NPCs.MODID, "npc_faction");
	
	private FactoryTaskFaction()
	{
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}

	@Override
	public TaskNpcFaction createNew()
	{
		return new TaskNpcFaction();
	}

	@Override
	public TaskNpcFaction loadFromJson(JsonObject json)
	{
		TaskNpcFaction task = createNew();
		task.readFromJson(json, EnumSaveType.CONFIG);
		return task;
	}
}
