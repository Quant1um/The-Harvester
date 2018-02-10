package net.quantium.harvester.entity.ai;

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
	
	private static final int HEATMAP_THRESHOLD = 6;
	private int getHeatmapOffset(int x, int y){
		World world = getWorld();
		int cx = getEntity().x >> 4;
		int cy = getEntity().y >> 4;
		
		int a = world.getTargetValue(cx - x, cy - y);
		int b = world.getTargetValue(cx + x, cy + y);
		int diff = b - a;
		
		int ply = 0;
		if(world.player != null)
			ply = x * (world.player.x - getEntity().x) + y * (world.player.y - getEntity().y);
	
		int desired = 0;
		if(Math.abs(diff) > HEATMAP_THRESHOLD)
			desired = MathUtils.sign(diff);
		else
			desired = MathUtils.sign(ply);
		
		if(!world.isTilePassableBy(getEntity(), cx + desired * x, cy + desired * y))
			return 0;
		return desired;
	}
	
	protected int getHeatmapOffsetX(){
		return getHeatmapOffset(1, 0);
	}
	
	protected int getHeatmapOffsetY(){
		return getHeatmapOffset(0, 1);
	}
}


