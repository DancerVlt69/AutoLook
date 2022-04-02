package com.alrexu.lockon.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.text.ITextProperties;

public class FontUtils {
	public static void drawCenteredText(MatrixStack stack, ITextProperties text, int x, int y, int color) {
		FontRenderer fontRenderer = Minecraft.getInstance().font;
		int width = fontRenderer.width(text.getString());
		IRenderTypeBuffer.Impl renderTypeBuffer = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		fontRenderer.drawInBatch(text.getString(), x - (width >> 1), y - (fontRenderer.lineHeight >> 1), color, false, stack.last().pose(), renderTypeBuffer, true, 0, 15728880);
		renderTypeBuffer.endBatch();
	}
}
