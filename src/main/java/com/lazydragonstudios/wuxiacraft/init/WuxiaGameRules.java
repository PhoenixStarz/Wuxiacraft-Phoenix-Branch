package com.lazydragonstudios.wuxiacraft.init;

import com.lazydragonstudios.wuxiacraft.networking.SynchronizeMaxAgility;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.network.PacketDistributor;

public class WuxiaGameRules {

	public static GameRules.Key<?> maxWuxiaAgility;

	public static void registerRules() {
		maxWuxiaAgility = GameRules.register("maxWuxiaAgility", GameRules.Category.PLAYER, GameRules.IntegerValue.create(10, (server, value) -> {
			for (var player : server.getPlayerList().getPlayers()) {
				WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SynchronizeMaxAgility(value.getCommandResult()));
			}
		}));
	}

}
