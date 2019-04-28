package bq_npc_integration.storage;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class CommandReloadQuests extends CommandBase
{
    @Nonnull
    @Override
    public String getCommandName()
    {
        return "bq_npcs";
    }
    
    @Nonnull
    @Override
    public String getCommandUsage(@Nonnull ICommandSender sender)
    {
        return "/bq_npcs reload";
    }
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException
    {
        if(args.length != 1)
        {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        
        if(args[0].equalsIgnoreCase("reload"))
        {
            StorageHandler.reloadDatabases();
            sender.addChatMessage(new TextComponentString("Reloaded NPC quest cache"));
            return;
        }
        
        throw new WrongUsageException(getCommandUsage(sender));
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Nonnull
	@Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] strings, BlockPos pos)
    {
        return strings.length == 1 ? Collections.singletonList("reload") : Collections.emptyList();
    }
}
