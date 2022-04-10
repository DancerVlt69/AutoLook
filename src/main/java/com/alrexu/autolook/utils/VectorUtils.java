package com.alrexu.autolook.utils;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class VectorUtils {
	public static double toPitch(Vector3d vec) {
		return -(Math.atan2(vec.y(), Math.sqrt(vec.x() * vec.x() + vec.z() * vec.z())) * 180.0 / Math.PI);

	}

	public static double toYaw(Vector3d vec) {
		return (Math.atan2(vec.z(), vec.x()) * 180.0 / Math.PI - 90);
	}

	// a=it.x, b=it.y, c=it.x, d=it.y
	// calculate ( a + bi ) / ( c + di )
	public static Vector2f divide(Vector2f it, Vector2f by) {
		float divideBy = by.x * by.x + by.y * by.y;
		return new Vector2f(
				(it.x * by.x + it.y * by.y) / divideBy,
				(-(it.x * by.y) + it.y * by.x) / divideBy
		);
	}

	public static Vector2f normalize(Vector2f vec) {
		if (vec.x == 0 && vec.y == 0) return vec;
		float length = (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
		return new Vector2f(vec.x / length, vec.y / length);
	}
}
