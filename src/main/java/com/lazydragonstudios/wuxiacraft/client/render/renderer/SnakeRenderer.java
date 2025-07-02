package com.lazydragonstudios.wuxiacraft.client.render.renderer;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.client.render.models.SnakeModel;
import com.lazydragonstudios.wuxiacraft.entity.Snake;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnakeRenderer extends MobRenderer<Snake, SnakeModel> {

	private final static ResourceLocation SNAKE_TEXTURE = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/entity/snake.png");

	public SnakeRenderer(EntityRendererProvider.Context pContext) {
		super(pContext, new SnakeModel(pContext.bakeLayer(new ModelLayerLocation(new ResourceLocation(WuxiaCraft.MOD_ID, "snake"), "main"))), 0.8f);
	}

	@Override
	protected void scale(Snake pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
		pPoseStack.scale(3F, 3.0F, 3.0F);
	}

	@Override
	public void render(Snake pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		pPoseStack.pushPose();
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		pPoseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(Snake pEntity) {
		return SNAKE_TEXTURE;
	}

}
