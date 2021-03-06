package bq_npc_integration.tasks.factory;

import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcDialog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryTaskDialog implements IFactoryData<ITask, NBTTagCompound>
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
	public TaskNpcDialog loadFromData(NBTTagCompound json)
	{
		TaskNpcDialog task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
