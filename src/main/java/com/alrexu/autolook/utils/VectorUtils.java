package com.alrexu.autolook.utils;

import net.minecraft.util.math.vector.Vector3d;

public class VectorUtils {
	public static double toPitch(Vector3d vec) {
		return -(Math.atan2(vec.y(), Math.sqrt(vec.x() * vec.x() + vec.z() * vec.z())) * 180.0 / Math.PI);

	}

	public static double toYaw(Vector3d vec) {
		return (Math.atan2(vec.z(), vec.x()) * 180.0 / Math.PI - 90);
	}
}
