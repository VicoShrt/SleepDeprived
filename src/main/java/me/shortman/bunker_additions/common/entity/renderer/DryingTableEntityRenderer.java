package me.shortman.bunker_additions.common.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.shortman.bunker_additions.common.block.entity.DryingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class DryingTableEntityRenderer implements BlockEntityRenderer<DryingTableBlockEntity> {
    public DryingTableEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(DryingTableBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack stack1 = blockEntity.inventory.getStackInSlot(4).isEmpty() ? blockEntity.inventory.getStackInSlot(0) : blockEntity.inventory.getStackInSlot(4);
        ItemStack stack2 = blockEntity.inventory.getStackInSlot(5).isEmpty() ? blockEntity.inventory.getStackInSlot(1) : blockEntity.inventory.getStackInSlot(5);
        ItemStack stack3 = blockEntity.inventory.getStackInSlot(6).isEmpty() ? blockEntity.inventory.getStackInSlot(2) : blockEntity.inventory.getStackInSlot(6);
        ItemStack stack4 = blockEntity.inventory.getStackInSlot(7).isEmpty() ? blockEntity.inventory.getStackInSlot(3) : blockEntity.inventory.getStackInSlot(7);

        poseStack.pushPose();
        poseStack.translate(0.3f, 1f, 0.3f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack1, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.7f, 1f, 0.7f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack2, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.3f, 1f, 0.7f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack3, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.7f, 1f, 0.3f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack4, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        int bLight = level.getBrightness(LightLayer.BLOCK, above);
        int sLight = level.getBrightness(LightLayer.SKY, above);
        return LightTexture.pack(bLight, sLight);
    }
}
