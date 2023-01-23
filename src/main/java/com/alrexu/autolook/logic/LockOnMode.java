package com.alrexu.autolook.logic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public enum LockOnMode {
	OnlyEnemies, AllEntities, OnlyPlayers;

	public boolean correct(Entity entity) {
		return switch (this) {
			case OnlyPlayers -> entity instanceof Player;
			default -> true;
		};
	}
}
