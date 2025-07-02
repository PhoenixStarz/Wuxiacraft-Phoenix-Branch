package com.lazydragonstudios.wuxiacraft.mixins;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerSpeed {

	@ModifyConstant(method = "handleMovePlayer", constant = @Constant(floatValue = 100.0F))
	private float wuxiacraft_playerMaxSpeed(float speed) {
		return Float.MAX_VALUE;
	}

	@ModifyConstant(method = "handleMovePlayer", constant = @Constant(floatValue = 300.0F))
	private float wuxiacraft_elytraMaxSpeed(float speed) {
		return Float.MAX_VALUE;
	}

	@ModifyConstant(method = "handleMoveVehicle", constant = @Constant(doubleValue = 100.0))
	private double wuxiacraft_vehicleMaxSpeed(double speed) {
		return Double.MAX_VALUE;
	}
}
