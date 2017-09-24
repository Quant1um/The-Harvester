package net.quantium.harvester.item;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.world.World;

public abstract class EatableItem extends Item {


	@Override
	public ItemType getType() {
		return ItemType.OTHER;
	}

	@Override
	public int getPower() {
		return 1;
	}

	@Override
	public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
		if(ply.health < ply.maxHealth) 
			if(slot.consume(1))
				ply.health = Math.max(ply.maxHealth, ply.health + getHeal());
		return false;
	}

	public abstract int getHeal();

}
