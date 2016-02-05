package bq_npc_integration;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TutRecipe implements IRecipe
{
	ItemStack output;
	
	@Override
	public boolean matches(InventoryCrafting invo, World world)
	{
		output = null;
		
		ItemStack handle = null;
		ItemStack crystal = null;
		boolean generator = false;
		boolean emitter = false;
		
		for(int i = 0; i < invo.getSizeInventory(); i++)
		{
			ItemStack stack = invo.getStackInSlot(i);
			
			if(stack == null || stack.getItem() == null)
			{
				continue;
			}
			
			Item item = stack.getItem();
			
			if(item == Items.stick && handle == null) // Handle
			{
				handle = stack;
			} else if(item == Items.nether_star && crystal == null) // Crystal
			{
				crystal = stack;
			} else if(item == Item.getItemFromBlock(Blocks.beacon) && !emitter) // Emitter
			{
				emitter = true;
			} else if(item == Item.getItemFromBlock(Blocks.furnace) && !generator) // Generator
			{
				generator = true;
			} else
			{
				return false;
			}
		}
		
		if(handle == null || crystal == null || !generator || !emitter)
		{
			return false;
		}
		
		output = new ItemStack(Items.diamond_sword); // Sabre
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("handle", handle.getItemDamage()); // Assuming you use metadata for handle types
		tags.setInteger("color", crystal.getTagCompound().getInteger("color"));
		output.setTagCompound(tags);
		
		return true;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting invo)
	{
		return output.copy();
	}
	
	@Override
	public int getRecipeSize()
	{
		return 4;
	}
	
	@Override
	public ItemStack getRecipeOutput()
	{
		return output.copy();
	}
	
}
