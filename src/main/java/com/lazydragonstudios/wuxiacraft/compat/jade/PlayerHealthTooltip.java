package com.lazydragonstudios.wuxiacraft.compat.jade;

import java.math.BigDecimal;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.util.StatsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PlayerHealthTooltip implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
	INSTANCE;

	@Override
	public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
		var entity = entityAccessor.getEntity();
		if (!(entity instanceof Player player)) return;
		var cultivation = Cultivation.get(player);
		var hp = StatsUtil.getShortHealthAmount(BigDecimal.valueOf((player.getHealth())));
		compoundTag.putString("wuxiaHP", hp);
	}

	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		var entity = entityAccessor.getEntity();
		if(!(entity instanceof Player)) return;
		var serverTag = entityAccessor.getServerData();
		if(!(serverTag.contains("wuxiaHP"))) return;
		var hp = serverTag.getString("wuxiaHP");
		iTooltip.remove(new ResourceLocation("minecraft:entity_health"));
		iTooltip.add(Component.translatable("wuxiacraft.gui.jade.health", hp));
	}

	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(WuxiaCraft.MOD_ID, "jade_hp_tooltip");
	}
}
