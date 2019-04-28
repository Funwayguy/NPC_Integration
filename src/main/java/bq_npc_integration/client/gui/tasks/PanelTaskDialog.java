package bq_npc_integration.client.gui.tasks;

import betterquesting.api.api.QuestingAPI;
import betterquesting.api.questing.IQuest;
import betterquesting.api2.client.gui.misc.*;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.bars.PanelVScrollBar;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.panels.lists.CanvasScrolling;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.storage.NpcDialogDB;
import bq_npc_integration.tasks.TaskNpcDialog;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import noppes.npcs.controllers.Dialog;

public class PanelTaskDialog extends CanvasEmpty
{
    private final TaskNpcDialog task;
    private final IQuest quest;
    
    public PanelTaskDialog(IGuiRect rect, IQuest quest, TaskNpcDialog task)
    {
        super(rect);
        this.quest = quest;
        this.task = task;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
    
        Dialog dialog = NpcDialogDB.INSTANCE.getValue(task.npcDialogID);
        String title = dialog == null ? "[?]" : dialog.title;
        boolean complete = task.isComplete(QuestingAPI.getQuestingUUID(Minecraft.getMinecraft().thePlayer));
        
        this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 4, 0, -16), 0), QuestTranslation.translate("bq_npc_integration.gui.dialog", title)).setColor(PresetColor.TEXT_MAIN.getColor()));
        String txt = complete ? (ChatFormatting.GREEN + QuestTranslation.translate("betterquesting.tooltip.complete")) : (ChatFormatting.RED + QuestTranslation.translate("betterquesting.tooltip.incomplete"));
        this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(0, 20, 0, -32), 0), QuestTranslation.translate("bq_npc_integration.gui.status", txt)).setColor(PresetColor.TEXT_MAIN.getColor()));
    
        CanvasScrolling cvDesc = new CanvasScrolling(new GuiTransform(GuiAlign.FULL_BOX, new GuiPadding(0, 40, 8, 0), 0));
        this.addPanel(cvDesc);
    
        PanelVScrollBar scDesc = new PanelVScrollBar(new GuiTransform(GuiAlign.RIGHT_EDGE, new GuiPadding(-8, 40, 0, 0), 0));
        this.addPanel(scDesc);
        cvDesc.setScrollDriverY(scDesc);
        
        int w = cvDesc.getTransform().getWidth();
        
        if(dialog != null) cvDesc.addPanel(new PanelTextBox(new GuiRectangle(0, 0, w, 0, 0), dialog.text, true).setColor(PresetColor.TEXT_MAIN.getColor()));
        cvDesc.refreshScrollBounds();
    }
}
