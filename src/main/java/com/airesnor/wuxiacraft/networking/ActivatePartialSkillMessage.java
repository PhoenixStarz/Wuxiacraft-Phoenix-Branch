package com.airesnor.wuxiacraft.networking;

import com.airesnor.wuxiacraft.cultivation.skills.Skills;
import com.airesnor.wuxiacraft.utils.CultivationUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class ActivatePartialSkillMessage implements IMessage {

	public String skillName;
	public double energy;
	public UUID senderUUID;

	@SuppressWarnings("unused")
	public ActivatePartialSkillMessage() {
	}

	public ActivatePartialSkillMessage(String skillName, double energy, UUID senderUUID) {
		this.skillName = skillName;
		this.energy = energy;
		this.senderUUID = senderUUID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer packetBuffer = new PacketBuffer(buf);
		this.energy = buf.readDouble();
		this.skillName = ByteBufUtils.readUTF8String(buf);
		this.senderUUID = packetBuffer.readUniqueId();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer packetBuffer = new PacketBuffer(buf);
		buf.writeDouble(energy);
		ByteBufUtils.writeUTF8String(buf, skillName);
		packetBuffer.writeUniqueId(senderUUID);
	}

	public static class Handler implements IMessageHandler<ActivatePartialSkillMessage, IMessage> {

		@Override
		public IMessage onMessage(ActivatePartialSkillMessage message, MessageContext ctx) {
			if (ctx.side == Side.SERVER) {
				final WorldServer world = ctx.getServerHandler().player.getServerWorld();
				world.addScheduledTask(() -> {
					EntityPlayer player = world.getPlayerEntityByUUID(message.senderUUID);
					if(player!=null){
						if ("barrageMinorBeam".equals(message.skillName)) {
							Skills.BARRAGE_MINOR_BEAM.activate(player);
							CultivationUtils.getCultivationFromEntity(player).remEnergy(message.energy);
						}
						else if ("applySlowness".equals(message.skillName)) {
							Skills.APPLY_SLOWNESS.activate(player);
							CultivationUtils.getCultivationFromEntity(player).remEnergy(message.energy);
						}
						else if ("pressureEveryoneNear".equals(message.skillName)) {
							Skills.PRESSURE_EVERYONE_NEAR.activate(player);
							CultivationUtils.getCultivationFromEntity(player).remEnergy(message.energy);
						}
						else if ("damageEveryoneNear".equals(message.skillName)) {
							Skills.DAMAGE_EVERYONE_NEAR.activate(player);
							CultivationUtils.getCultivationFromEntity(player).remEnergy(message.energy);
						}
					}
				});
			}
			return null;
		}
	}

}
