package com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects;

import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabel;
import com.lazydragonstudios.wuxiacraft.client.gui.widgets.WuxiaLabelBox;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.LinkedList;

public class StartAspect extends TechniqueAspect {

	@Override
	public boolean canConnect(TechniqueAspect aspect) {
		return aspect instanceof ElementalGenerator;
	}

	@Override
	public int canConnectFromCount() {
		return 0;
	}

	@Override
	public int canConnectToCount() {
		return -1;
	}

	@Nonnull
	@Override
	public LinkedList<AbstractWidget> getStatsSheetDescriptor(BigDecimal proficiency) {
		var nameLocation = WuxiaRegistries.TECHNIQUE_ASPECT.get().getKey(this);
		if (nameLocation == null) return new LinkedList<>();
		WuxiaLabel nameLabel = new WuxiaLabel(5, 2, Component.translatable("wuxiacraft.aspect." + nameLocation.getPath() + ".name"), 0xFFAA00);
		WuxiaLabelBox descriptionLabel = new WuxiaLabelBox(5, 12, 190, Component.translatable("Description: wuxiacraft.aspect." + nameLocation.getPath() + ".description"));
		LinkedList<AbstractWidget> widgets = new LinkedList<>();
		widgets.add(nameLabel);
		widgets.add(descriptionLabel);
		return widgets;
	}
}
