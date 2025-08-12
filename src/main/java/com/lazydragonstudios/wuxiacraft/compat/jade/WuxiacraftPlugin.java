package com.lazydragonstudios.wuxiacraft.compat.jade;

import net.minecraft.world.entity.player.Player;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class WuxiacraftPlugin implements IWailaPlugin {

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerEntityDataProvider(PlayerHealthTooltip.INSTANCE, Player.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(PlayerHealthTooltip.INSTANCE, Player.class);
	}
}
