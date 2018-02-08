package net.quantium.harvester.entity;

import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class BuildableEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final int type;
	
	protected int clicks = 0;
	
	public Inventory inventory;
	
	public int data0;
	
	public BuildableEntity(int type){
		this.type = type;
		inventory = new Inventory(BuildableInfo.Registry.get(type).getInventorySize());
	}

	@Override
	public void init() {
		hitbox = new Hitbox(16, 5, -8, -3);
	}

	@Override
	public void update() {
		if(clicks > 0) clicks--;
		BuildableInfo.Registry.get(type).update(this);
	}

	@Override
	public void render(Renderer render) {
		render.get().draw(x - 12, y - 16, type * 3, 19, 3, 3, "sheet0", 0);		
	}

	@Override
	public void bump(Entity ent) {
		
	}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot item) {
		if(im == InteractionMode.RIGHT){
			BuildableInfo.Registry.get(type).onInteract(world2, playerEntity, im, item, this);
		}else if(im == InteractionMode.LEFT){
			clicks += 50;
			
			if(clicks >= 60){
				playerEntity.inventory.add(new ItemSlot((byte) type, 0, 1));
				this.remove();
			}
		}
		return true;
	}
	
	public void remove(){
		super.remove();
		for(int i = 0; i < inventory.size(); i++)
			if(inventory.get(i) != null)
				world.throwItem(x, y, inventory.get(i));
	}

}
