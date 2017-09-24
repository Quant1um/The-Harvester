package net.quantium.harvester.entity;

import java.io.Serializable;

import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.tile.Tile;
import net.quantium.harvester.world.PassingInfo;
import net.quantium.harvester.world.World;

public abstract class Entity implements Serializable, Comparable<Entity>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 24, y = 24;
	public Hitbox hitbox = new Hitbox(12, 12);
	public World world;
	public boolean removed;
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render(Renderer render);
	
	public abstract void bump(Entity ent);
	
	public abstract boolean isPassable(Entity ent);
	
	public void remove(){
		removed = true;
	}
	
	public void move(int xx, int yy){
		PassingInfo infx = world.tryPass(this, xx, 0);
		PassingInfo infy = world.tryPass(this, 0, yy);
		if(infx.isPassed()){
			x += xx;
			Tile.Registry.get(infx.getSteppedOn()).onInteract(world, infx.getTileX(), infx.getTileY(), this, InteractionMode.STEP);
			for(Entity e : infx.getBumped())
				e.bump(this);
		}
		if(infy.isPassed()){
			y += yy;
			Tile.Registry.get(infy.getSteppedOn()).onInteract(world, infy.getTileX(), infy.getTileY(), this, InteractionMode.STEP);
			for(Entity e : infy.getBumped())
				e.bump(this);
		}
	}
	
	public abstract boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active);
	
	public enum InteractionMode{
		LEFT, RIGHT, OTHER, BUTTON, STEP;
	}
	
	@Override
	public int compareTo(Entity b) {
		if(b == null) return 0;
		if (b.y < y) return 1;
		if (b.y > y) return -1;
		return 0;
	}
	
	public int sqrDistanceTo(Entity e){
		return (x - e.x) * (x - e.x) + (y - e.y) * (y - e.y);
	}
}
