package net.quantium.harvester.entity.buildable;

import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableBehavior;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableType;
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
	
	public final BuildableType type;
	
	protected int clicks = 0;
	public final Inventory inventory;
	
	public final Object container;
	
	public BuildableEntity(BuildableType type){
		this.type = type;
		this.inventory = new Inventory(getBehaviour().inventorySize);
		this.container = getBehaviour().instantiateContainer();
	}

	public BuildableBehavior getBehaviour(){
		return BuildableInfo.Registry.get(type);
	}
	
	@Override
	public void init() {
		hitbox = new Hitbox(16, 5, -8, -3);
	}

	@Override
	public void update() {
		if(clicks > 0) clicks--;
		getBehaviour().update(this);
	}

	@Override
	public void render(Renderer render) {
		render.get().draw(x - 12, y - 16, BuildableInfo.Registry.get(type).spriteOffset * 3, 19, 3, 3, "sheet0", 0);		
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
				playerEntity.inventory.add(new ItemSlot(BuildableInfo.Registry.get(type).item, 0, 1));
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
