package net.quantium.harvester.entity;

import net.quantium.harvester.item.Item;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class ItemEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemSlot slot;
	int time = 0;
	float xx, yy;
	public ItemEntity(ItemSlot it, float xx, float yy){
		slot = it.copy();
		this.xx = xx * 2.4f;
		this.yy = yy * 2.4f;
	}
	
	@Override
	public void init() {

	}

	@Override
	public void update() {
		x += (int)xx;
		y += (int)yy;
		xx *= 0.8;
		yy *= 0.8;
		time++;
		if(time >= 60 * 60 * 5) remove();
	}

	@Override
	public void render(Renderer render) {
		if(slot == null) return;
		Item itemd = Item.Registry.get(slot.getItem());
		render.get().draw(x - 3, y - 3, itemd.getIconX() * 2, itemd.getIconY() * 2, 2, 2, "sheet0", 0);
	}

	@Override
	public void bump(Entity ent) {
	}

	@Override
	public boolean isPassable(Entity ent) {
		return true;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		ItemSlot s = playerEntity.inventory.add(slot);
		this.slot = s;
		if(this.slot == null)
			remove();
		return true;
	}

}
