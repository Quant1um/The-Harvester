package net.quantium.harvester.entity.ai;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.utilities.MathUtils;
import net.quantium.harvester.world.World;

public abstract class AIBehavior<T extends Entity> {
	private T entity;
	
	public AIBehavior(T entity){
		this.entity = entity;
	}
	
	protected T getEntity(){
		return entity;
	}
	
	protected World getWorld(){
		return entity.world;
	}
	
	public abstract void update();
	
	//TODO merge heatmap x and y functions and fix local optima bug
	public int getHeatmapOffsetX(){
		int x = getEntity().x >> World.ENTITY_TILE_COORDSHIFT;
		int y = getEntity().y >> World.ENTITY_TILE_COORDSHIFT;
		int diff = getWorld().getAITarget(x + 1, y) - getWorld().getAITarget(x - 1, y);
		if(diff == 0){
			if(getWorld().player == null)
				return Main.RANDOM.nextInt(3) - 1;
			return MathUtils.sign(getWorld().player.x - getEntity().x);
		}
		return diff;
	}
	
	public int getHeatmapOffsetY(){
		int x = getEntity().x >> World.ENTITY_TILE_COORDSHIFT;
		int y = getEntity().y >> World.ENTITY_TILE_COORDSHIFT;
		int diff = getWorld().getAITarget(x, y + 1) - getWorld().getAITarget(x, y - 1);
		if(diff == 0){
			if(getWorld().player == null)
				return Main.RANDOM.nextInt(3) - 1;
			return MathUtils.sign(getWorld().player.y - getEntity().y);
		}
		return diff;
	}
}


