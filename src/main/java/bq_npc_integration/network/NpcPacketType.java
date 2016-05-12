package bq_npc_integration.network;

import net.minecraft.util.ResourceLocation;
import betterquesting.core.BetterQuesting;
import bq_npc_integration.core.BQ_NPCs;

public enum NpcPacketType
{
	SYNC_QUESTS,
	SYNC_DIALOG;
	
	public ResourceLocation GetLocation()
	{
		return new ResourceLocation(BQ_NPCs.MODID + ":" + this.toString().toLowerCase());
	}
	
	public String GetName()
	{
		return BetterQuesting.MODID + ":" + this.toString().toLowerCase();
	}
}
