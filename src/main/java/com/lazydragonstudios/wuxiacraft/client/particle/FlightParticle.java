package com.lazydragonstudios.wuxiacraft.client.particle;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FlightParticle extends TextureSheetParticle {

	private SpriteSet spriteSet = null;

	protected FlightParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
		super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
		this.xd = pXSpeed*0.5;
		this.yd = Math.abs(pYSpeed*1.3);
		this.zd = pZSpeed*0.5;
		setLifetime(80);
	}

	public void setSpriteSet(SpriteSet spriteSet) {
		this.spriteSet = spriteSet;
	}

	@Override
	public void tick() {
		super.tick();
		this.oRoll = this.roll;
		this.roll += 0.02f;
		if(this.spriteSet != null)
			this.setSpriteFromAge(this.spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return BeamParticle.PARTICLE_SHEET_TRANSLUCENT_ADD;
	}

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
			FlightParticle particle = (FlightParticle) new FlightParticle(level, x, y, z, xd, yd, zd).scale(8f);
			particle.setSpriteFromAge(this.sprites);
			particle.setSpriteSet(this.sprites);
			particle.setColor(this.red, this.green, this.blue);
			return particle;
		}
	}
}
