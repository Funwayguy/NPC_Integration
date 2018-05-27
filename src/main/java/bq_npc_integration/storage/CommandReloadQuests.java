package bq_npc_integration.storage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class CommandReloadQuests extends CommandBase
{
    @Override
    public String getName()
    {
        return "bq_npcs";
    }
    
    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/bq_npcs reload";
    }
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length != 1)
        {
            throw new WrongUsageException(getUsage(sender));
        }
        
        if(args[0].equalsIgnoreCase("reload"))
        {
            StorageHandler.reloadDatabases();
            sender.sendMessage(new TextComponentString("Reloaded NPC quest cache"));
            return;
        }
        
        throw new WrongUsageException(getUsage(sender));
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] strings, BlockPos pos)
    {
        List<String> list = new ArrayList<>();
        
        if(strings.length == 1)
        {
            list.add("reload");
        }
        
        return list;
    }
}
