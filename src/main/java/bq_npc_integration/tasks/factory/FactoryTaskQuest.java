package bq_npc_integration.tasks.factory;

import betterquesting.api.misc.IFactory;
import bq_npc_integration.core.BQ_NPCs;
import bq_npc_integration.tasks.TaskNpcQuest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class FactoryTaskQuest implements IFactory<TaskNpcQuest>
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
	public TaskNpcQuest loadFromNBT(NBTTagCompound json)
	{
		TaskNpcQuest task = createNew();
		task.readFromNBT(json);
		return task;
	}
}
