package net.quantium.harvester.entity;

import net.quantium.harvester.entity.ai.AIBehavior;
import net.quantium.harvester.entity.ai.AISlime;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.item.Item;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ToolItem;
import net.quantium.harvester.item.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.tile.Tile;
import net.quantium.harvester.world.PassingInfo;
import net.quantium.harvester.world.World;

public class SlimeEntity extends MobEntity implements AIEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient AISlime ai;
	protected int jump;

	protected int frame = 1;
	protected int type = 0;
	
	public static final int[] damage = {1, 1, 2, 2, 2};
	public static final int[] yOffset = {0, 2, 1};
	@Override
	public AIBehavior<SlimeEntity> behavior() {
		return ai;
	}

	public SlimeEntity(int type){
		this.type = type;
		health = maxHealth = 8 + damage[type] * 2;
	}

	
	@Override
	public void init() {
		ai = new AISlime();
		hitbox = new Hitbox(8, 4, -12, -6);
	}

	@Override
	public void render(Renderer render) {
		render.get().draw(x - 16, y - yOffset[frame] - 16, frame * 2, 33 + type * 2, 2, 2, "sheet0", 0);
		if(invincibleTime >= 20){} //todo
	}

	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity){
			((LivingEntity) ent).hit(damage[type]);
		}
	}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		if(active != null && ((Item.Registry.get(active.getItem()) instanceof ToolItem) && ((ToolItem) Item.Registry.get(active.getItem())).getToolType() == ToolType.DAGGER))
			hit(((ToolItem) Item.Registry.get(active.getItem())).level * 2 + 2);
		else
			hit(active != null ? Item.Registry.get(active.getItem()).getPower() : 1);
		return true;
	}
	

	public void move(int xx, int yy){
		PassingInfo infx = world.tryPass(this, xx, 0);
		PassingInfo infy = world.tryPass(this, 0, yy);
		if(infx.isPassed()){
			x += xx;
			Tile.Registry.get(infx.getSteppedOn()).onInteract(world, infx.getTileX(), infx.getTileY(), this, InteractionMode.STEP);
			
		}else
			for(Entity e : infx.getBumped())
				e.bump(this);
		if(infy.isPassed()){
			y += yy;
			Tile.Registry.get(infy.getSteppedOn()).onInteract(world, infy.getTileX(), infy.getTileY(), this, InteractionMode.STEP);
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
			ai.update(this);
		}
		jump++;
	}

	@Override
	public void onDied() {
		world.player.slimesKilled++;
		if(world.player.slimesKilled >= 100){
			SlimeBossEntity e = new SlimeBossEntity();
			e.x = x;
			e.y = y;
			world.addEntity(e);
			world.player.slimesKilled = 0;
		}
	}
}
