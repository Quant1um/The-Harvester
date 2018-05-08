package net.quantium.harvester.entity;

import net.quantium.harvester.entity.ai.AIBehavior;
import net.quantium.harvester.entity.ai.AISlime;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.PassingInfo;

public class SlimeEntity extends MobEntity implements AIEntity<SlimeEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient AISlime ai;
	protected int jump;

	protected int frame = 1;
	public final SlimeType type;
	
	public static final int[] yOffset = {0, 2, 1};
	
	@Override
	public AIBehavior<SlimeEntity> behavior() {
		return ai;
	}

	public SlimeEntity(SlimeType type){
		this.type = type;
		this.maxHealth = this.health = type.health;
	}
	
	@Override
	public void init() {
		ai = new AISlime(this);
		hitbox = new Hitbox(8, 4, -12, -6);
	}

	@Override
	public void render(Renderer render) {
		render.get().draw(x - 16, y - yOffset[frame] - 16, frame * 2, 33 + type.spriteOffset * 2, 2, 2, "sheet0", 0);
		if(invincibleTime >= 20)
			render.get().drawTinted(x - 16, y - yOffset[frame] - 16, frame * 2, 33 + type.spriteOffset * 2, 2, 2, DAMAGE_TINT_COLOR, "sheet0", 0);
		//render.get().drawLine(x - 8, y - 8, x - 8 + ai.getHeatmapOffsetX() * 16, y - 8 + ai.getHeatmapOffsetY() * 16, 990);
	}

	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity)
			((LivingEntity) ent).hit(type.damage);
	}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	public void move(int xx, int yy){
		PassingInfo infx = world.tryPass(this, xx, 0);
		PassingInfo infy = world.tryPass(this, 0, yy);
		if(infx.isPassed()){
			x += xx;
			infx.getSteppedOn().onInteract(world, infx.getTileX(), infx.getTileY(), this, InteractionMode.STEP);
			
		}else
			for(Entity e : infx.getBumped())
				e.bump(this);
		if(infy.isPassed()){
			y += yy;
			infy.getSteppedOn().onInteract(world, infy.getTileX(), infy.getTileY(), this, InteractionMode.STEP);
		}else
			for(Entity e : infy.getBumped())
				e.bump(this);
	}

	@Override
	public void update() {
		super.update();
		if(jump <= 0){
			frame = 0;
		}else if(jump < 10){
			frame = 2;
		}else if(jump < 25){
			frame = 0;
			ai.forceChange();
		}else if(jump < 40){
			frame = 1;
		}else{
			jump = 0;
			frame = 2;
		}
		if(jump > 15 && jump < 35){
			ai.update();
		}
		jump++;
	}

	@Override
	public void onDied() {
		super.onDied();
		world.player.slimesKilled++;
		if(world.player.slimesKilled >= 100){
			SlimeBossEntity e = new SlimeBossEntity();
			e.x = x;
			e.y = y;
			world.addEntity(e);
			world.player.slimesKilled = 0;
		}
	}
	
	public enum SlimeType{
		JELLY(1, 8, 0),
		GRASS(1, 14, 1),
		WATER(2, 10, 2),
		DARKIE(3, 16, 3);
		
		public final int damage, health, spriteOffset;
		
		private SlimeType(int damage, int health, int offset){
			this.damage = damage;
			this.health = health;
			this.spriteOffset = offset;
		}
	}
}
