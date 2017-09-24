package net.quantium.harvester.entity;

import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class DamageParticle extends ParticleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int damage;
	
	private float yy;
	
	public DamageParticle(int damage){
		this.damage = damage;
	}
	
	@Override
	public void particleUpdate() {
		yy += 0.3f;
		if(yy >= 27) remove();
		
	}

	@Override
	public void init() {
		lifetime = 200;
	}

	@Override
	public void render(Renderer render) {
		render.get().drawDamageText(x, (int) (y - yy), damage + "", (int)(9 - (yy / 3)));
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
		return false;
	}

}
