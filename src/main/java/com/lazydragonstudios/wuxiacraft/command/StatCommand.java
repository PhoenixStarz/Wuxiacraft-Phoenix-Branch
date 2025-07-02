package com.lazydragonstudios.wuxiacraft.command;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.SystemContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemElementalStat;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import com.lazydragonstudios.wuxiacraft.networking.CultivationSyncMessage;
import com.lazydragonstudios.wuxiacraft.networking.WuxiaPacketHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.command.EnumArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("stat")
				.requires(commandSourceStack -> commandSourceStack.hasPermission(2))
				.then(Commands.argument("target", EntityArgument.player())
						.then(Commands.literal("get")
								.then(Commands.literal("all")
										.executes(StatCommand::getStats)
								)
								.then(Commands.argument("playerStat", EnumArgument.enumArgument(PlayerStat.class))
										.executes(StatCommand::getStat)
								)
								.then(Commands.argument("elementalStat", EnumArgument.enumArgument(PlayerElementalStat.class))
										.then(Commands.argument("element", ElementArgument.id())
												.executes(StatCommand::getElementalStat)
										)
								)
								.then(Commands.argument("system", EnumArgument.enumArgument(System.class))
										.then(Commands.argument("systemStat", EnumArgument.enumArgument(PlayerSystemStat.class))
												.executes(StatCommand::getSystemStat)
										)
										.then(Commands.argument("systemElementalStat", EnumArgument.enumArgument(PlayerSystemElementalStat.class))
												.then(Commands.argument("element", ElementArgument.id())
														.executes(StatCommand::getSystemElementalStat)
												)
										)
								)
						)
						.then(Commands.literal("set")
								.then(Commands.argument("stat", EnumArgument.enumArgument(PlayerStat.class))
										.then(Commands.argument("amount", IntegerArgumentType.integer())
												.executes(StatCommand::setStat)
										)
								)
								.then(Commands.argument("element", ElementArgument.id())
										.then(Commands.argument("stat", EnumArgument.enumArgument(PlayerElementalStat.class))
												.then(Commands.argument("amount", IntegerArgumentType.integer())
														.executes(StatCommand::setElementalStat)
												)
										)
								)
								.then(Commands.argument("system", EnumArgument.enumArgument(System.class))
										.then(Commands.argument("stat", EnumArgument.enumArgument(PlayerSystemStat.class))
												.then(Commands.argument("amount", IntegerArgumentType.integer())
														.executes(StatCommand::setSystemStat)
												)
										)
								)
								.then(Commands.argument("system", EnumArgument.enumArgument(System.class))
										.then(Commands.argument("element", ElementArgument.id())
												.then(Commands.argument("stat", EnumArgument.enumArgument(PlayerSystemElementalStat.class))
														.then(Commands.argument("amount", IntegerArgumentType.integer())
																.executes(StatCommand::setSystemElementalStat)
														)
												)
										)
								)
						)
				)
		);
	}

	public static void syncClientCultivation(ServerPlayer player) {
		ICultivation cultivation = Cultivation.get(player);
		WuxiaPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new CultivationSyncMessage(cultivation));
		cultivation.calculateStats();
	}

	public static int getStats(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		ICultivation cultivation = Cultivation.get(target);
		var message = Component.empty();
		message.append("Player Stats: ").append("\n");
		for (var stat : PlayerStat.values()) {
			String statName = stat.name();
			message.append(statName).append(": ").append(String.format("%.1f", cultivation.getStat(stat))).append("\n");
		}
		ctx.getSource().sendSuccess(() -> message, true);
		return 1;
	}

	public static int getStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		ICultivation cultivation = Cultivation.get(target);
		var stat = ctx.getArgument("playerStat", PlayerStat.class);
		var statValue = cultivation.getStat(stat);
		var message = Component.translatable("wuxiacraft.command.get_player_stat",
				stat.name(), statValue.setScale(2, RoundingMode.HALF_DOWN).toPlainString());
		ctx.getSource().sendSuccess(() -> message, true);
		return 1;
	}

	public static int getSystemStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		ICultivation cultivation = Cultivation.get(target);
		System system = ctx.getArgument("system", System.class);
		PlayerSystemStat stat = ctx.getArgument("systemStat", PlayerSystemStat.class);
		var statValue = cultivation.getStat(system, stat);
		var message = Component.translatable("wuxiacraft.command.get_system_stat",
				system.name(), stat.name(), statValue.setScale(2, RoundingMode.HALF_DOWN).toPlainString());
		ctx.getSource().sendSuccess(() -> message, true);
		return 1;
	}

	public static int getElementalStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		ICultivation cultivation = Cultivation.get(target);
		var element = ElementArgument.getAspectLocation(ctx, "element");
		var stat = ctx.getArgument("elementalStat", PlayerElementalStat.class);
		var statValue = cultivation.getStat(element, stat);
		var message = Component.translatable("wuxiacraft.command.get_system_stat",
				Component.translatable(element.getNamespace() + ".element." + element.getPath()), stat.name(), statValue.setScale(2, RoundingMode.HALF_DOWN).toPlainString());
		ctx.getSource().sendSuccess(() -> message, true);
		return 1;
	}

	public static int getSystemElementalStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		ICultivation cultivation = Cultivation.get(target);
		var element = ElementArgument.getAspectLocation(ctx, "element");
		System system = ctx.getArgument("system", System.class);
		PlayerSystemElementalStat stat = ctx.getArgument("systemElementalStat", PlayerSystemElementalStat.class);
		var statValue = cultivation.getStat(system, element, stat);
		var message = Component.translatable("wuxiacraft.command.get_system_elemental_stat",
				system.name(),
				Component.translatable(element.getNamespace() + ".element." + element.getPath()),
				stat.name(),
				statValue.setScale(2, RoundingMode.HALF_DOWN).toPlainString());
		ctx.getSource().sendSuccess(() -> message, true);
		return 1;
	}

	public static int setStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		int amount = IntegerArgumentType.getInteger(ctx, "amount");
		var playerStat = ctx.getArgument("stat", PlayerStat.class);

		ICultivation cultivation = Cultivation.get(target);
		var message = Component.empty();
		BigDecimal newValue = BigDecimal.valueOf(amount);
		cultivation.setStat(playerStat, newValue);

		message.append("Successfully set the target's " + playerStat.name() + " stat to: " + newValue.toPlainString());
		ctx.getSource().sendSuccess(() -> message, true);
		syncClientCultivation(target);
		return 1;
	}

	public static int setElementalStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		var element = ElementArgument.getAspectLocation(ctx, "element");
		var stat = ctx.getArgument("stat", PlayerElementalStat.class);
		int amount = IntegerArgumentType.getInteger(ctx, "amount");

		ICultivation cultivation = Cultivation.get(target);
		var message = Component.empty();
		BigDecimal newValue = BigDecimal.valueOf(amount);
		cultivation.setStat(element, stat, newValue);

		message.append(Component.translatable("wuxiacraft.command.elemental_stat",
				stat.name(),
				Component.translatable(element.getNamespace() + ".element." + element.getPath()),
				newValue.toPlainString()));
		ctx.getSource().sendSuccess(() -> message, true);
		syncClientCultivation(target);
		return 1;
	}

	public static int setSystemStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		System system = ctx.getArgument("system", System.class);
		PlayerSystemStat stat = ctx.getArgument("stat", PlayerSystemStat.class);
		int amount = IntegerArgumentType.getInteger(ctx, "amount");

		ICultivation cultivation = Cultivation.get(target);
		var message = Component.empty();

		cultivation.setStat(system, stat, BigDecimal.valueOf(amount));

		message.append("Successfully set the target's " + stat + " stat.");
		ctx.getSource().sendSuccess(() -> message, true);
		syncClientCultivation(target);
		return 1;
	}

	public static int setSystemElementalStat(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
		System system = ctx.getArgument("system", System.class);
		PlayerSystemElementalStat stat = ctx.getArgument("stat", PlayerSystemElementalStat.class);
		var element = ElementArgument.getAspectLocation(ctx, "element");
		int amount = IntegerArgumentType.getInteger(ctx, "amount");

		ICultivation cultivation = Cultivation.get(target);
		var message = Component.empty();

		cultivation.setStat(system, element, stat, BigDecimal.valueOf(amount));

		message.append("Successfully set the target's " + stat + " stat.");
		ctx.getSource().sendSuccess(() -> message, true);
		syncClientCultivation(target);
		return 1;
	}
}
