package net.quantium.harvester.entity.particle;

import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.world.World;

public abstract class ParticleEntity extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int time;
	protected int lifetime = 50;
	
	@Override
	public void update(){
		time++;
		if(time >= lifetime) this.remove();
	}
	
	public int getTime(){
		return time;
	}
	
	@Override
	public boolean isPassable(Entity ent) {
		return true;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		return false;
	}
	
	@Override
	public void bump(Entity ent) {}
}
