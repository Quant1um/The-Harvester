package net.quantium.harvester.item;

import net.quantium.harvester.entity.BuildableEntity;
import net.quantium.harvester.entity.BuildableInfo;
import net.quantium.harvester.entity.BuildableInfo.BuildableType;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.world.World;

public class BuildableItem extends Item {

	private final String name;
	private final BuildableType type;
	
	public BuildableItem(String name, BuildableType type){
		this.name = name;
		this.type = type;
	}
	
	@Override
	public int getIconX() {
		return BuildableInfo.Registry.get(type).spriteOffset;
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
		return ItemType.BUILDABLE;
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
