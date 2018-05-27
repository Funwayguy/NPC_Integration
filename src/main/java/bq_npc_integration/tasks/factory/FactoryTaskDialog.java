package bq_npc_integration.tasks.factory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
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
	public TaskNpcDialog loadFromNBT(NBTTagCompound json)
	{
		TaskNpcDialog task = createNew();
		task.readFromNBT(json, EnumSaveType.CONFIG);
		return task;
	}
}
