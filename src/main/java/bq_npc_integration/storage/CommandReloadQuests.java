package bq_npc_integration.storage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.List;

public class CommandReloadQuests extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "bq_npcs";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/bq_npcs reload";
    }
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length != 1)
        {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        
        if(args[0].equalsIgnoreCase("reload"))
        {
            StorageHandler.reloadDatabases();
            sender.addChatMessage(new ChatComponentText("Reloaded NPC quest cache"));
            return;
        }
        
        throw new WrongUsageException(getCommandUsage(sender));
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
	@Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] strings)
    {
        return strings.length == 1 ? Collections.singletonList("reload") : Collections.emptyList();
    }
}
