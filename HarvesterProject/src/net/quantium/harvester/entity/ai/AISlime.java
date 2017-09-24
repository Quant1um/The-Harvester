package net.quantium.harvester.entity.ai;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.AIEntity;
import net.quantium.harvester.entity.SlimeEntity;

public class AISlime implements AIBehavior<SlimeEntity> {

	public static final int PLAYER_CHECK_DISTANCE = 16 * 14;
	public static final int PLAYER_AIVALUE_DISTANCE = 16 * 2;
	
	protected int xx, yy = 0;

	protected boolean change;

	@Override
	public void update(SlimeEntity entity) {
		if(entity.world.player != null && change){
			change = false;
			xx = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			yy = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			
			if((entity.world.player.x - entity.x) * (entity.world.player.x - entity.x) + (entity.world.player.y - entity.y) * (entity.world.player.y - entity.y) <= PLAYER_CHECK_DISTANCE * PLAYER_CHECK_DISTANCE){
				
					xx += AIEntity.getTargettedOffsetX(entity.world, entity.x >> 4, entity.y >> 4);
					yy += AIEntity.getTargettedOffsetY(entity.world, entity.x >> 4, entity.y >> 4);
					
					if((entity.world.player.x - entity.x) * (entity.world.player.x - entity.x) + (entity.world.player.y - entity.y) * (entity.world.player.y - entity.y) > PLAYER_AIVALUE_DISTANCE * PLAYER_AIVALUE_DISTANCE){
						xx += entity.world.player.x - entity.x;
						yy += entity.world.player.y - entity.y;
					}
				
			}else{
				if(entity.world.time > 60000){
					xx += entity.world.w * 8 - entity.x;
					yy += entity.world.h * 8 - entity.y;
				}
			}
			
		}
		entity.move((int)Math.signum(xx), (int)Math.signum(yy));
	}

	public void forceChange(){
		change = true;
	}
}
