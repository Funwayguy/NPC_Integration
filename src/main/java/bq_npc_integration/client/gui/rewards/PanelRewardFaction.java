package bq_npc_integration.client.gui.rewards;

import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.rewards.RewardNpcFaction;
import bq_npc_integration.storage.NpcFactionDB;
import com.mojang.realmsclient.gui.ChatFormatting;
import noppes.npcs.controllers.Faction;
import org.lwjgl.util.vector.Vector4f;

public class PanelRewardFaction extends CanvasEmpty
{
    private final RewardNpcFaction reward;
    private final IQuest quest;
    
    public PanelRewardFaction(IGuiRect rect, IQuest quest, RewardNpcFaction reward)
    {
        super(rect);
        this.reward = reward;
        this.quest = quest;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
        
        Faction faction = NpcFactionDB.INSTANCE.getValue(reward.factionID);
        String factName = faction == null ? "[?]" : faction.name;
        this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, -16, 0, 0), 0), QuestTranslation.translate("bq_npc_integration.gui.faction_name", factName)).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
		String txt2 = ChatFormatting.BOLD.toString();
		
		if(!reward.relative)
		{
			txt2 += "= " + reward.value;
		} else if(reward.value >= 0)
		{
			txt2 += ChatFormatting.GREEN + "+ " + Math.abs(reward.value);
		} else
		{
			txt2 += ChatFormatting.RED + "- " + Math.abs(reward.value);
		}
		
        this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, 0, 0, -16), 0), txt2).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
    }
}
