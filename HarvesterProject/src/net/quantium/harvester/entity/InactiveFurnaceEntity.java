package net.quantium.harvester.entity;

import net.quantium.harvester.entity.BuildableInfo.BuildableType;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class InactiveFurnaceEntity extends BuildableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int epoch;
	
	public InactiveFurnaceEntity() {
		super(BuildableType.FURNACE);
	}

	@Override
	public void update() {
		if(clicks > 0) clicks--;
	}

	@Override
	public void render(Renderer render) {
		render.get().draw(x - 12, y - 16, epoch * 3, 46, 3, 3, "sheet0", 0);		
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
		if(im == InteractionMode.LEFT){
			clicks += 30;
			world.damageParticle(x, y, 1);
			if(clicks >= 100){
				ItemSlot is = null;
				switch(epoch){
					case 0: is = new ItemSlot(Items.rock, 0, 2); break;
					case 1: is = new ItemSlot(Items.rock, 0, 6); break;
					case 2: is = new ItemSlot(Items.rock, 0, 8); break;
					case 3: is = new ItemSlot(Items.rock, 0, 10); break;
					case 4: is = new ItemSlot(Items.coal, 0, 2); break;
					case 5: is = new ItemSlot(Items.coal, 0, 3); break;
				}
				world.throwItem(x, y, is);
				this.remove();
			}
		}else if(im == InteractionMode.RIGHT)
		switch(epoch){
			case 0: if(playerEntity.inventory.reduce(new ItemSlot(Items.rock, 0, 5))) epoch++; break;
			case 1: if(playerEntity.inventory.reduce(new ItemSlot(Items.rock, 0, 5))) epoch++; break;
			case 2: if(playerEntity.inventory.reduce(new ItemSlot(Items.coal, 0, 3))) epoch++; break;
			case 3: if(playerEntity.inventory.reduce(new ItemSlot(Items.wood, 0, 3))) epoch++; break;
			case 4: if(playerEntity.inventory.reduce(new ItemSlot(Items.rock, 0, 5))) epoch++; break;
			case 5: if(playerEntity.inventory.reduce(new ItemSlot(Items.rock, 0, 7))) epoch++; 
			if(epoch >= 6){
				BuildableEntity e = new BuildableEntity(BuildableType.FURNACE);
				e.x = x;
				e.y = y;
				world.addEntity(e);
				remove();
			} break;
		}
		return true;
	}	
}
