package com.alrexu.lockon.utils;


import com.alrexu.lockon.logic.LockOnMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LockOnUtils {
	@Nullable
	public static Entity getLookingTargetEntity(PlayerEntity player, double extent, LockOnMode targetMode) {
		World world = player.level;
		AxisAlignedBB box = new AxisAlignedBB(
				player.getX() - extent,
				player.getY() - extent,
				player.getZ() - extent,
				player.getX() + extent,
				player.getY() + extent,
				player.getZ() + extent
		);
		List<Entity> list = world.getEntities(player, box);
		double minDistance = extent;
		Entity target = null;
		Vector3d lookVec = player.getLookAngle();
		double lengthLookVec = lookVec.length();
		{
			for (Entity entity : list) {
				if (!targetMode.correct(entity)) continue;
				Vector3d entityPos = EntityUtils.getCenterBoundingBox(entity, 0);
				Vector3d offsetVec = new Vector3d(
						entityPos.x() - player.getX(),
						entityPos.y() - player.getY(),
						entityPos.z() - player.getZ()
				);
				double cos = lookVec.dot(offsetVec) / (lengthLookVec * offsetVec.length());
				double distance = offsetVec.length();
				if (cos > 0.94 && distance < minDistance) {
					target = entity;
					minDistance = distance;
				}
			}
		}
		return target;
	}
}

