package com.lazydragonstudios.wuxiacraft.client.render.renderer;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.entity.Snake;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlueSnakeRenderer extends SnakeRenderer {

	private final static ResourceLocation SNAKE_TEXTURE = new ResourceLocation(WuxiaCraft.MOD_ID, "textures/entity/blue_snake.png");

	public BlueSnakeRenderer(EntityRendererProvider.Context pContext) {
		super(pContext);
	}

	@Override
	public ResourceLocation getTextureLocation(Snake pEntity) {
		return SNAKE_TEXTURE;
	}
}
