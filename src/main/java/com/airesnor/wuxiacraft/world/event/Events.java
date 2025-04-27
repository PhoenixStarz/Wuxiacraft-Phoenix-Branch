package com.airesnor.wuxiacraft.world.event;

import com.airesnor.wuxiacraft.config.WuxiaCraftConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class Events {
	public float etimer;

	private static boolean worldEvent1;
	private static boolean worldEvent2;
	private static boolean worldEvent3;

	public Events() {
		etimer = 0;
		worldEvent1 = false;
		worldEvent2 = false;
		worldEvent3 = false;
	}

	public float getETimer() {
		return this.etimer;
	}

	public void advETimer() {
		this.etimer++;
	}

	public void resetETimer() {
		this.etimer = 0;
	}

	public static boolean getWorldEvent1() {
		return worldEvent1;
	}

	public static void setWorldEvent1(Boolean event) {
		worldEvent1 = event;
	}

	public static boolean getWorldEvent2() {
		return worldEvent2;
	}

	public static void setWorldEvent2(Boolean event) {
		worldEvent2 = event;
	}

	public static boolean getWorldEvent3() {
		return worldEvent3;
	}

	public static void setWorldEvent3(Boolean event) {
		worldEvent3 = event;
	}
	
	@SubscribeEvent
	public void eventWorldTick(TickEvent.WorldTickEvent event) {
		if (WuxiaCraftConfig.enabledEvents == true) {
			if (event.side == Side.SERVER && event.phase == TickEvent.Phase.END) {
				World world = event.world;
					this.advETimer();
					if (this.getETimer() == 1) {
						if (Math.random() < WuxiaCraftConfig.worldEvent1Chance) {
							setWorldEvent1(true);
							setWorldEvent2(false);
							for (Object object : world.playerEntities)
							{
								EntityPlayer player = (EntityPlayer) object;
								TextComponentString startText = new TextComponentString("The air thickens.");
								startText.getStyle().setColor(TextFormatting.BLUE);
								player.sendMessage(startText);
							}
						} else 
						if (Math.random() < WuxiaCraftConfig.worldEvent2Chance) {
							setWorldEvent1(false);
							setWorldEvent2(true);
							for (Object object : world.playerEntities) {
								EntityPlayer player = (EntityPlayer) object;
								TextComponentString startText = new TextComponentString("The air thins.");
								startText.getStyle().setColor(TextFormatting.GRAY);
								player.sendMessage(startText);
							}
						} 
						if (Math.random() < WuxiaCraftConfig.worldEvent3Chance) {
							setWorldEvent3(true);
							for (Object object : world.playerEntities) {
								EntityPlayer player = (EntityPlayer) object;
								TextComponentString startText = new TextComponentString("The air broils with rage.");
								startText.getStyle().setColor(TextFormatting.RED);
								player.sendMessage(startText);
							}
						}
					} else
					if (this.getETimer() == 24000) {
						if (getWorldEvent1() != false || getWorldEvent2() != false || getWorldEvent3() != false) {
							setWorldEvent1(false);
							setWorldEvent2(false);
							setWorldEvent3(false);
							for (Object object : world.playerEntities) {
								EntityPlayer player = (EntityPlayer) object;
								TextComponentString endText = new TextComponentString("The air normalizes.");
								endText.getStyle().setColor(TextFormatting.WHITE);
							player.sendMessage(endText);
							}
						}
					this.resetETimer();
				}
			}
		}
	}
}
