package com.alrexu.autolook.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyRecorder {
	public static final KeyState keyAimState = new KeyState();
	public static final KeyState keySetLockState = new KeyState();

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.START) return;

		keyAimState.record(KeyBindings.getAimKeyBinding());
		keySetLockState.record(KeyBindings.getSetLockKeyBinding());
	}

	public static class KeyState {
		private void record(KeyBinding keyBinding) {
			pressed = (keyBinding.isDown() && tickKeyDown == 0);
			doubleTapped = (keyBinding.isDown() && 0 < tickNotKeyDown && tickNotKeyDown <= 2);
			if (keyBinding.isDown()) {
				tickKeyDown++;
				tickNotKeyDown = 0;
			} else {
				tickKeyDown = 0;
				tickNotKeyDown++;
			}
		}

		private boolean pressed = false;
		private boolean doubleTapped = false;
		private int tickKeyDown = 0;
		private int tickNotKeyDown = 0;

		public boolean isPressed() {
			return pressed;
		}

		public boolean isDoubleTapped() {
			return doubleTapped;
		}

		public int getTickKeyDown() {
			return tickKeyDown;
		}

		public int getTickNotKeyDown() {
			return tickNotKeyDown;
		}
	}
}
