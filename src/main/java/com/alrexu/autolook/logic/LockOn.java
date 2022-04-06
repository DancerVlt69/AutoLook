package com.alrexu.autolook.logic;

import com.alrexu.autolook.utils.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class LockOn {
	private Entity targetEntity;
	private Vector3d targetPoint;
	private boolean removed = false;

	public LockOn(Entity entity) {
		targetEntity = entity;
		targetPoint = null;
	}

	public LockOn(Vector3d point) {
		targetPoint = point;
		targetEntity = null;
	}

	public boolean isTargetingEntity() {
		return targetEntity != null;
	}

	public boolean isTargetingPoint() {
		return targetPoint != null;
	}

	@Nullable
	public Entity getTargetEntity() {
		return targetEntity;
	}

	@Nullable
	public Vector3d getTargetPoint() {
		return targetPoint;
	}

	@Nullable
	public Vector3d getPoint() {
		if (targetEntity != null) return EntityUtils.getCenterBoundingBox(targetEntity, 0);
		if (targetPoint != null) return targetPoint;
		return null;
	}

	@Nullable
	public Vector3d getOriginalPoint() {
		if (targetEntity != null) return targetEntity.position();
		if (targetPoint != null) return targetPoint;
		return null;
	}

	@Nullable
	public Vector3d getPoint(float partialTick) {
		if (targetEntity != null) {
			if (targetEntity instanceof ItemEntity) {
				return targetEntity.position().add(0, 0.4, 0);
			} else return EntityUtils.getCenterBoundingBox(targetEntity, partialTick);
		}
		if (targetPoint != null) return targetPoint;
		return null;
	}

	public void tick() {
		if (targetEntity != null) {
			if (targetEntity.removed) {
				unLock();
			}
		}
	}

	public void unLock() {
		targetEntity = null;
		targetPoint = null;
		removed = true;
	}

	public boolean isLockingOn() {
		return !removed;
	}

	public boolean isUnLocked() {
		return removed;
	}

	public boolean hasSameTargetWith(LockOn lockOn) {
		if (this.isTargetingPoint() && lockOn.isTargetingPoint()) {
			return this.getTargetPoint().equals(lockOn.getTargetPoint());
		} else if (this.isTargetingEntity() && lockOn.isTargetingEntity()) {
			return this.getTargetEntity() == lockOn.getTargetEntity();
		} else return false;
	}
}
