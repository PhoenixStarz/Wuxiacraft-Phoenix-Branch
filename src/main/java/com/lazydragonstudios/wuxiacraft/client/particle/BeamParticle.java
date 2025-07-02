package com.lazydragonstudios.wuxiacraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BeamParticle extends TextureSheetParticle {

	public BeamParticle(ClientLevel level, double posX, double posY, double posZ, double deltaX, double deltaY, double deltaZ) {
		super(level, posX, posY, posZ, deltaX, deltaY, deltaZ);
		this.xd = deltaX*0.5;
		this.yd = Math.abs(deltaY*1.3);
		this.zd = deltaZ*0.5;
		setLifetime(20);
	}

	@Override
	public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
		super.render(pBuffer, pRenderInfo, pPartialTicks);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return PARTICLE_SHEET_TRANSLUCENT_ADD;
	}

	public static final ParticleRenderType PARTICLE_SHEET_TRANSLUCENT_ADD = new ParticleRenderType() {

		@Override
		public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
			RenderSystem.depthMask(false);
			RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
		}

		@Override
		public void end(Tesselator pTesselator) {
			pTesselator.end();
			RenderSystem.defaultBlendFunc();
		}

		@Override
		public String toString() {
			return "PARTICLE_SHEET_TRANSLUCENT_ADD";
		}
	};

	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {

		private final SpriteSet sprites;

		private final float red;

		private final float green;

		private final float blue;

		public Provider(SpriteSet spriteSet, float red, float green, float blue) {
			this.sprites = spriteSet;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
			BeamParticle particle = (BeamParticle) new BeamParticle(level, x, y, z, xd, yd, zd).scale(2f);
			particle.setSpriteFromAge(this.sprites);
			particle.setColor(this.red, this.green, this.blue);
			return particle;
		}
	}
}
