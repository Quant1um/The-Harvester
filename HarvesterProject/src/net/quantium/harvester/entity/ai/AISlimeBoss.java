package net.quantium.harvester.entity.ai;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.SlimeBossEntity;
import net.quantium.harvester.entity.SlimeEntity;

public class AISlimeBoss extends AISlime {
	@Override
	public void update(SlimeEntity entity) {
		if(entity.world.player != null && change){
			change = false;
			xx = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			yy = Main.GLOBAL_RANDOM.nextInt(3) - 1;
			
			if((entity.world.player.x - entity.x) * (entity.world.player.x - entity.x) + (entity.world.player.y - entity.y) * (entity.world.player.y - entity.y) <= 3200 * 3200){
				xx += entity.world.player.x - entity.x;
				yy += entity.world.player.y - entity.y;
				if((entity.world.player.x - entity.x) * (entity.world.player.x - entity.x) + (entity.world.player.y - entity.y) * (entity.world.player.y - entity.y) <= 100 * 100){
					((SlimeBossEntity)entity).readyToEject = true;
				}
			}
			
		}
		entity.move((int)Math.signum(xx), (int)Math.signum(yy));
	}
}
