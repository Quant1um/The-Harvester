package net.quantium.harvester.item;

import net.quantium.harvester.entity.BuildableEntity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.world.World;

public class SettableItem extends Item {

	private final String name;
	private final int type;
	
	public SettableItem(String name, int type){
		this.name = name;
		this.type = type;
	}
	
	@Override
	public int getIconX() {
		return type;
	}

	@Override
	public int getIconY() {
		return 11;
	}

	@Override
	public int getMaxSizeInSlot() {
		return 2;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ItemType getType() {
		return ItemType.SETTABLE;
	}

	@Override
	public int getPower() {
		return 1;
	}

	@Override
	public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
		BuildableEntity e = new BuildableEntity(type);
		e.x = x;
		e.y = y;
		w.addEntity(e);
		return slot.consume(1);
	}

}
