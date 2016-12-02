package bq_npc_integration.tasks.factory;

import net.minecraft.util.ResourceLocation;
import com.google.gson.JsonObject;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcDialog;

public class FactoryTaskDialog implements IFactory<TaskNpcDialog>
{
	public static final FactoryTaskDialog INSTANCE = new FactoryTaskDialog();
	
	private final ResourceLocation ID = new ResourceLocation(BQ_NPCs.MODID, "npc_dialog");
	
	private FactoryTaskDialog()
	{
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}

	@Override
	public TaskNpcDialog createNew()
	{
		return new TaskNpcDialog();
	}

	@Override
	public TaskNpcDialog loadFromJson(JsonObject json)
	{
		TaskNpcDialog task = createNew();
		task.readFromJson(json, EnumSaveType.CONFIG);
		return task;
	}
}
