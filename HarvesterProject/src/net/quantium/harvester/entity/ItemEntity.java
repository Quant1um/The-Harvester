package net.quantium.harvester.entity;

import net.quantium.harvester.Main;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.instances.Item;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class ItemEntity extends Entity {

	private static final int LIFETIME = (int) (Main.TICKS_PER_SECOND * 60 * 4);
	private static final int BLINKING_TIME = (int) (LIFETIME - Main.TICKS_PER_SECOND * 3);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemSlot slot;
	private int time = 0;
	private float xx, yy;
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
		xx *= 0.9;
		yy *= 0.9;
		time++;
		if(time >= LIFETIME) remove();
	}

	@Override
	public void render(Renderer render) {
		if(slot == null) return;
		if(time >= BLINKING_TIME && ((Main.instance().getCounter() / 20) % 2) == 0)
			return;
		
		int offground = (Main.instance().getCounter() / 30) % 2;
		Item itemd = slot.getItem();
		render.get().drawTinted(x - 3, y - 3, itemd.getIconX() * 2, itemd.getIconY() * 2, 2, 2, 5000,"sheet0", 0);
		render.get().draw(x - 3, y - 4 - offground, itemd.getIconX() * 2, itemd.getIconY() * 2, 2, 2, "sheet0", 0);
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
