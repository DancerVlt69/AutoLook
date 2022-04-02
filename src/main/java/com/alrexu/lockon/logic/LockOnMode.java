package com.alrexu.lockon.logic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public enum LockOnMode {
	OnlyEnemies, AllEntities, OnlyPlayers;

	public boolean correct(Entity entity) {
		switch (this) {
			case AllEntities:
				return true;
			case OnlyPlayers:
				return entity instanceof PlayerEntity;
		}
		return true;
	}
}
