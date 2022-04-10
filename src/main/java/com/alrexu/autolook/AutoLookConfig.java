package com.alrexu.autolook;

import net.minecraftforge.common.ForgeConfigSpec;

public class AutoLookConfig {
	private static final ForgeConfigSpec.Builder C_BUILDER = new ForgeConfigSpec.Builder();

	public static final Client CONFIG_CLIENT = new Client(C_BUILDER);

	public static class Client {
		Client(ForgeConfigSpec.Builder builder) {

		}
	}

	public static final ForgeConfigSpec CLIENT_SPEC = C_BUILDER.build();
}
