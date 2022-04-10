package com.alrexu.autolook.handler;

import com.alrexu.autolook.AutoLookMod;
import com.alrexu.autolook.input.KeyBindings;
import com.alrexu.autolook.input.KeyRecorder;
import com.alrexu.autolook.input.MouseRecorder;
import com.alrexu.autolook.logic.LockOn;
import com.alrexu.autolook.logic.LockOnHolder;
import com.alrexu.autolook.utils.LockOnUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
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

		boolean aimChanged = false;
		LockOnHolder holder = LockOnHolder.getInstance();
		if (KeyRecorder.keyAutoLockState.isPressed()) {
			AutoLookMod.changeAutoLock();
			player.displayClientMessage(new StringTextComponent(Boolean.toString(AutoLookMod.isAutoLock())), false);
		}
		if (AutoLookMod.isAutoLock()) {
			holder.updateAutoLock(player);
		}
		if (KeyRecorder.keyAimState.isPressed()) {
			if (holder.isLookingSomething()) {
				if (KeyBindings.getShiftKeyBinding().isDown()) {
					holder.removeAll();
					player.displayClientMessage(new TranslationTextComponent("autolook.message.remove.aim"), true);
				} else {
					holder.removeAim();
					player.displayClientMessage(new TranslationTextComponent("autolook.message.cancel.aim"), true);
				}
			} else {
				Entity entity = LockOnUtils.getLookingTargetEntity(player, 30, AutoLookMod.getTargetMode());
				LockOn lock;
				if (entity == null) lock = new LockOn(player.position());
				else lock = new LockOn(entity);
				if (KeyBindings.getShiftKeyBinding().isDown()) {
					holder.addLockOn(lock);
					player.playSound(SoundEvents.LEVER_CLICK, 1, 1);
				} else {
					holder.aimTo(lock);
					aimChanged = true;
					player.displayClientMessage(new TranslationTextComponent("autolook.message.locked"), true);
				}
			}
		}
		MouseRecorder.MouseState mouse = MouseRecorder.getState();
		if (mouse.isUpShaken()) {
			holder.changeAimToward(LockOnHolder.Direction.UP);
			aimChanged = true;
		}
		if (mouse.isDownShaken()) {
			holder.changeAimToward(LockOnHolder.Direction.DOWN);
			aimChanged = true;
		}
		if (mouse.isLeftShaken()) {
			holder.changeAimToward(LockOnHolder.Direction.LEFT);
			aimChanged = true;
		}
		if (mouse.isRightShaken()) {
			holder.changeAimToward(LockOnHolder.Direction.RIGHT);
			aimChanged = true;
		}
		if (aimChanged) {
			player.playSound(SoundEvents.BOOK_PAGE_TURN, 1.0f, 1.0f);
		}
		holder.tick();
	}
}
