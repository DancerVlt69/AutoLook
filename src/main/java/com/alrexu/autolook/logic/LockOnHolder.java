package com.alrexu.autolook.logic;

import com.alrexu.autolook.render.aim.AimRenderer;
import com.alrexu.autolook.utils.VectorUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.*;

public class LockOnHolder {
	Pair<LockOn, AimRenderer> lookedAim = null;
	LinkedList<Pair<LockOn, AimRenderer>> lockOnList = new LinkedList<>();
	int lookingTick = 0;

	public int getLookingTick() {
		return lookingTick;
	}

	public boolean isLookingSomething() {
		return lookedAim != null;
	}

	private static LockOnHolder instance = null;

	public static LockOnHolder getInstance() {
		if (instance == null) instance = new LockOnHolder();
		return instance;
	}

	public LinkedList<Pair<LockOn, AimRenderer>> getLockOnList() {
		return lockOnList;
	}

	@Nullable
	public Pair<LockOn, AimRenderer> getLookedAim() {
		return lookedAim;
	}

	public void addLockOn(LockOn lockOn) {
		if (getLockedTarget(lockOn) != null) return;

		lockOnList.add(new Pair<>(lockOn, new AimRenderer(lockOn)));
	}

	@Nullable
	public Pair<LockOn, AimRenderer> getLockedTarget(LockOn lockOn) {
		return lockOnList.stream().filter((item) -> item.getFirst().hasSameTargetWith(lockOn)).findFirst().orElse(null);
	}

	public void aimTo(LockOn lockOn) {
		Pair<LockOn, AimRenderer> aim = getLockedTarget(lockOn);
		if (aim == null) {
			aim = new Pair<>(lockOn, new AimRenderer(lockOn));
		}
		aimTo(aim);
		lockOnList.add(lookedAim);
	}

	private void aimTo(Pair<LockOn, AimRenderer> aim) {
		lookingTick = 0;
		lookedAim = aim;
	}

	public enum Direction {UP, DOWN, LEFT, RIGHT}

	public void changeAimToward(Direction direction) {
		if (lookedAim == null) return;
		Minecraft mc = Minecraft.getInstance();
		Vector3f lookVec = mc.gameRenderer.getMainCamera().getLookVector();
		Vector3d position = mc.gameRenderer.getMainCamera().getPosition();
		lockOnList.stream()
				.map((item) -> {
					if (item.getFirst() == lookedAim.getFirst()) return null;
					Vector3d targetPosition = item.getFirst().getPoint();
					if (targetPosition == null) return null;
					Vector3d offsetVec = targetPosition.subtract(position).normalize();

					Vector2f lookVec_Pitch = VectorUtils.normalize(new Vector2f(
							(float) Math.sqrt(lookVec.x() * lookVec.x() + lookVec.z() * lookVec.z()), lookVec.y()
					));
					Vector2f lookVec_Yaw = VectorUtils.normalize(new Vector2f(lookVec.x(), lookVec.z()));

					Vector2f offset_Pitch = VectorUtils.normalize(new Vector2f(
							(float) Math.sqrt(offsetVec.x * offsetVec.x + offsetVec.z * offsetVec.z), (float) offsetVec.y
					));
					Vector2f offset_Yaw = VectorUtils.normalize(new Vector2f((float) offsetVec.x(), (float) offsetVec.z()));

					Vector2f dividedPitch = VectorUtils.divide(offset_Pitch, lookVec_Pitch);
					Vector2f dividedYaw = VectorUtils.divide(offset_Yaw, lookVec_Yaw);

					if (dividedPitch.x < 0 || dividedYaw.x < 0) return null;

					return Triple.of(item, Math.asin(dividedPitch.y), Math.asin(dividedYaw.y));
				})
				.filter(Objects::nonNull)
				.filter((item) -> {
					switch (direction) {
						case RIGHT:
							return item.getRight() > 0;
						case LEFT:
							return item.getRight() < 0;
						case UP:
							return item.getMiddle() < 0;
						case DOWN:
							return item.getMiddle() > 0;
					}
					return false;
				})
				.map((item) -> {
					double score = 0;
					switch (direction) {
						case RIGHT:
						case LEFT:
							score += Math.abs(item.getMiddle()) * 3;
							score += Math.abs(item.getRight());
							break;

						case UP:
						case DOWN:
							score += Math.abs(item.getMiddle());
							score += Math.abs(item.getRight()) * 3;
							break;
					}
					return Pair.of(item.getLeft(), score);
				})
				.min(Comparator.comparingDouble(Pair::getSecond))
				.ifPresent((it) -> {
					aimTo(it.getFirst());
				});
	}

	public void updateAutoLock(PlayerEntity player) {
		final float extent = 15;
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
		list.stream().map(LockOn::new).forEach(this::addLockOn);
	}

	public void removeAll() {
		lookedAim = null;
		lockOnList.forEach(item -> item.getFirst().unLock());
		lockOnList = new LinkedList<>();
	}

	public void removeAim() {
		lookedAim = null;
	}

	public void tick() {
		ListIterator<Pair<LockOn, AimRenderer>> iterator = lockOnList.listIterator();
		if (lookedAim != null) {
			lookingTick++;
		}
		while (iterator.hasNext()) {
			Pair<LockOn, AimRenderer> item = iterator.next();
			LockOn lock = item.getFirst();
			lock.tick();
			item.getSecond().tick();
			if (lock.isUnLocked()) {
				if (lookedAim != null && lock.hasSameTargetWith(lookedAim.getFirst())) lookedAim = null;
				iterator.remove();
			}
		}
		if (lookedAim != null && lookedAim.getFirst().isUnLocked()) {
			lookedAim = null;
		}
	}

}
