package com.lazydragonstudios.wuxiacraft.networking;

import com.lazydragonstudios.wuxiacraft.item.RuneStencil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record RuneSelectionMessage(int rune) {

	public static void encode(RuneSelectionMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.rune);
	}

	public static RuneSelectionMessage decode(FriendlyByteBuf buf) {
		return new RuneSelectionMessage(buf.readInt());
	}

	public static void handleMessage(RuneSelectionMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		var ctx = ctxSupplier.get();
		var side = ctx.getDirection();
		if(side.getReceptionSide() == LogicalSide.CLIENT) return;
		ctx.enqueueWork(() -> {
			var player = ctx.getSender();
			if(player == null) return;
			var itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
			if (!(itemStack.getItem() instanceof RuneStencil)) return;
			var tag = itemStack.getTag();
			if(tag == null) tag = new CompoundTag();
			tag.putInt("runeSelected", msg.rune);
			itemStack.setTag(tag);
		});
	}

}
