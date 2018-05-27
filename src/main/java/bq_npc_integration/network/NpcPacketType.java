package bq_npc_integration.network;

import net.minecraft.util.ResourceLocation;
import bq_npc_integration.core.BQ_NPCs;

public enum NpcPacketType
{
	SYNC_QUESTS,
	SYNC_DIALOG,
	SYNC_FACTIONS;
	
	private final ResourceLocation ID;
	
	private NpcPacketType()
	{
		this.ID = new ResourceLocation(BQ_NPCs.MODID, this.toString().toLowerCase());
	}
	
	public ResourceLocation GetLocation()
	{
		return ID;
	}
}
