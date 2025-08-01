package com.lazydragonstudios.wuxiacraft.client;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerStat;
import com.lazydragonstudios.wuxiacraft.init.WuxiaGameRules;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientSideEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerWalk(MovementInputUpdateEvent event) {
		Player player = event.getEntity();
		var cultivation = Cultivation.get(player);
		if (cultivation.isExercising()) {
			event.getInput().up = false;
			event.getInput().down = false;
			event.getInput().left = false;
			event.getInput().right = false;
			event.getInput().jumping = false;
			event.getInput().forwardImpulse = 0f;
			event.getInput().leftImpulse = 0f;
			event.getInput().shiftKeyDown = false;
		} else if (cultivation.isCombat()) {
			var moveInputVec = event.getInput().getMoveVector();
			BigDecimal agility = cultivation.getStat(PlayerStat.AGILITY);
			GameRules.Value<?> maxAgilityRule = player.level().getGameRules().getRule(WuxiaGameRules.maxWuxiaAgility);
			var maxAgility = BigDecimal.valueOf(maxAgilityRule.getCommandResult()).setScale(2, RoundingMode.HALF_DOWN).divide(new BigDecimal("100"), RoundingMode.HALF_DOWN);
			agility = agility.min(maxAgility);
			if (player.onGround()) {
				player.moveRelative(agility.floatValue() * 0.47f, new Vec3(moveInputVec.x, 0, moveInputVec.y));
		/* 	} else if (player.getAbilities().flying) {
				var lookDir = player.getLookAngle();
				var sideLookDir = new Vec3(1, 0, 0).yRot((float) Mth.atan2(lookDir.x, lookDir.z));
				var moveDir = lookDir.scale(moveInputVec.y).add(sideLookDir.scale(moveInputVec.x)).normalize();
				moveDir = moveDir.scale(agility.doubleValue()*1.12f);
				if(moveDir.length() > 0)
				player.setDeltaMovement(moveDir.x, moveDir.y, moveDir.z);									*/
			}
		}
	}
}
