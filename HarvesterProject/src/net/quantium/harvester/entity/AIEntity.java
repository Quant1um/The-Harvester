package net.quantium.harvester.entity;

import net.quantium.harvester.entity.ai.AIBehavior;
import net.quantium.harvester.world.World;

public interface AIEntity{

	@SuppressWarnings("rawtypes")
	public AIBehavior behavior();

	public static int getTargettedOffsetX(World w, int x, int y){
		int left = w.getTargetValue(x - 1, y);
		int right = w.getTargetValue(x + 1, y);
		int current = w.getTargetValue(x, y);
		return left > current ? -1 : right > current ? 1 : 0;
	}
	
	public static int getTargettedOffsetY(World w, int x, int y){
		int up = w.getTargetValue(x, y - 1);
		int down = w.getTargetValue(x, y + 1);
		int current = w.getTargetValue(x, y);
		return up > current ? -1 : down > current ? 1 : 0;
	}
}
