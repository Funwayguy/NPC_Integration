package bq_npc_integration.client.gui.tasks;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.storage.NpcFactionDB;
import bq_npc_integration.tasks.TaskNpcFaction;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import noppes.npcs.controllers.Faction;
import org.lwjgl.util.vector.Vector4f;

public class PanelTaskFaction extends CanvasEmpty
{
    private final TaskNpcFaction task;
    private final IQuest quest;
    
    public PanelTaskFaction(IGuiRect rect, IQuest quest, TaskNpcFaction task)
    {
        super(rect);
        this.task = task;
        this.quest = quest;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
    
        Faction faction = NpcFactionDB.INSTANCE.getValue(task.factionID);
        String factName = faction == null ? "[?]" : faction.name;
		int score = task.getUsersProgress(QuestingAPI.getQuestingUUID(Minecraft.getMinecraft().thePlayer));
		String value = "" + score;
		
		if(task.operation.checkValues(score, task.target))
		{
			value = ChatFormatting.GREEN + value;
		} else
		{
			value = ChatFormatting.RED + value;
		}
		
		String txt2 = ChatFormatting.BOLD + value + " " + ChatFormatting.RESET + task.operation.GetText() + " " + task.target;
		
		// TODO: Add x2 scale when supported
		this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, -16, 0, 0), 0), QuestTranslation.translate("bq_npc_integration.gui.faction_name", factName)).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
		this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, 0, 0, -16), 0), txt2).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
    }
}
