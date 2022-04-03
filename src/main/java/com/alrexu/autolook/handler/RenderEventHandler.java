package com.alrexu.autolook.handler;

import com.alrexu.autolook.logic.LockOn;
import com.alrexu.autolook.logic.LockOnHolder;
import com.alrexu.autolook.render.aim.AimRenderer;
import com.alrexu.autolook.render.hud.InformationHUD;
import com.alrexu.autolook.utils.PlayerUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEventHandler {
	private InformationHUD informationHUD = new InformationHUD();

	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player == null) return;
		Pair<LockOn, AimRenderer> aim = LockOnHolder.getInstance().getLookedAim();
		if (aim == null) return;

		LockOn lockOn = aim.getFirst();
		Vector3d vec = lockOn.getPoint(event.renderTickTime);
		if (vec == null) return;
		PlayerUtils.faceTo(player, vec, event.renderTickTime);
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Pair<LockOn, AimRenderer> aim = LockOnHolder.getInstance().getLookedAim();
		if (aim != null) aim.getSecond().render(event);
		LockOnHolder.getInstance().getLockOnList().forEach(item -> item.getSecond().render(event));
	}

	@SubscribeEvent
	public void onGameOverlay(RenderGameOverlayEvent event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
		LockOnHolder holder = LockOnHolder.getInstance();
		if (holder.getLookedAim() != null) {
			informationHUD.render(event, holder.getLookedAim().getFirst(), holder.getLookedAim().getSecond());
		}
	}
}
