package bq_npc_integration.handlers;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import bq_npc_integration.core.BQN_Settings;
import bq_npc_integration.core.BQ_NPCs;

public class ConfigHandler
{
	public static Configuration config;
	
	public static void initConfigs()
	{
		if(config == null)
		{
			BQ_NPCs.logger.log(Level.ERROR, "Config attempted to be loaded before it was initialised!");
			return;
		}
		
		config.load();
		
		BQN_Settings.hideUpdates = config.getBoolean("Hide Updates", Configuration.CATEGORY_GENERAL, false, "Hide update notifications");
		
		config.save();
		
		BQ_NPCs.logger.log(Level.INFO, "Loaded configs...");
	}
}
