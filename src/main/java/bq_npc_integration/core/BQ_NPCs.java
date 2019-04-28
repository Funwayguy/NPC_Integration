package bq_npc_integration.core;

import bq_npc_integration.core.proxies.CommonProxy;
import bq_npc_integration.storage.CommandReloadQuests;
import bq_npc_integration.storage.StorageHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;

@Mod(modid = BQ_NPCs.MODID, version = "@VERSION@")
public class BQ_NPCs
{
    public static final String MODID = "bq_npc_integration";
    public static final String PROXY = "bq_npc_integration.core.proxies";
    public static final String CHANNEL = "BQ_NPC_INT";
	
	@Instance(MODID)
	public static BQ_NPCs instance;
	
	@SidedProxy(clientSide = PROXY + ".ClientProxy", serverSide = PROXY + ".CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network;
	public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
    	
    	proxy.registerHandlers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
    {
        MinecraftServer server = event.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;
        
        manager.registerCommand(new CommandReloadQuests());
    }
    
    @EventHandler
    public void serverShutdown(FMLServerStoppedEvent event)
    {
        StorageHandler.INSTANCE.unloadDatabases();
    }
}
