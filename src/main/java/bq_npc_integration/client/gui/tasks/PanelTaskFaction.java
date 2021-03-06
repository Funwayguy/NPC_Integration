package bq_npc_integration.client.gui.tasks;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.storage.NpcFactionDB;
import bq_npc_integration.tasks.TaskNpcFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import noppes.npcs.controllers.data.Faction;
import org.lwjgl.util.vector.Vector4f;

public class PanelTaskFaction extends CanvasEmpty
{
    private final TaskNpcFaction task;
    
    public PanelTaskFaction(IGuiRect rect, TaskNpcFaction task)
    {
        super(rect);
        this.task = task;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
    
        Faction faction = NpcFactionDB.INSTANCE.getValue(task.factionID);
        String factName = faction == null ? "[?]" : faction.name;
		int score = task.getUsersProgress(QuestingAPI.getQuestingUUID(Minecraft.getMinecraft().player));
		String value = "" + score;
		
		if(task.operation.checkValues(score, task.target))
		{
			value = TextFormatting.GREEN + value;
		} else
		{
			value = TextFormatting.RED + value;
		}
		
		String txt2 = TextFormatting.BOLD + value + " " + TextFormatting.RESET + task.operation.GetText() + " " + task.target;
		
		// TODO: Add x2 scale when supported
		this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, -16, 0, 0), 0), QuestTranslation.translate("bq_npc_integration.gui.faction_name", factName)).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
		this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, 0, 0, -16), 0), txt2).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
    }
}
