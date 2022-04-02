package com.alrexu.lockon.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class EntityUtils {
	public static Vector3d getCenterPoint(Entity entity) {
		return new Vector3d(entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ());
	}

	public static Vector3d getEyePositionWithPartialTick(Entity entity, float partialTick) {
		return new Vector3d(
				entity.getX() + (entity.getX() - entity.xOld) * partialTick,
				entity.getY() + (entity.getY() - entity.yOld) * partialTick + entity.getEyeHeight(),
				entity.getZ() + (entity.getZ() - entity.zOld) * partialTick
		);
	}

	public static Vector3d getCenterBoundingBox(Entity entity, float partialTick) {
		AxisAlignedBB bb = entity.getBoundingBox();
		return new Vector3d(
				(bb.maxX + bb.minX) / 2 + (entity.getX() - entity.xOld) * partialTick,
				(bb.maxY + bb.minY) / 2 + (entity.getY() - entity.yOld) * partialTick,
				(bb.maxZ + bb.minZ) / 2 + (entity.getZ() - entity.zOld) * partialTick
		);
	}

	public static Vector3d getitionWithPartialTick(Entity entity, float partialTick) {
		return new Vector3d(
				entity.getX() + (entity.getX() - entity.xOld) * partialTick,
				entity.getY() + (entity.getY() - entity.yOld) * partialTick,
				entity.getZ() + (entity.getZ() - entity.zOld) * partialTick
		);
	}
}
