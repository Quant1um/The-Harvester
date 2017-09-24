package net.quantium.harvester.entity;

import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class SpikeEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float yy;
	private float xx;
	private int time;
	public SpikeEntity(int x, int y, float xx, float yy){
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
	}

	@Override
	public void init() {
		hitbox = new Hitbox(1, 1, 0, 0);
	}

	@Override
	public void update() {
		time++;
		
		xx *= 0.95f;
		yy *= 0.95f;
		move((int)xx, (int)yy);
		if(time >= 120) remove();
	}

	@Override
	public void render(Renderer render) {
		render.get().put(x, y, 762);
	}

	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity)
			((PlayerEntity) ent).hit(1);
	}

	@Override
	public boolean isPassable(Entity ent) {
		return !(ent instanceof PlayerEntity);
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		return false;
	}

}
