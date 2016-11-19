package bq_npc_integration.core;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import betterquesting.network.PacketTypeRegistry;
import betterquesting.quests.rewards.RewardRegistry;
import betterquesting.quests.tasks.TaskRegistry;
import bq_npc_integration.core.proxies.CommonProxy;
import bq_npc_integration.handlers.ConfigHandler;
import bq_npc_integration.network.NpcPacketType;
import bq_npc_integration.network.PktHandlerNpcQuests;
import bq_npc_integration.rewards.RewardNpcFaction;
import bq_npc_integration.rewards.RewardNpcMail;
import bq_npc_integration.tasks.TaskNpcDialog;
import bq_npc_integration.tasks.TaskNpcFaction;
import bq_npc_integration.tasks.TaskNpcQuest;

@Mod(modid = BQ_NPCs.MODID, version = BQ_NPCs.VERSION, name = BQ_NPCs.NAME, guiFactory = "bq_npc_integration.handlers.ConfigGuiFactory")
public class BQ_NPCs
{
    public static final String MODID = "bq_npc_integration";
    public static final String VERSION = "CI_MOD_VERSION";
    public static final String HASH = "CI_MOD_HASH";
    public static final String BRANCH = "CI_MOD_BRANCH";
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
    	
    	PacketTypeRegistry.RegisterType(new PktHandlerNpcQuests(), NpcPacketType.SYNC_QUESTS.GetLocation());
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
