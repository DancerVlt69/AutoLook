package com.alrexu.autolook.render.hud;

import com.alrexu.autolook.logic.LockOn;
import com.alrexu.autolook.render.aim.AimRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;

public class InformationHUD extends AbstractGui {
	ResourceLocation BACKGROUND = new ResourceLocation("minecraft", "textures/gui/demo_background.png");
	public float animationFunction(float phase) {
		return 1 - (phase - 1) * (phase - 1);
	}

	public void render(RenderGameOverlayEvent event, LockOn lockOn, AimRenderer aim) {
		if (lockOn.isUnLocked()) return;
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;
		MatrixStack stack = event.getMatrixStack();
		MainWindow window = mc.getWindow();
		final int width = window.getGuiScaledWidth();
		final int height = window.getGuiScaledHeight();
		ArrayList<String> content = new ArrayList<>(10);
		Vector3d pos = lockOn.getOriginalPoint();
		if (pos == null) return;

		Entity entity = lockOn.getTargetEntity();
		if (entity != null) {
			ITextComponent customName = entity.getCustomName();
			content.add(String.format("%s : §1%s", I18n.get("autolook.text.name"), (customName != null ? customName : entity.getDisplayName()).getString()));
		}
		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;
			String textColor = living.isDeadOrDying() ? "§4" : (living.getHealth() / living.getMaxHealth() <= 0.5) ? "§6" : "§2";
			content.add(String.format("%s : %s%4d/%4d", I18n.get("autolook.text.health"), textColor, (int) Math.round(living.getHealth()), (int) Math.round(living.getMaxHealth())));
		} else if (entity instanceof ItemEntity) {
			ItemEntity item = (ItemEntity) entity;
			content.add(String.format("%s : §2%4d", I18n.get("autolook.text.item.size"), item.getItem().getCount()));
		}
		content.add(String.format("%s : §1%7f", I18n.get("autolook.text.pos.x"), pos.x()));
		content.add(String.format("%s : §1%7f", I18n.get("autolook.text.pos.y"), pos.y()));
		content.add(String.format("%s : §1%7f", I18n.get("autolook.text.pos.z"), pos.z()));

		final int boxWidth = Math.max(width / 4, 10 + content.stream().map(font::width).max(Integer::compareTo).orElse(0));
		final int left;
		if (aim.getTick() < 5) {
			left = (int) (width - boxWidth * animationFunction((aim.getTick() + event.getPartialTicks()) / 5));
		} else left = (int) width - boxWidth;
		final int top = (int) (height * 0.10);
		final int boxHeight = (font.lineHeight + 1) * content.size() + 10/*10 is margin(5 + 5)*/;

		mc.textureManager.bind(BACKGROUND);
		blit(stack, left, top, 0, 0, boxWidth, 5, 256, 256);
		blit(stack, left, top + 5 + (font.lineHeight + 1) * content.size(), 0, 161, boxWidth, 5, 256, 256);
		for (int i = 0; i < content.size(); i++) {
			final int lineTop = top + 5 + (font.lineHeight + 1) * i;
			blit(stack, left, lineTop, 0, 5, boxWidth, font.lineHeight + 1, 256, 256);
		}
		for (int i = 0; i < content.size(); i++) {
			final int lineTop = top + 5 + (font.lineHeight + 1) * i;
			font.draw(stack, content.get(i), left + 5, lineTop, 0x333333);
		}
	}
}
