package com.lazydragonstudios.wuxiacraft.client.gui.widgets;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.technique.aspects.TechniqueAspect;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@ParametersAreNonnullByDefault
public class WuxiaCheckpointsWidget extends AbstractWidget {

	private BigDecimal proficiency;
	private ResourceLocation aspectLocation;
	private TechniqueAspect aspect;
	private final HashMap<TechniqueAspect.Checkpoint, Integer> checkpointsFill;


	public WuxiaCheckpointsWidget(int x, int y, int width, ResourceLocation aspect) {
		super(x, y, width, 12, Component.empty());
		checkpointsFill = new HashMap<>();
		setAspectLocation(aspect);
	}

	public void setAspectLocation(ResourceLocation aspectLocation) throws IllegalArgumentException {
		var aspect = WuxiaRegistries.TECHNIQUE_ASPECT.get().getValue(aspectLocation);
		if (aspect == null)
			throw new IllegalArgumentException("Wrong aspect location. No aspect found for '" + aspectLocation + "'!");
		this.aspectLocation = aspectLocation;
		this.aspect = aspect;
		checkpointsFill.clear();
		var maxAmount = this.aspect.checkpoints.getLast().proficiencyRequired();
		var iterator = this.aspect.checkpoints.iterator();
		var aux = iterator.next(); // this is always true because there is always a no_checkpoint at the beginning
		while (iterator.hasNext()) {
			var nextAux = iterator.next();
			checkpointsFill.put(aux, new BigDecimal(this.width)
					.multiply(nextAux.proficiencyRequired().subtract(aux.proficiencyRequired()))
					.divide(maxAmount, RoundingMode.HALF_UP).intValue());
			aux = nextAux;
		}
		checkpointsFill.put(aux, BigDecimal.ZERO.intValue());
	}

	public void setProficiency(BigDecimal proficiency) {
		this.proficiency = proficiency;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		var player = Minecraft.getInstance().player;
		if (player == null) return;
		var cultivation = Cultivation.get(player);
		this.proficiency = cultivation.getAspects().getAspectProficiency(this.aspectLocation);
		RenderSystem.enableBlend();

		//render the gb bar
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX(), this.getY(),
				1, 12,
				0, 25,
				1, 12,
				256, 256
		);
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + 1, this.getY(),
				this.width - 2, 12,
				1, 25,
				1, 12,
				256, 256
		);
		guiGraphics.blit(WuxiaButton.UI_CONTROLS,
				this.getX() + this.width - 1, this.getY(),
				1, 12,
				2, 25,
				1, 12,
				256, 256
		);
		//render the actual fills now
		var currentCheckpoint = this.aspect.getCurrentCheckpoint(proficiency);
		int currentFillLeft = 0;
		int index = 0; //using indexOf while iterating a linked list is rather stupid so just add +1 to each iteration

		for (var checkpoint : this.aspect.checkpoints) {
			int fillWidth = checkpointsFill.get(checkpoint);
			if (checkpoint == currentCheckpoint) {
				if( this.aspect.checkpoints.getLast() == checkpoint) continue;
				var nextCheckpoint = this.aspect.checkpoints.get(index+1);
                int barFill = new BigDecimal(fillWidth)
                        .multiply(this.proficiency.subtract(checkpoint.proficiencyRequired()))
                        .divide(nextCheckpoint.proficiencyRequired().subtract(checkpoint.proficiencyRequired()).max(BigDecimal.ONE), RoundingMode.HALF_UP)
                        .intValue();
				guiGraphics.blit(WuxiaButton.UI_CONTROLS, // guiGraphics
						this.getX() + currentFillLeft + 1, this.getY() + 1, // in screen coords
						barFill, 10, //in screen width and height
						3 + index, 26, // in texture coords
						1, 10, // in texture width and height
						256, 256 // whole texture size (width and height)
				);
				break;
			} else {
				guiGraphics.blit(WuxiaButton.UI_CONTROLS,
						this.getX() + currentFillLeft + 1, this.getY() + 1,
						fillWidth, 10,
						3 + index, 26,
						1, 10,
						256, 256
				);
			}

			
			currentFillLeft += fillWidth;
			index++;
		}
		
		currentFillLeft = 0;
		for (var checkpoint : this.aspect.checkpoints) {
			int fillWidth = checkpointsFill.get(checkpoint);
			if( this.aspect.checkpoints.indexOf(checkpoint) < this.aspect.checkpoints.size() -2) {

				guiGraphics.blit(WuxiaButton.UI_CONTROLS, // guiGraphics
						this.getX() + currentFillLeft + fillWidth, this.getY() , // in screen coords
						2, 11, //in screen width and height
						11, 25, // in texture coords
						3, 11, // in texture width and height
						256, 256 // whole texture size (width and height)
				);
			}

			currentFillLeft += fillWidth;
		}
		RenderSystem.disableBlend();
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
	}
}
