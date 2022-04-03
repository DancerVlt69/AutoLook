package com.alrexu.autolook.handler;

import com.alrexu.autolook.AutoLookMod;
import com.alrexu.autolook.input.KeyBindings;
import com.alrexu.autolook.input.KeyRecorder;
import com.alrexu.autolook.logic.LockOn;
import com.alrexu.autolook.logic.LockOnHolder;
import com.alrexu.autolook.utils.LockOnUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
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
		if (!KeyBindings.getShiftKeyBinding().isDown()) {
			if (KeyRecorder.keySetLockState.isPressed()) {
				Entity entity = LockOnUtils.getLookingTargetEntity(player, 30, AutoLookMod.getTargetMode());
				if (entity != null) {
					holder.addLockOn(new LockOn(entity));
				} else {
					holder.addLockOn(new LockOn(player.position()));
				}
			} else if (KeyRecorder.keyAimState.isPressed()) {
				Entity entity = LockOnUtils.getLookingTargetEntity(player, 30, AutoLookMod.getTargetMode());
				if (entity != null) {
					holder.aimTo(new LockOn(entity));
					player.displayClientMessage(new TranslationTextComponent("autolook.message.locked"), true);
				}
			}
		} else {
			if (KeyRecorder.keySetLockState.isPressed()) {
				holder.removeAll();
				player.displayClientMessage(new TranslationTextComponent("autolook.message.remove.aim"), true);
			} else if (KeyRecorder.keyAimState.isPressed()) {
				holder.removeAim();
				player.displayClientMessage(new TranslationTextComponent("autolook.message.cancel.aim"), true);
			}
		}
		holder.tick();
	}
}
