package net.quantium.harvester.entity.ai;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.SlimeEntity;
import net.quantium.harvester.world.World;

public class AISlime extends AIBehavior<SlimeEntity> {

	public static final int PLAYER_CHECK_DISTANCE = 16 * 14;
	public static final int PLAYER_AIVALUE_DISTANCE = 16 * 2;
	
	public AISlime(SlimeEntity entity) {
		super(entity);
	}
	
	protected int xx, yy = 0;
	protected boolean change;

	@Override
	public void update() {
		World world = getWorld();
		PlayerEntity player = world.player;
		SlimeEntity entity = getEntity();
		
		if(player != null && change){
			change = false;
			xx = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			yy = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			
			float sqrDist = getEntity().sqrDistanceTo(player);
			if(sqrDist <= PLAYER_CHECK_DISTANCE * PLAYER_CHECK_DISTANCE){
				
					xx += getHeatmapOffsetX() * 2;
					yy += getHeatmapOffsetY() * 2;
					
					if(sqrDist > PLAYER_AIVALUE_DISTANCE * PLAYER_AIVALUE_DISTANCE){
						xx += world.player.x - entity.x;
						yy += world.player.y - entity.y;
					}
			}else{
				if(entity.world.time > 60000){
					xx += world.w * 8 - entity.x;
					yy += world.h * 8 - entity.y;
				}
			}
		}
		
		entity.move((int)Math.signum(xx), (int)Math.signum(yy));
	}

	public void forceChange(){
		change = true;
	}
}
