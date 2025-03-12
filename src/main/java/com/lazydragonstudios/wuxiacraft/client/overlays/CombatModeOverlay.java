package com.lazydragonstudios.wuxiacraft.client.overlays;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.ICultivation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class CombatModeOverlay implements IIngameOverlay {
   public static final ResourceLocation COMBAT_MODE = new ResourceLocation("wuxiacraft", "textures/gui/overlay/combat_mode.png");

	public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;
		int i = width - 80;
		int j = height - 30;
         ICultivation cultivation = Cultivation.get(mc.player);
         RenderSystem.setShaderTexture(0, COMBAT_MODE);
         if (cultivation.isCombat()) {
            GuiComponent.blit(mStack, i, j, 64, 16, 0, 0, 64, 15, 64, 15);
         }
      
   }
}
