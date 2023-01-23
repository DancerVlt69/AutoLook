package com.alrexu.autolook;

import com.alrexu.autolook.handler.RenderEventHandler;
import com.alrexu.autolook.handler.TickEventHandler;
import com.alrexu.autolook.input.KeyBindings;
import com.alrexu.autolook.input.KeyRecorder;
import com.alrexu.autolook.input.MouseRecorder;
import com.alrexu.autolook.logic.LockOnMode;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(AutoLookMod.MODID)
public class AutoLookMod {
	public static final String MODID = "autolook";
	private static final Logger LOGGER = LogManager.getLogger();
	private static LockOnMode targetMode = LockOnMode.AllEntities;
	private static boolean autolock = false;

	public static LockOnMode getTargetMode() {
		return targetMode;
	}

	public static boolean isAutoLock() {
		return autolock;
	}

	public static void changeAutoLock() {
		autolock = !autolock;
	}

	public static void setTargetMode(LockOnMode mode) {
		targetMode = mode;
	}

	public AutoLookMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyBindings::register);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
        MinecraftForge.EVENT_BUS.register(new KeyRecorder());
        MinecraftForge.EVENT_BUS.register(new TickEventHandler());
		MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
		MinecraftForge.EVENT_BUS.register(new MouseRecorder());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        // @SubscribeEvent
        // public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
		// register a new block here
	}
}
