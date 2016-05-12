package bq_npc_integration.core;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import betterquesting.network.PacketTypeRegistry;
import betterquesting.quests.rewards.RewardRegistry;
import betterquesting.quests.tasks.TaskRegistry;
import bq_npc_integration.core.proxies.CommonProxy;
import bq_npc_integration.handlers.ConfigHandler;
import bq_npc_integration.network.PktHandlerNpcQuests;
import bq_npc_integration.rewards.RewardNpcFaction;
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
    	
    	PacketTypeRegistry.RegisterType(new PktHandlerNpcQuests(), new ResourceLocation(MODID + ":npc_quests"));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerThemes();
    	
    	TaskRegistry.RegisterTask(TaskNpcQuest.class, new ResourceLocation(MODID + ":npc_quest"));
    	TaskRegistry.RegisterTask(TaskNpcDialog.class, new ResourceLocation(MODID + ":npc_dialog"));
    	TaskRegistry.RegisterTask(TaskNpcFaction.class, new ResourceLocation(MODID + ":npc_faction"));
    	
    	RewardRegistry.RegisterReward(RewardNpcMail.class, new ResourceLocation(MODID + ":npc_mail"));
    	RewardRegistry.RegisterReward(RewardNpcFaction.class, new ResourceLocation(MODID + ":npc_faction"));
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
