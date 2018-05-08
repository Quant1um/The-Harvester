package net.quantium.harvester.item.instances;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.world.World;

public class AbstractItem extends Item{

	private final int iconX, iconY, maxSize, power;
	private final String name;
	
	public AbstractItem(String name, int iconX, int iconY, int maxSize, int power){
		this.name = name;
		this.iconX = iconX;
		this.iconY = iconY;
		this.maxSize = maxSize;
		this.power = power;
	}
	
	@Override
	public int getIconX() {
		return iconX;
	}

	@Override
	public int getIconY() {
		return iconY;
	}

	@Override
	public int getMaxSizeInSlot() {
		return maxSize;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ItemType getType() {
		return ItemType.OTHER;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
		return false;
	}
}
