package net.quantium.harvester.entity;

import net.quantium.harvester.entity.ai.AIBehavior;

public interface AIEntity<T extends Entity & AIEntity<T>>{

	public AIBehavior<T> behavior();
}
