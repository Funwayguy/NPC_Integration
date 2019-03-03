package bq_npc_integration.tasks.factory;

import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryTaskFaction implements IFactoryData<ITask,NBTTagCompound>
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
	public TaskNpcFaction loadFromData(NBTTagCompound json)
	{
		TaskNpcFaction task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
