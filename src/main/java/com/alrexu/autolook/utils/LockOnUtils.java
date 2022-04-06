package com.alrexu.autolook.utils;


import com.alrexu.autolook.logic.LockOnMode;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class LockOnUtils {
	@Nullable
	public static Entity getLookingTargetEntity(PlayerEntity player, double extent, LockOnMode targetMode) {
		World world = player.level;
		Minecraft mc = Minecraft.getInstance();
		AxisAlignedBB box = new AxisAlignedBB(
				player.getX() - extent,
				player.getY() - extent,
				player.getZ() - extent,
				player.getX() + extent,
				player.getY() + extent,
				player.getZ() + extent
		);
		List<Entity> list = world.getEntities(player, box);
		Vector3d playerVec = mc.gameRenderer.getMainCamera().getPosition();
		Vector3d lookVec = player.getLookAngle().normalize();
		Pair<Entity, Double> selected = list.stream()
				.map((item) -> {
					Vector3d offsetVec = EntityUtils.getCenterBoundingBox(item, 0).subtract(playerVec).normalize();
					return new Pair<>(item, offsetVec.dot(lookVec));
				})
				.max(Comparator.comparingDouble(Pair::getSecond))
				.orElse(null);
		if (selected == null || selected.getSecond() < 0.95) {
			return null;
		} else return selected.getFirst();
	}
}
