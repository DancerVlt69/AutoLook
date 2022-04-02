package com.alrexu.lockon.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class PlayerUtils {
	public static void faceTo(PlayerEntity player, Vector3d vec, float partialTick) {
		Vector3d vector = vec.add(
				EntityUtils.getEyePositionWithPartialTick(player, partialTick).reverse()
		);
		player.yRot = (float) VectorUtils.toYaw(vector);
		player.xRot = (float) VectorUtils.toPitch(vector);
	}

}
