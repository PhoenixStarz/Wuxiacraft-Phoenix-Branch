package com.lazydragonstudios.wuxiacraft.event;

import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.init.WuxiaElements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

import java.math.BigDecimal;
import java.util.HashMap;

public class CultivatingEvent extends Event {

	private final Player player;

	private final System system;

	private BigDecimal amount;

	private HashMap<ResourceLocation, BigDecimal> element = new HashMap<ResourceLocation, BigDecimal>();

	public CultivatingEvent(Player player, System system, BigDecimal amount) {
		this.player = player;
		this.system = system;
		this.amount = amount;
		this.element = null;
	}

	public CultivatingEvent(Player player, System system, BigDecimal amount, HashMap<ResourceLocation, BigDecimal> element) {
		this.player = player;
		this.system = system;
		this.amount = amount;
		this.element = element;
	}

	public Player getPlayer() {
		return player;
	}

	public System getSystem() {
		return system;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public HashMap<ResourceLocation, BigDecimal> getElement() {
		if (element == null) {
			this.element = new HashMap<ResourceLocation, BigDecimal>();
			element.put(new ResourceLocation("test"), BigDecimal.ZERO);
		};
		return element;
	}
	public void addElement(ResourceLocation element, BigDecimal value) {
		this.element.put(element, value);
	}
	
}
