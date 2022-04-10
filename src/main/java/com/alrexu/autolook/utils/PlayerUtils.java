package com.alrexu.autolook.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class PlayerUtils {
	public static void faceTo(PlayerEntity player, Vector3d vec, float partialTick) {
		Vector3d vector = vec.subtract(
				EntityUtils.getEyePositionWithPartialTick(player, partialTick)
		);
		player.yRot = (float) VectorUtils.toYaw(vector);
		player.xRot = (float) VectorUtils.toPitch(vector);
	}

	public static void smoothlyFaceTo(PlayerEntity player, Vector3d vec, float partialTick) {
		Vector3d offset = vec.subtract(
				EntityUtils.getEyePositionWithPartialTick(player, partialTick)
		).normalize();
		Vector3d lookVec = player.getLookAngle().normalize();
		Vector3d calculatedVector = new Vector3d(
				MathHelper.lerp(0.4, lookVec.x(), offset.x()),
				MathHelper.lerp(0.4, lookVec.y(), offset.y()),
				MathHelper.lerp(0.4, lookVec.z(), offset.z())
		);
		player.yRot = (float) VectorUtils.toYaw(calculatedVector);
		player.xRot = (float) VectorUtils.toPitch(calculatedVector);
	}

}
