package net.quantium.harvester.entity.ai;

import net.quantium.harvester.entity.Entity;

public interface AIBehavior<T extends Entity> {
	public void update(T entity);
}


