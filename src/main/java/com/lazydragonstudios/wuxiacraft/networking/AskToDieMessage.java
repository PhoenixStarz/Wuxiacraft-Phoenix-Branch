package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.combat.WuxiaDamageSource;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.init.WuxiaDamageTypes;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.network.NetworkEvent;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class AskToDieMessage {

	public AskToDieMessage() {
	}

	public static void encode(AskToDieMessage msg, FriendlyByteBuf buf) {
	}

	public static AskToDieMessage decode(FriendlyByteBuf buf) {
		return new AskToDieMessage();
	}

	public static void handleMessage(AskToDieMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);
		if (side.isServer()) {
			ctx.enqueueWork(() -> {
				var serverPlayer = ctx.getSender();
				Cultivation.get(serverPlayer).setSemiDeadState(false);
				serverPlayer.setHealth(-1);
				DamageSource lastDamageSource = serverPlayer.getLastDamageSource();
				if (lastDamageSource == null) {
					lastDamageSource = new WuxiaDamageSource(serverPlayer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(WuxiaDamageTypes.FORGOT), WuxiaElements.PHYSICAL.get(), BigDecimal.ZERO);
				}
				serverPlayer.die(lastDamageSource);
			});
		}
	}
}
