package com.alrexu.lockon.logic;

import com.alrexu.lockon.render.aim.AimRenderer;
import com.mojang.datafixers.util.Pair;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.ListIterator;

public class LockOnHolder {
	Pair<LockOn, AimRenderer> lookedAim = null;
	LinkedList<Pair<LockOn, AimRenderer>> lockOnList = new LinkedList<>();

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
			lookedAim = new Pair<>(lockOn, new AimRenderer(lockOn));
		} else {
			lookedAim = aim;
		}
		lockOnList.add(lookedAim);
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
