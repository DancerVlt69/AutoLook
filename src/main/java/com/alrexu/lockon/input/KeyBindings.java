package com.alrexu.lockon.input;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
	private static final KeyBinding aimKeyBinding = new KeyBinding("key.lockon.aim", GLFW.GLFW_KEY_N, "key.categories.lockon");
	private static final KeyBinding setLockKeyBinding = new KeyBinding("key.lockon.set", GLFW.GLFW_KEY_M, "key.categories.lockon");
	private static final GameSettings settings = Minecraft.getInstance().options;

	public static KeyBinding getAimKeyBinding() {
		return aimKeyBinding;
	}

	public static KeyBinding getSetLockKeyBinding() {
		return setLockKeyBinding;
	}

	public static KeyBinding getShiftKeyBinding() {
		return settings.keyShift;
	}

	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(aimKeyBinding);
		ClientRegistry.registerKeyBinding(setLockKeyBinding);
	}
}
