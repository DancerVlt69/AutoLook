package com.alrexu.autolook.input;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
	private static final KeyBinding aimKeyBinding = new KeyBinding("key.autolook.aim", GLFW.GLFW_KEY_N, "key.categories.autolook");
	private static final KeyBinding autoLockToggleBinding = new KeyBinding("key.autolook.autolock", GLFW.GLFW_KEY_M, "key.categories.autolook");
	private static final GameSettings settings = Minecraft.getInstance().options;

	public static KeyBinding getAimKeyBinding() {
		return aimKeyBinding;
	}

	public static KeyBinding getAutoLockToggleBinding() {
		return autoLockToggleBinding;
	}

	public static KeyBinding getShiftKeyBinding() {
		return settings.keyShift;
	}

	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(aimKeyBinding);
		ClientRegistry.registerKeyBinding(autoLockToggleBinding);
	}
}
