package com.alrexu.lockon.handler;

import com.alrexu.lockon.LockOnMod;
import com.alrexu.lockon.input.KeyRecorder;
import com.alrexu.lockon.logic.LockOn;
import com.alrexu.lockon.logic.LockOnHolder;
import com.alrexu.lockon.utils.LockOnUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class TickEventHandler {
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) return;
		if (event.side == LogicalSide.SERVER) return;
		PlayerEntity player = event.player;
		if (!player.isLocalPlayer()) return;

		LockOnHolder holder = LockOnHolder.getInstance();

		if (KeyRecorder.keySetLockState.isPressed()) {
			Entity entity = LockOnUtils.getLookingTargetEntity(player, 30, LockOnMod.getTargetMode());
			if (entity != null) {
				holder.addLockOn(new LockOn(entity));
			} else {
				holder.addLockOn(new LockOn(player.position()));
			}
		} else if (KeyRecorder.keyAimState.isPressed()) {
			Entity entity = LockOnUtils.getLookingTargetEntity(player, 30, LockOnMod.getTargetMode());
			if (entity != null) holder.aimTo(new LockOn(entity));
		}
		if (KeyRecorder.keyAimState.isDoubleTapped() || KeyRecorder.keySetLockState.isDoubleTapped()) {
			holder.removeAll();
		}
		holder.tick();
	}
}
