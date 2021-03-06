package bq_npc_integration.tasks.factory;

import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcQuest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryTaskQuest implements IFactoryData<ITask,NBTTagCompound>
{
	public static final FactoryTaskQuest INSTANCE = new FactoryTaskQuest();
	
	private final ResourceLocation ID = new ResourceLocation(BQ_NPCs.MODID, "npc_quest");
	
	private FactoryTaskQuest()
	{
	}
	
	@Override
	public ResourceLocation getRegistryName()
	{
		return ID;
	}

	@Override
	public TaskNpcQuest createNew()
	{
		return new TaskNpcQuest();
	}

	@Override
	public TaskNpcQuest loadFromData(NBTTagCompound json)
	{
		TaskNpcQuest task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
