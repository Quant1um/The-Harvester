package net.quantium.harvester.entity;

import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class CollidableTileEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollidableTileEntity(int x, int y){
		hitbox = World.TILE_HITBOX;
		this.x = x * World.ENTITY_TILE_COORDSCALE;
		this.y = y * World.ENTITY_TILE_COORDSCALE;
	}
	
	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Renderer render) {
	}

	@Override
	public void bump(Entity ent) {
	}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		return false;
	}
}
