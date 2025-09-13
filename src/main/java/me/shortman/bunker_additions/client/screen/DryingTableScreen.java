package me.shortman.bunker_additions.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.entity.container.DryingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DryingTableScreen extends AbstractContainerScreen<DryingTableMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            BunkerAdditions.resource("textures/gui/drying_table/drying_table_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            BunkerAdditions.resource("textures/gui/arrow_progress.png");

    private static final ResourceLocation CAN_DRY_TEXTURE_ON =
            BunkerAdditions.resource("textures/gui/drying_table/can_dry_on.png");
    private static final ResourceLocation CAN_DRY_TEXTURE_OFF =
            BunkerAdditions.resource("textures/gui/drying_table/can_dry_off.png");

    public static final int X_PROGRESS_1 = 32;
    public static final int Y_PROGRESS_1 = 21;
    public static final int X_PROGRESS_2 = 114;
    public static final int Y_PROGRESS_2 = 21;
    public static final int X_PROGRESS_3 = 32;
    public static final int Y_PROGRESS_3 = 50;
    public static final int X_PROGRESS_4 = 114;
    public static final int Y_PROGRESS_4 = 50;

    public static final int X_CAN_DRY_1 = 12;
    public static final int Y_CAN_DRY_1 = 20;
    public static final int X_CAN_DRY_2 = 94;
    public static final int Y_CAN_DRY_2 = 20;
    public static final int X_CAN_DRY_3 = 12;
    public static final int Y_CAN_DRY_3 = 49;
    public static final int X_CAN_DRY_4 = 94;
    public static final int Y_CAN_DRY_4 = 49;


    public DryingTableScreen(DryingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y,0,0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderCanDryStatus(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        int slot = 0;
        if (menu.isCrafting(slot)) {

            guiGraphics.blit(ARROW_TEXTURE, x + X_PROGRESS_1, y + Y_PROGRESS_1, 0, 0, menu.getScaledArrowProgress(slot), 16, 24, 16);
        }
        slot++;
        if (menu.isCrafting(slot)) {

            guiGraphics.blit(ARROW_TEXTURE, x + X_PROGRESS_2, y + Y_PROGRESS_2, 0, 0, menu.getScaledArrowProgress(slot), 16, 24, 16);
        }
        slot++;
        if (menu.isCrafting(slot)) {

            guiGraphics.blit(ARROW_TEXTURE, x + X_PROGRESS_3, y + Y_PROGRESS_3, 0, 0, menu.getScaledArrowProgress(slot), 16, 24, 16);
        }
        slot++;
        if (menu.isCrafting(slot)) {

            guiGraphics.blit(ARROW_TEXTURE, x + X_PROGRESS_4, y + Y_PROGRESS_4, 0, 0, menu.getScaledArrowProgress(slot), 16, 24, 16);
        }
    }

    public void renderCanDryStatus(GuiGraphics guiGraphics, int x, int y) {
        if (menu.canDry()) {
            guiGraphics.blit(CAN_DRY_TEXTURE_ON, x + X_CAN_DRY_1, y + Y_CAN_DRY_1, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_ON, x + X_CAN_DRY_2, y + Y_CAN_DRY_2, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_ON, x + X_CAN_DRY_3, y + Y_CAN_DRY_3, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_ON, x + X_CAN_DRY_4, y + Y_CAN_DRY_4, 0, 0, 16, 16, 16, 16);
        } else {
            guiGraphics.blit(CAN_DRY_TEXTURE_OFF, x + X_CAN_DRY_1, y + Y_CAN_DRY_1, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_OFF, x + X_CAN_DRY_2, y + Y_CAN_DRY_2, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_OFF, x + X_CAN_DRY_3, y + Y_CAN_DRY_3, 0, 0, 16, 16, 16, 16);
            guiGraphics.blit(CAN_DRY_TEXTURE_OFF, x + X_CAN_DRY_4, y + Y_CAN_DRY_4, 0, 0, 16, 16, 16, 16);
        }


    }
}
