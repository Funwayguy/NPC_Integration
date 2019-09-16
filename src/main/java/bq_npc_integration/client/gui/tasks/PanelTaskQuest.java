package bq_npc_integration.client.gui.tasks;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api2.client.gui.misc.*;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.bars.PanelVScrollBar;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.panels.lists.CanvasScrolling;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.storage.NpcQuestDB;
import bq_npc_integration.tasks.TaskNpcQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import noppes.npcs.controllers.data.Quest;

public class PanelTaskQuest extends CanvasEmpty
{
    private final TaskNpcQuest task;
    
    public PanelTaskQuest(IGuiRect rect, TaskNpcQuest task)
    {
        super(rect);
        this.task = task;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
    
        Quest npcQuest = NpcQuestDB.INSTANCE.getValue(task.npcQuestID);
        String title = npcQuest == null ? "[?]" : npcQuest.title;
        boolean complete = task.isComplete(QuestingAPI.getQuestingUUID(Minecraft.getMinecraft().player));
        
        this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 4, 0, -16), 0), QuestTranslation.translate("bq_npc_integration.gui.quest", title)).setColor(PresetColor.TEXT_MAIN.getColor()));
        String txt = complete ? (TextFormatting.GREEN + QuestTranslation.translate("betterquesting.tooltip.complete")) : (TextFormatting.RED + QuestTranslation.translate("betterquesting.tooltip.incomplete"));
        this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 20, 0, -32), 0), QuestTranslation.translate("bq_npc_integration.gui.status", txt)).setColor(PresetColor.TEXT_MAIN.getColor()));
    
        CanvasScrolling cvDesc = new CanvasScrolling(new GuiTransform(GuiAlign.FULL_BOX, new GuiPadding(0, 40, 8, 0), 0));
        this.addPanel(cvDesc);
    
        PanelVScrollBar scDesc = new PanelVScrollBar(new GuiTransform(GuiAlign.RIGHT_EDGE, new GuiPadding(-8, 40, 0, 0), 0));
        this.addPanel(scDesc);
        cvDesc.setScrollDriverY(scDesc);
        
        int w = cvDesc.getTransform().getWidth();
        
        if(npcQuest != null) cvDesc.addPanel(new PanelTextBox(new GuiRectangle(0, 0, w, 0, 0), complete ? npcQuest.completeText : npcQuest.logText, true).setColor(PresetColor.TEXT_MAIN.getColor()));
        cvDesc.refreshScrollBounds();
    }
}
