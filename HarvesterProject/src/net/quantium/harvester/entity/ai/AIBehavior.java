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
	
	private static final int DISTANCE_THRESHOLD = 8;
	private static final int DISTANCE_CHECK_OFFSET = 4;
	
	private int getHeatmapOffset(int x, int y){
		World world = getWorld();
		
		if(world.player == null)
			return Main.GLOBAL_RANDOM.nextInt(3) - 1;
		
		int cx = getEntity().x;
		int cy = getEntity().y;
		
		int a = world.player.sqrDistanceTo(cx - x * DISTANCE_CHECK_OFFSET, cy - y * DISTANCE_CHECK_OFFSET);
		int b = world.player.sqrDistanceTo(cx + x * DISTANCE_CHECK_OFFSET, cy + y * DISTANCE_CHECK_OFFSET);
		int diff = a - b;
		
		int desired = 0;
		if(Math.abs(diff) > DISTANCE_THRESHOLD)
			desired = MathUtils.sign(diff);
		else
			return Main.GLOBAL_RANDOM.nextInt(3) - 1;
		
		if(!world.isTilePassableBy(getEntity(), (cx >> 4) + desired * x, (cy >> 4) + desired * y))
			return 0;
		return desired;
	}
	
	public int getHeatmapOffsetX(){
		return getHeatmapOffset(1, 0);
	}
	
	public int getHeatmapOffsetY(){
		return getHeatmapOffset(0, 1);
	}
}


