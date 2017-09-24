package net.quantium.harvester.entity;

import java.util.List;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.ExternalUtils;
import net.quantium.harvester.Main;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.item.Item;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.DeathScreen;
import net.quantium.harvester.screen.InventoryScreen;
import net.quantium.harvester.screen.PauseScreen;
import net.quantium.harvester.screen.Screen;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.tile.Tile;
import net.quantium.harvester.tile.Tiles;
import net.quantium.harvester.world.PassingInfo;
import net.quantium.harvester.world.World;

public class PlayerEntity extends AliveEntity implements ISpectator{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;

	public static final int INTERACTION_DISTANCE = 2;
	
	private int intTime = 0;
	private float intCursor = 0;
	private int walkFrame = 0;
	private int direction = 0;
	private int walking = 0;
	private int moveX, moveY;
	private int interactionTime;
	public Inventory inventory = new Inventory(16);

	private boolean requestAnimation;
	
	private int requestedHitLevel;
	private ToolType requestedHitTool;
	
	private int spawnX = -1, spawnY = -1;
	
	public boolean died;
	
	int slimesKilled = 0;
	
	public PlayerEntity(){
		maxHealth = health = 20;
	}
	
	@Override
	public void init() {
		hitbox = new Hitbox(10, 6, -2, -6);
		if(died) openScreen(new DeathScreen(this)); //after game loading died value can be false
	}

	@Override
	public void update() {
		super.update();
		if((Main.getInstance().getCounter() & 3) == 0) world.addTargetValue(x >> 4, y >> 4, 0);
		
		if(intTime > 0){
			intTime--;
			if(intTime <= 0){
				requestedHitLevel = -1;
			}
		}
		if(interactionTime > 0) interactionTime--;
		if(walking > 0) walking--;
		
		int shouldMoveX = 0;
		int shouldMoveY = 0;
		if(getScreenService().getInput().w.down) shouldMoveY--;
		if(getScreenService().getInput().s.down) shouldMoveY++;
		if(getScreenService().getInput().a.down) shouldMoveX--;
		if(getScreenService().getInput().d.down) shouldMoveX++;
		move(shouldMoveX, shouldMoveY);
		if(shouldMoveX != 0 || shouldMoveY != 0) requestAnimation = true;
		
		if(requestAnimation){
			walkFrame++;
			walkFrame = walkFrame % 40;
			walking = 5;
			requestAnimation = false;
		}
		
		if(intTime > 0){
			if(intCursor < 1) intCursor += 0.03f;
		}else{
			if(intCursor > 0) intCursor -= 0.045f;
		}
	}

	@Override
	public void render(Renderer render) {
		int o0 = direction == 1 ? 0 : direction == 0 || direction == 2 ? 8 : 16;
		boolean isInWater = world.getTile(x >> 4, y >> 4) == Tiles.water;
		int offset = 0;
		if(isInWater){
			offset = -(world.getHeight(x >> 4, y >> 4) + 10) / 2;
			if(offset > 10) offset = 10;
			render.get().draw(x - hitbox.getWidth() / 2 - 1, y - hitbox.getHeight(), 12 + (Main.getInstance().getCounter() / 30 % 2 == 0 ? 2 : 0), 19, 2, 1, "sheet0", 0);
			render.get().clipY1 = y - hitbox.getHeight() + 5 - getYOffset();	
		}
		render.get().drawWorldShadow(x - hitbox.getWidth() / 2 - offset / 3, y - hitbox.getHeight() - 18 + offset, o0 + (walking > 0 ? walkFrame / 10 * 2 : 0), 16, 2, 3, "sheet0", direction == 0 ? 1 : 0);
		render.get().draw(x - hitbox.getWidth() / 2, y - hitbox.getHeight() - 18 + offset, o0 + (walking > 0 ? walkFrame / 10 * 2 : 0), 16, 2, 3, "sheet0", direction == 0 ? 1 : 0);
		if(invicibleTime >= 20){
			render.get().drawDamageOverlay(x - hitbox.getWidth() / 2, y - hitbox.getHeight() - 18 + offset, o0 + (walking > 0 ? walkFrame / 10 * 2 : 0), 16, 2, 3, "sheet0", direction == 0 ? 1 : 0);
		}
		render.get().resetClip();

		if(intCursor > 0) render.get().drawCircle(x + 2, y - hitbox.getHeight() / 2 - 9, (int)(INTERACTION_DISTANCE * World.ENTITY_TILE_COORDSCALE * ExternalUtils.easeInElastic(intCursor)), 999);
	}

	@Override
	public void bump(Entity ent) {
		ent.bump(this);
	}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	@Override
	public int getXOffset() {
		int x = this.x - Main.getInstance().getRenderWidth() / 2;
		if (x < 0) x = 0;
		if (x > this.world.w * 16 - Main.getInstance().getRenderWidth()) x = this.world.h * 16 - Main.getInstance().getRenderWidth();
		return x;
	}

	@Override
	public int getYOffset() {
		int y = this.y - Main.getInstance().getRenderHeight() / 2;
		if (y < 0) y = 0;
		if (y > this.world.h * 16 - Main.getInstance().getRenderHeight()) y = this.world.h * 16 - Main.getInstance().getRenderHeight();
		return y;
	}

	@Override
	public void onMouseClick(int x, int y, int button) {
		int xx = x + this.getXOffset();
		int yy = y + this.getYOffset();
		intTime = 5;
		if((xx - this.x - 2) * (xx - this.x - 2) + (yy - (this.y - hitbox.getHeight() / 2 - 9)) * (yy - (this.y - hitbox.getHeight() / 2 - 9)) <= INTERACTION_DISTANCE * INTERACTION_DISTANCE * World.ENTITY_TILE_COORDSCALE * World.ENTITY_TILE_COORDSCALE){
			if(interactionTime > 0) return;
			interactionTime = 20;
			
			List<Entity> ents = world.interact(this, xx, yy);
			for(Entity e : ents)
				if(e.onInteract(world, this, button == 1 ? InteractionMode.LEFT : button == 3 ? InteractionMode.RIGHT : InteractionMode.OTHER, inventory.get(0))) return;
			if(inventory.get(0) == null || Item.Registry.get(inventory.get(0).getItem()) == null || !Item.Registry.get(inventory.get(0).getItem()).
					interact(world, xx, 
							yy, 
							this, 
							button == 1 ? InteractionMode.LEFT : 
							button == 3 ? InteractionMode.RIGHT : 
								          InteractionMode.OTHER, 
						    inventory.get(0))){
				Tile.Registry.get(world.getTile(xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT)).onInteract(world, xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT, this, button == 1 ? InteractionMode.LEFT : button == 3 ? InteractionMode.RIGHT : InteractionMode.OTHER);
				Tile.Registry.get(world.getTile(xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT)).hit(world, xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT, this, 1, ToolType.NONE);
			}else{
				if(requestedHitLevel > 0 && intTime > 0)
					Tile.Registry.get(world.getTile(xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT)).hit(world, xx >> World.ENTITY_TILE_COORDSHIFT, yy >> World.ENTITY_TILE_COORDSHIFT, this, requestedHitLevel, requestedHitTool);
			}
		}
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		
		if(key.code == KeyEvent.VK_ESCAPE && first){
			getScreenService().setScreen(new PauseScreen());
		}
		
		if(key.code == KeyEvent.VK_E && first){
			getScreenService().setScreen(new InventoryScreen(inventory, null, null));
		}
	}

	@Override
	public void onMouseWheel(int ticks) {
		
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot item) {
		return false;
	}

	public void refetchPosition() {	
		x += moveX;
		y += moveY;
		if(moveX > 0) direction = 0;
		else if(moveX < 0) direction = 2;
		else if(moveY > 0) direction = 1;
		else if(moveY < 0) direction = 3;
		
		moveX = moveY = 0;
	}	
	
	@Override
	public void move(int xx, int yy){
		if(world.getTile(x >> 4, y >> 4) == Tiles.water && Main.getInstance().getCounter() % 2 != 0) return;
		PassingInfo infx = world.tryPass(this, xx, 0);
		PassingInfo infy = world.tryPass(this, 0, yy);
		if(infx.isPassed()){
			moveX += xx;
			Tile.Registry.get(infx.getSteppedOn()).onInteract(world, infx.getTileX(), infx.getTileY(), this, InteractionMode.STEP);
			
		}else
			for(int i = 0; i < infx.getBumped().size(); i++)
				this.bump(infx.getBumped().get(i));
		if(infy.isPassed()){
			moveY += yy;
			Tile.Registry.get(infy.getSteppedOn()).onInteract(world, infy.getTileX(), infy.getTileY(), this, InteractionMode.STEP);
			
		}else
			for(int i = 0; i < infy.getBumped().size(); i++)
				this.bump(infy.getBumped().get(i));
	}

	public void hitSelected(int level, ToolType type) {
		this.requestedHitLevel = level * 3 + 3;
		this.requestedHitTool = type;
	}

	public void openScreen(Screen screen) {
		getScreenService().setScreen(screen);
	}

	private ScreenService getScreenService() {
		return Main.getInstance().getScreenService();
	}

	@Override
	public void died() {
		openScreen(new DeathScreen(this));
		for(int i = 0; i < inventory.size(); i++)
			if(inventory.get(i) != null){
				world.throwItem(x, y, inventory.get(i));
				inventory.set(i, null);
			}
	}
	
	@Override
	public void remove(){
		died = true;
	}
	
	public void setSpawn(int x, int y){
		this.spawnX = x;
		this.spawnY = y;
	}
	
	public void resetSpawn(){
		this.spawnX = -1;
		this.spawnY = -1;
	}
	
	public void respawn(){
		if(this.spawnX >= 0 && this.spawnY >= 0 && this.spawnX < world.w && this.spawnY < world.h){
			if(!world.isTilePassableBy(this, spawnX, spawnY)){
				resetSpawn();
				boolean found = false;
				Random r = new Random(world.seed);
				while(!found){
					int x = r.nextInt(world.w);
					int y = r.nextInt(world.h);
					if(world.getTile(x, y) == Tiles.grass){
						this.x = x * 16;
						this.y = y * 16;
						found = true;
					}
				}
			}else{
				this.x = this.spawnX * 16;
				this.y = this.spawnY * 16;
			}
		}
		boolean found = false;
		Random r = new Random(world.seed);
		while(!found){
			int x = r.nextInt(world.w);
			int y = r.nextInt(world.h);
			if(world.getTile(x, y) == Tiles.grass){
				this.x = x * 16;
				this.y = y * 16;
				found = true;
			}
		}
		this.health = this.maxHealth;
		this.died = false;
	}
	
	@Override
	public String toString(){
		return "PlayerEntity{InteractionTime=" + intTime + ", InteractionCursor=" + intCursor + ", WalkFrame=" + walkFrame + 
				", Direction=" + direction + ", Walking=" + walking + ", MoveX=" + moveX + ", MoveY=" + moveY + 
				", InteractionCooldown=" + interactionTime + ", RequestAnimation=" + requestAnimation + ", Inventory=" + inventory + 
				", RequestedHitLevel=" + requestedHitLevel + ", RequestedHitTool=" + requestedHitTool + ", SpawnX=" + spawnX + ", SpawnY=" + spawnY + 
				", Health=" + health + ", MaxHealth=" + maxHealth + ", InvicibleTime=" + invicibleTime + ", SlimesKilled=" + slimesKilled + 
				", X=" + x + ", Y= " + y + "}";
	}
}
