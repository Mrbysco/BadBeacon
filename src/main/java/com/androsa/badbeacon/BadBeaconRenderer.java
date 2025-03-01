package com.androsa.badbeacon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BadBeaconRenderer implements BlockEntityRenderer<BadBeaconBlockEntity> {
    private static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");

    public BadBeaconRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(BadBeaconBlockEntity entity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light, int destroyStage) {
        long time = entity.getLevel().getGameTime();
        List<BadBeaconBlockEntity.BeamSegment> segments = entity.getBeamSegments();
        int height = 0;

        for(int segment = 0; segment < segments.size(); ++segment) {
            BadBeaconBlockEntity.BeamSegment beacontileentity$beamsegment = segments.get(segment);
            renderSegments(stack, buffer, partialTicks, time, height, segment == segments.size() - 1 ? 1024 : beacontileentity$beamsegment.getHeight(), beacontileentity$beamsegment.getColors());
            height += beacontileentity$beamsegment.getHeight();
        }
    }

    private static void renderSegments(PoseStack matrix, MultiBufferSource buffer, float partialTicks, long time, int height, int segment, float[] colours) {
        BeaconRenderer.renderBeaconBeam(matrix, buffer, TEXTURE_BEACON_BEAM, partialTicks, 1.0F, time, height, segment, colours, 0.2F, 0.25F);
    }

    @Override
    public boolean shouldRenderOffScreen(BadBeaconBlockEntity te) {
        return true;
    }

	@Override
	public int getViewDistance() {
		return 256;
	}

	@Override
	public boolean shouldRender(BadBeaconBlockEntity p_173531_, Vec3 p_173532_) {
		return Vec3.atCenterOf(p_173531_.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(p_173532_.multiply(1.0D, 0.0D, 1.0D), this.getViewDistance());
	}
}
