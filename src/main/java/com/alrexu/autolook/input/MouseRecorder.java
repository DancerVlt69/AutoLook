package com.alrexu.autolook.input;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MouseRecorder {
	private static final MouseState state = new MouseState();

	public static MouseState getState() {
		return state;
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent tickEvent) {
		if (tickEvent.phase == TickEvent.Phase.END) return;
		state.record(Minecraft.getInstance().mouseHandler);
	}

	public static class MouseState {
		private static final double THRESHOLD_OF_SHAKE_HORIZONTAL = 250;
		private static final double THRESHOLD_OF_SHAKE_VERTICAL = 200;

		private void record(MouseHelper mouse) {
			oldXPos = xPos;
			oldYPos = yPos;
			xPos = mouse.xpos();
			yPos = mouse.ypos();
			xVelocity = xPos - oldXPos;
			yVelocity = yPos - oldYPos;

			leftShaken = xVelocity < -THRESHOLD_OF_SHAKE_HORIZONTAL;
			if (alreadyLeftShaken) {
				alreadyLeftShaken = leftShaken;
				leftShaken = false;
			} else {
				alreadyLeftShaken = leftShaken;
			}
			rightShaken = xVelocity > THRESHOLD_OF_SHAKE_HORIZONTAL;
			if (alreadyRightShaken) {
				alreadyRightShaken = rightShaken;
				rightShaken = false;
			} else {
				alreadyRightShaken = rightShaken;
			}
			upShaken = yVelocity > THRESHOLD_OF_SHAKE_VERTICAL;
			if (alreadyUpShaken) {
				alreadyUpShaken = upShaken;
				upShaken = false;
			} else {
				alreadyUpShaken = upShaken;
			}
			downShaken = yVelocity < -THRESHOLD_OF_SHAKE_VERTICAL;
			if (alreadyDownShaken) {
				alreadyDownShaken = downShaken;
				downShaken = false;
			} else {
				alreadyDownShaken = downShaken;
			}
		}

		double xPos = 0;
		double oldXPos = 0;
		double yPos = 0;
		double oldYPos = 0;
		double xVelocity = 0;
		double yVelocity = 0;
		boolean leftShaken = false;
		boolean rightShaken = false;
		boolean upShaken = false;
		boolean downShaken = false;
		boolean alreadyLeftShaken = false;
		boolean alreadyRightShaken = false;
		boolean alreadyUpShaken = false;
		boolean alreadyDownShaken = false;

		public boolean isDownShaken() {
			return downShaken;
		}

		public boolean isLeftShaken() {
			return leftShaken;
		}

		public boolean isRightShaken() {
			return rightShaken;
		}

		public boolean isUpShaken() {
			return upShaken;
		}
	}
}
