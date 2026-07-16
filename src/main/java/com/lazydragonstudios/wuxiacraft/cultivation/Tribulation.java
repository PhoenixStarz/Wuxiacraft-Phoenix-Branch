package com.lazydragonstudios.wuxiacraft.cultivation;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;


public class Tribulation {
    protected int tick = 0;
    protected int lightningStrikes = 0;
    protected int lightningPower = 0;
    protected float lightningPowerModifier = 0;
    protected System tribSystem;

    protected int targetStrikes;

    public Tribulation(int numberOfLightningStrikes, int lightningStrength, float lightningStrengthGrowth, System system) {
        targetStrikes = numberOfLightningStrikes;
        lightningPower = lightningStrength;
        lightningPowerModifier = lightningStrengthGrowth;
        tribSystem = system;
    }

    public void reset() {
        tick = 0;
        lightningStrikes = 0;
    }

    public boolean tick(Player player) {
        tick++;
        if (tick % 40 == 0) {
            // Return true if the player has survived the target number of lightning strikes
            if (lightningStrikes >= targetStrikes) return true;
            int strength = (int)(lightningPower * (1 + lightningStrikes * lightningPowerModifier));
            switch (tribSystem) {
                case ESSENCE: {
                    // Do not do tribulation unless in a dimension with a sky
                    if (!player.level().dimensionType().hasCeiling() && player.level().dimensionType().hasSkyLight()) 
                    player.level().setThunderLevel(41);

                    if (player.level().isClientSide) return false;
                    // Spawn one lightning strike every 2 seconds
                    LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(player.level());
                    lightningbolt.moveTo(Vec3.atBottomCenterOf(player.blockPosition()));
                    lightningbolt.setDamage(strength);
                    player.level().addFreshEntity(lightningbolt);
                    lightningStrikes++;
                }
                break;
                case BODY: {
                    player.hurt(player.damageSources().wither(), strength);
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1, true, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 3, true, true, false));
                    lightningStrikes++;
                }
                break;
                case DIVINE: {
                    player.hurt(player.damageSources().cramming(), strength);
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0, true, true, false));
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true, false));
                    lightningStrikes++;
                }
                break;
            } 
        }
        return false;
    }
    
    public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("total-strikes", this.lightningStrikes);
		tag.putInt("target-strikes", this.targetStrikes);
		tag.putInt("power", this.lightningPower);
		tag.putFloat("modifier", this.lightningPowerModifier);
		tag.putString("system", this.tribSystem.toString());
		return tag;
	}

	public void deserialize(CompoundTag tag) {
		if (tag.contains("total-strikes")) {
			this.lightningStrikes = (tag.getInt("strikes"));
		}
        if (tag.contains("target-strikes")) {
			this.targetStrikes = (tag.getInt("strikes"));
		}
		if (tag.contains("power")) {
			this.lightningPower = (tag.getInt("power"));
		}
        if (tag.contains("modifier")) {
			this.lightningPowerModifier = (tag.getFloat("modifier"));
		}
		if (tag.contains("system")) {
			this.tribSystem = System.BODY;
		}
	}

}
