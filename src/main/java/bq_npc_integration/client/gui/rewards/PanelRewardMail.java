package bq_npc_integration.client.gui.rewards;

import betterquesting.api.questing.IQuest;
import betterquesting.api.utils.BigItemStack;
import betterquesting.api2.client.gui.misc.GuiAlign;
import betterquesting.api2.client.gui.misc.GuiPadding;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelGeneric;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.resources.textures.ItemTexture;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.utils.QuestTranslation;
import bq_npc_integration.rewards.RewardNpcMail;
import noppes.npcs.CustomItems;
import org.lwjgl.util.vector.Vector4f;

public class PanelRewardMail extends CanvasEmpty
{
    private final RewardNpcMail reward;
    private final IQuest quest;
    
    public PanelRewardMail(IGuiRect rect, IQuest quest, RewardNpcMail reward)
    {
        super(rect);
        this.reward = reward;
        this.quest = quest;
    }
    
    @Override
    public void initPanel()
    {
        super.initPanel();
        
        this.addPanel(new PanelGeneric(new GuiTransform(GuiAlign.MID_CENTER, -16, -32, 32, 32, 0), new ItemTexture(new BigItemStack(CustomItems.mailbox))));
        this.addPanel(new PanelTextBox(new GuiTransform(new Vector4f(0F, 0.5F, 1F, 0.5F), new GuiPadding(0, 4, 0, -16), 0), QuestTranslation.translate("bq_npc_integration.gui.mail", reward.mail == null ? "[?]" : reward.mail.sender)).setAlignment(1).setColor(PresetColor.TEXT_MAIN.getColor()));
    }
}
