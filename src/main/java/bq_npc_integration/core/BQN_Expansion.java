package bq_npc_integration.core;

import betterquesting.api.api.IQuestExpansion;
import betterquesting.api.api.QuestExpansion;

@QuestExpansion
public class BQN_Expansion implements IQuestExpansion
{
	@Override
	public void loadExpansion()
	{
		BQ_NPCs.proxy.registerExpansion();
	}
}
