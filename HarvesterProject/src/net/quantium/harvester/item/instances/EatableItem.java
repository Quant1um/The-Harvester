package net.quantium.harvester.item.instances;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.world.World;

public class EatableItem extends AbstractItem {

	protected final int heal;
	public EatableItem(String name, int iconX, int iconY, int maxSize, int heal) {
		super(name, iconX, iconY, maxSize, 1);
		this.heal = heal;
	}

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
		if(ply.getHealth() < ply.getMaxHealth()) 
			if(slot.consume(1))
				ply.heal(getHeal());
		return false;
	}

	public int getHeal(){
		return this.heal;
	}
}
