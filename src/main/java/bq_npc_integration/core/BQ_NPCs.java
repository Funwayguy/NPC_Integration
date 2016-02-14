package bq_npc_integration.core;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import betterquesting.quests.rewards.RewardRegistry;
import betterquesting.quests.tasks.TaskRegistry;
import bq_npc_integration.core.proxies.CommonProxy;
import bq_npc_integration.handlers.ConfigHandler;
import bq_npc_integration.rewards.RewardNpcMail;
import bq_npc_integration.tasks.TaskNpcDialog;
import bq_npc_integration.tasks.TaskNpcFaction;
import bq_npc_integration.tasks.TaskNpcQuest;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = BQ_NPCs.MODID, version = BQ_NPCs.VERSION, name = BQ_NPCs.NAME, guiFactory = "bq_npc_integration.handlers.ConfigGuiFactory")
public class BQ_NPCs
{
    public static final String MODID = "bq_npc_integration";
    public static final String VERSION = "BQ_NPC_VER";
    public static final String NAME = "NPC Integration";
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
    	
    	ConfigHandler.config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	ConfigHandler.initConfigs();
    	
    	proxy.registerHandlers();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerThemes();
    	
    	TaskRegistry.RegisterTask(TaskNpcQuest.class, "npc_quest");
    	TaskRegistry.RegisterTask(TaskNpcDialog.class, "npc_dialog");
    	TaskRegistry.RegisterTask(TaskNpcFaction.class, "npc_faction");
    	//TaskRegistry.RegisterTask(TaskNpcMail.class, "npc_mail"); // Players can't send mail to NPCs :(
    	
    	RewardRegistry.RegisterReward(RewardNpcMail.class, "npc_mail");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
