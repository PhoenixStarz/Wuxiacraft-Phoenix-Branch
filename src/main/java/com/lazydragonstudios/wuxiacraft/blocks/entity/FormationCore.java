package com.lazydragonstudios.wuxiacraft.blocks.entity;

import com.lazydragonstudios.wuxiacraft.WuxiaCraft;
import com.lazydragonstudios.wuxiacraft.blocks.StatRuneBlock;
import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.FormationStatsContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.formation.FormationStat;
import com.lazydragonstudios.wuxiacraft.formation.FormationSystemStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaBlockEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FormationCore extends BlockEntity {

	private final HashMap<FormationStat, BigDecimal> formationStats;

	private final HashMap<System, HashMap<FormationSystemStat, BigDecimal>> formationSystemStats;

	private final HashSet<BlockPos> runePositions;

	private final FormationStatsContainer formationPlayerStats = new FormationStatsContainer();

	public UUID owner;

	public String ownerName;

	public boolean scheduleActivation;

	private boolean active;

	private int runeRange;

	public FormationCore(BlockPos pos, BlockState blockState) {
		super(WuxiaBlockEntities.FORMATION_CORE.get(), pos, blockState);
		this.runeRange = 1;
		this.formationStats = new HashMap<>();
		this.formationSystemStats = new HashMap<>();
		this.runePositions = new HashSet<>();
		this.owner = null;
		this.scheduleActivation = false;
	}

	public FormationStatsContainer getFormationPlayerStats() {
		return formationPlayerStats;
	}

	public void activate(@Nullable UUID playerId) {
		this.scheduleActivation = false;
		if (this.active) return;
		if (this.level == null) return;
		if (playerId == null) return;
		var centerPos = this.getBlockPos();
		this.formationPlayerStats.clear();
		for (int i = -this.runeRange; i <= this.runeRange; i++) {
			for (int j = 0; j <= 0; j++) {
				for (int k = -this.runeRange; k <= this.runeRange; k++) {
					if (i == 0 && j == 0 && k == 0) continue;
					var currentPos = new BlockPos(centerPos.getX() - i, centerPos.getY() - j, centerPos.getZ() - k);
					var state = this.level.getBlockState(currentPos);
					var block = state.getBlock();
					if (!(block instanceof StatRuneBlock rune)) continue;
					this.runePositions.add(currentPos);
					for (var stat : rune.formationStats.keySet()) {
						if (stat.isModifiable) continue;
						var initialValue = this.formationStats.getOrDefault(stat, BigDecimal.ZERO);
						var runeValue = rune.formationStats.get(stat);
						this.formationStats.put(stat, stat.join(initialValue, runeValue));
					}
					for (var system : rune.formationSystemStats.keySet()) {
						for (var stat : rune.formationSystemStats.get(system).keySet()) {
							this.formationSystemStats.putIfAbsent(system, new HashMap<>());
							if (stat.isModifiable) continue;
							var initialValue = this.formationSystemStats.get(system).getOrDefault(stat, BigDecimal.ZERO);
							var runeValue = rune.formationSystemStats.get(system).get(stat);
							this.formationSystemStats.get(system).put(stat, stat.join(initialValue, runeValue));
						}
					}
				}
			}
		}
		var energyCost = this.getStat(FormationStat.ENERGY_COST);
		var energyGeneration = this.getStat(FormationStat.ENERGY_GENERATION);
		for (var system : this.formationSystemStats.keySet()) {
			for (var stat : this.formationSystemStats.get(system).keySet()) {
				PlayerSystemStat playerSystemStat = null;
				switch (stat) {
					case ENERGY_REGEN -> playerSystemStat = PlayerSystemStat.ENERGY_REGEN;
					case CULTIVATION_SPEED -> playerSystemStat = PlayerSystemStat.CULTIVATION_SPEED;
				}
				if (playerSystemStat != null) {
					this.formationPlayerStats.setStat(system, playerSystemStat, this.formationSystemStats.get(system).get(stat));
				}
			}
		}
		if (energyCost.compareTo(energyGeneration) <= 0) {
			this.owner = playerId;
			var player = this.getOwner();
			if (player != null) {
				this.ownerName = player.getDisplayName().getString();
				var cultivation = Cultivation.get(player);
				cultivation.setFormation(this.getBlockPos());
				cultivation.getFormationStats().setFormationActive(this.getBlockPos());
				cultivation.getFormationStats().copyFrom(this.formationPlayerStats);
			}
			this.active = true;
			if (!this.level.isClientSide && player != null) {
				this.level.playSound(null, player.getOnPos(), SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.BLOCKS, 6f, 0.6f);
			}
		} else {
			deactivate();
		}
		if (level.getPlayerByUUID(playerId) instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(Component.translatable("wuxiacraft.gui.energy", energyGeneration.subtract(energyCost)),
				true);
		}
		this.setChanged();
		if (!this.level.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
	}

	public BigDecimal getStat(FormationStat stat) {
		return this.formationStats.getOrDefault(stat, BigDecimal.ZERO);
	}

	@Nullable
	public Player getOwner() {
		if (this.level == null) return null;
		return this.level.getPlayerByUUID(this.owner);
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void deactivate() {
		this.active = false;
		this.runePositions.clear();
		this.formationStats.clear();
		this.formationSystemStats.clear();
		this.formationPlayerStats.clear();
		if (this.owner != null) {
			if (this.level != null) {
				var owner = this.level.getPlayerByUUID(this.owner);
				if (owner != null) {
					var cultivation = Cultivation.get(owner);
					cultivation.setFormation(null);
					cultivation.getFormationStats().setFormationActive(null);
					cultivation.getFormationStats().clear();
				}
			}
			this.owner = null;
			this.ownerName = null;
		}
		this.setChanged();
		if (this.level != null && !this.level.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
	}

	public boolean attackBarrierMelee(LivingEntity attacker, float attackStrength) {
		Level level1 = this.level;
		if (level1 == null) return false;
		var finalAttackDamage = BigDecimal.valueOf(attackStrength);
		if (attacker != null)
			finalAttackDamage = BigDecimal.valueOf(attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
		if (attacker instanceof Player player) {
			var attackerStrengthStat = Cultivation.get(player).getStat(PlayerStat.STRENGTH, false).divide(BigDecimal.TEN);
			finalAttackDamage = attackerStrengthStat.add(BigDecimal.valueOf(attackStrength));
		}
		var barrierMaxHP = this.getStat(FormationStat.BARRIER_MAX_AMOUNT);
		var barrierHP = this.getStat(FormationStat.BARRIER_AMOUNT);
		var barrierDefense = this.getStat(FormationStat.BARRIER_STRENGTH);
		var barrierDamage = finalAttackDamage.subtract(barrierDefense).max(BigDecimal.ZERO);
		var finalHP = barrierHP.subtract(barrierDamage);
		this.setStat(FormationStat.BARRIER_AMOUNT, finalHP);
		boolean barrierBroke = false;
		BigDecimal addedCoolDown = new BigDecimal("30");
		if (!(finalHP.equals(barrierMaxHP))) {
			if (finalHP.compareTo(BigDecimal.ZERO) > 0) {
				this.setStat(FormationStat.BARRIER_COOLDOWN, addedCoolDown);
			} else {
				this.setStat(FormationStat.BARRIER_COOLDOWN, new BigDecimal("300"));
				if (!level1.isClientSide) {
					this.level.playSound(null, attacker.getOnPos(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 6f, 0.6f);
				}
				barrierBroke = true;
			}
		}
		if (!level1.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
		return barrierBroke;
	}

	public boolean attackBarrierOther(BigDecimal attackStrength) {
		Level level1 = this.level;
		if (level1 == null) return false;
		var barrierMaxHP = this.getStat(FormationStat.BARRIER_MAX_AMOUNT);
		var barrierHP = this.getStat(FormationStat.BARRIER_AMOUNT);
		var finalHP = barrierHP.subtract(attackStrength);
		this.setStat(FormationStat.BARRIER_AMOUNT, finalHP);
		boolean barrierBroke = false;
		BigDecimal addedCoolDown = new BigDecimal("30");
		if (finalHP.compareTo(BigDecimal.ZERO) > 0) {
			this.setStat(FormationStat.BARRIER_COOLDOWN, addedCoolDown);
		} else {
			this.setStat(FormationStat.BARRIER_COOLDOWN, new BigDecimal("300"));
			barrierBroke = true;
		}
		if (!level1.isClientSide) {
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
		}
		return barrierBroke;
	}

	public boolean containsRune(BlockPos pos) {
		return this.runePositions.contains(pos);
	}

	public int getRuneRange() {
		return runeRange;
	}

	public FormationCore setRuneRange(int runeRange) {
		this.runeRange = runeRange;
		return this;
	}

	public BigDecimal getStat(System system, FormationSystemStat stat) {
		return this.formationSystemStats.getOrDefault(system, new HashMap<>()).getOrDefault(stat, BigDecimal.ZERO);
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("rune-range")) {
			this.runeRange = tag.getInt("rune-range");
		}
		if (tag.contains("owner")) {
			this.owner = tag.getUUID("owner");
		}
		if (tag.contains("owner-name")) {
			this.ownerName = tag.getString("owner-name");
		}
		if (tag.contains("active")) {
			if (tag.getBoolean("active")) {
				this.scheduleActivation = true;
			} else {
				this.deactivate();
			}
		}
		if (tag.contains("stats")) {
			var statsList = (ListTag) tag.get("stats");
			for (var rawStatsTag : statsList) {
				if (!(rawStatsTag instanceof CompoundTag statsTag)) continue;
				var stat = FormationStat.valueOf(statsTag.getString("name"));
				var value = new BigDecimal(statsTag.getString("value"));
				this.setStat(stat, value);
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("rune-range", this.runeRange);
		tag.putBoolean("active", this.active);
		if (this.owner != null) {
			tag.putUUID("owner", this.owner);
		}
		if (this.ownerName != null) {
			tag.putString("owner-name", this.ownerName);
		}
		var statsList = new ListTag();
		for (var stat : FormationStat.values()) {
			if (!stat.isModifiable) continue;
			var statTag = new CompoundTag();
			statTag.putString("name", stat.name());
			statTag.putString("value", this.getStat(stat).toPlainString());
			statsList.add(statTag);
		}
		tag.put("stats", statsList);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		var tag = super.getUpdateTag();
		this.saveAdditional(tag);
		return tag;
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
	}

	public void setStat(System system, FormationSystemStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.formationSystemStats.putIfAbsent(system, new HashMap<>());
		this.formationSystemStats.get(system).put(stat, value.max(BigDecimal.ZERO));
	}

	public void setStat(FormationStat stat, BigDecimal value) {
		if (!stat.isModifiable) return;
		this.formationStats.put(stat, value.max(BigDecimal.ZERO));
	}
}
