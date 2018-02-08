package net.quantium.harvester.world;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.MobEntity;
import net.quantium.harvester.entity.CollidableTileEntity;
import net.quantium.harvester.entity.DamageParticle;
import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.ItemEntity;
import net.quantium.harvester.entity.ParticleEntity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.SlimeEntity;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;
import net.quantium.harvester.tile.Tile;

public class World implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int METADATA_LAYERS = 2;
	
	public static final int RENDERLAYER_TILE = 0;
	public static final int RENDERLAYER_ENTITY = 1;
	public static final int RENDERLAYER_PARTICLE = 2;
	
	public static final int TIME_PER_TICK = 2;
	
	public static final int ENTITY_TILE_COORDSCALE = 16;
	public static final int ENTITY_TILE_COORDSHIFT = 4;
	
	public byte[] map;
	public short[] meta;
	
	public byte[] temperature;
	public byte[] moisture;
	public byte[] height;
	
	public transient byte[] aiTargetMap;
	
	public transient List<Entity>[] entityTileCache;
	
	public int time = 0; //max = 86400
	
	public List<Entity> entities = new ArrayList<Entity>();
	private List<ParticleEntity> particles = new ArrayList<ParticleEntity>();
	
	public int w, h;
	public transient PlayerEntity player;
	public int seed;
	
	private transient Comparator<Entity> compatator = new Comparator<Entity>() {
		  public int compare(Entity a, Entity b) {
			return a.compareTo(b);
		  }
	};

	
	@SuppressWarnings("unchecked")
	public World(int w, int h){
		this.w = w;
		this.seed = Main.GLOBAL_RANDOM.nextInt(Integer.MAX_VALUE);
		this.h = h;
		this.map = new byte[w * h];
		this.aiTargetMap = new byte[w * h];
		this.meta = new short[w * h * METADATA_LAYERS];
		this.temperature = new byte[w * h];
		this.moisture = new byte[w * h];
		this.height = new byte[w * h];
		this.entityTileCache = new List[w * h];
		for(int i = 0; i < entityTileCache.length; i++)
			this.entityTileCache[i] = new ArrayList<Entity>();
		WorldGenerator.generate(this, this.seed);
	}
	
	public void update(){
		time += TIME_PER_TICK;
		if(time >= 86400) time = 0;
		for(int i = 0; i < w * h / 150; i++){
			int j = Main.GLOBAL_RANDOM.nextInt(w * h);
			Tile.Registry.get(map[j]).
				randomTick(this, j % w, j / w);
		}
		
		if((Main.getInstance().getCounter() & 7) == 0)
			for(int i = 0; i < w * h; i++)
				if(aiTargetMap[i] > 0) aiTargetMap[i] -= 5;
			
		
		if(Main.GLOBAL_RANDOM.nextInt(300) == 0) updateEntitySpawningCycle();
		
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			if((e.x - player.x) * (e.x - player.x) + (e.y - player.y) * (e.y - player.y) < 100 * 16 * 100 * 16){
				int preX = e.x;
				int preY = e.y;
				e.update();
				if(e.removed) this.removeEntity(e);
				if(preX >> ENTITY_TILE_COORDSHIFT != e.x >> ENTITY_TILE_COORDSHIFT || preY >> ENTITY_TILE_COORDSHIFT != e.y >> ENTITY_TILE_COORDSHIFT)
					recacheEntity(preX >> ENTITY_TILE_COORDSHIFT, preY >> ENTITY_TILE_COORDSHIFT, e.x >> ENTITY_TILE_COORDSHIFT, e.y >> ENTITY_TILE_COORDSHIFT, e);
			}
		}
		
		if(!player.died){
			int preX = player.x;
			int preY = player.y;
			player.update();
			
			player.refetchPosition();
			if((preX >> ENTITY_TILE_COORDSHIFT) != (player.x >> ENTITY_TILE_COORDSHIFT) || (preY >> ENTITY_TILE_COORDSHIFT) != (player.y >> ENTITY_TILE_COORDSHIFT)){
				recacheEntity((preX >> ENTITY_TILE_COORDSHIFT), (preY >> ENTITY_TILE_COORDSHIFT), (player.x >> ENTITY_TILE_COORDSHIFT), (player.y >> ENTITY_TILE_COORDSHIFT), player);
			}
		}
		for(int i = 0; i < particles.size(); i++){
			Entity e = particles.get(i);
			e.update();
			if(e.removed) removeEntity(e);
		}
			
	}
	
	public void renderLayer(Renderer render, int layer){
		
		final int OFFSCEEEN = 2;
		switch(layer){
			case RENDERLAYER_TILE: {
				for(int i = (int)(render.get().offsetX / ENTITY_TILE_COORDSCALE) - OFFSCEEEN; i <= (int)(Main.getInstance().getRenderWidth() + render.get().offsetX) / ENTITY_TILE_COORDSCALE + OFFSCEEEN; i++)
					for(int j = (int)(render.get().offsetY / ENTITY_TILE_COORDSCALE) - OFFSCEEEN; j <= (int)(Main.getInstance().getRenderHeight() + render.get().offsetY) / ENTITY_TILE_COORDSCALE + OFFSCEEEN; j++){
						Tile.Registry.get(getTile(i, j)).render(render, this, i, j);
						if(Main.getInstance().getDebugMode() == 2) render.get().drawText(i * 16, j * 16, FontSize.SMALL, String.valueOf(getTargetValue(i, j)), 833, TextAlign.LEFT);
						if(Main.getInstance().getDebugMode() == 3) render.get().drawText(i * 16, j * 16, FontSize.SMALL, getMetadata(i, j, 0) + "; " + getMetadata(i, j, 1), 338, TextAlign.LEFT);
					}
				return;
			}
			
			case RENDERLAYER_ENTITY: {
				List<Entity> ents = new ArrayList<Entity>();//getEntitiesIn((int)(render.get().offsetX / ENTITY_TILE_COORDSCALE) - OFFSCEEEN, (int)(render.get().offsetY / ENTITY_TILE_COORDSCALE) - OFFSCEEEN , (Main.getInstance().getRenderWidth() + render.get().offsetX) / ENTITY_TILE_COORDSCALE + OFFSCEEEN + 1, (Main.getInstance().getRenderHeight() + render.get().offsetY) / ENTITY_TILE_COORDSCALE + OFFSCEEEN + 1);
				
				if(!player.died) ents.add(player);
				
				for(int i = 0; i < entities.size(); i++){
					Entity e = entities.get(i);
					if(e.x >= render.get().offsetX - OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.y >= render.get().offsetY - OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.x <= Main.getInstance().getRenderWidth() + render.get().offsetX + OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.y <= Main.getInstance().getRenderHeight() + render.get().offsetY + OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE)
					ents.add(e);
				}
				
				ents.sort(compatator);
				for(Entity e : ents){
					e.render(render);
					if(Main.getInstance().getDebugMode() == 1){
						Hitbox hbox = e.hitbox;
						render.get().drawRect(e.x + hbox.getOffsetX(), e.y + hbox.getOffsetY(), hbox.getWidth(), hbox.getHeight(), 911);
						render.get().put(e.x, e.y, 119);
					}
				}
				
				return;
			}
			
			case RENDERLAYER_PARTICLE: {
				
				List<Entity> ents = new ArrayList<Entity>();//getEntitiesIn((int)(render.get().offsetX / ENTITY_TILE_COORDSCALE) - OFFSCEEEN, (int)(render.get().offsetY / ENTITY_TILE_COORDSCALE) - OFFSCEEEN , (Main.getInstance().getRenderWidth() + render.get().offsetX) / ENTITY_TILE_COORDSCALE + OFFSCEEEN + 1, (Main.getInstance().getRenderHeight() + render.get().offsetY) / ENTITY_TILE_COORDSCALE + OFFSCEEEN + 1);				
				for(ParticleEntity e : particles){
					
					if(e.x >= render.get().offsetX - OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.y >= render.get().offsetY - OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.x <= Main.getInstance().getRenderWidth() + render.get().offsetX + OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE &&
					   e.y <= Main.getInstance().getRenderHeight() + render.get().offsetY + OFFSCEEEN * 2 * ENTITY_TILE_COORDSCALE){
					ents.add(e);
					}
				}
				ents.sort(compatator);
				for(Entity e : ents){
					e.render(render);
				}
				return;
			}
			default: return;
		}
	}
	
	public void addEntity(Entity e){
		e.world = this;
		e.init();
		if(e instanceof ParticleEntity)
			particles.add((ParticleEntity) e);
		else if(e instanceof PlayerEntity){
			player = (PlayerEntity) e;
			cacheEntity(e.x >> ENTITY_TILE_COORDSHIFT, e.y >> ENTITY_TILE_COORDSHIFT, e);
		}else{
			cacheEntity(e.x >> ENTITY_TILE_COORDSHIFT, e.y >> ENTITY_TILE_COORDSHIFT, e);
			entities.add(e);
		}
	}
	
	public boolean removeEntity(Entity e){
		if(e instanceof ParticleEntity)
			return particles.remove((ParticleEntity) e);
		else if(e instanceof PlayerEntity){
			uncacheEntity(e.x >> ENTITY_TILE_COORDSHIFT, e.y >> ENTITY_TILE_COORDSHIFT, e);
			player = null;
			return true;
		}else{
			uncacheEntity(e.x >> ENTITY_TILE_COORDSHIFT, e.y >> ENTITY_TILE_COORDSHIFT, e);
			return entities.remove(e);
		}
	}
	
	public void cacheEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		this.entityTileCache[x + y * w].add(e);
	}

	public void uncacheEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		this.entityTileCache[x + y * w].remove(e);
	}
	
	public void recacheEntity(int lx, int ly, int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		this.entityTileCache[x + y * w].add(e);
		if (lx < 0 || ly < 0 || lx >= w || ly >= h) return;
		this.entityTileCache[lx + ly * w].remove(e);
	}
	
	public byte getTile(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return map[x + y * w];
		return 0;
	}
	
	public void setTile(int x, int y, byte b){
		if(x >= 0 && y >= 0 && x < w && y < h)
			map[x + y * w] = b;
	}
	
	public byte getTargetValue(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return aiTargetMap[x + y * w];
		return 0;
	}
	
	public void addTargetValue(int x, int y, int depth){
		if(depth >= 3) return;
		if(x >= 0 && y >= 0 && x < w && y < h){
				aiTargetMap[x + y * w] += (3 - depth) * 3 - depth;
			if(aiTargetMap[x + y * w] >= 100) aiTargetMap[x + y * w] = 100;
			for(int i = -1; i < 2; i++)
				for(int j = -1; j < 2; j++)
					addTargetValue(x + i, y + j, depth + 1);
		}
	}
	
	public byte getTemperature(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return temperature[x + y * w];
		return 0;
	}
	
	public void setTemperature(int x, int y, byte b){
		if(x >= 0 && y >= 0 && x < w && y < h)
			temperature[x + y * w] = b;
	}
	
	public byte getMoisture(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return moisture[x + y * w];
		return 0;
	}
	
	public void setMoisture(int x, int y, byte b){
		if(x >= 0 && y >= 0 && x < w && y < h)
			moisture[x + y * w] = b;
	}
	
	public short getMetadata(int x, int y, int layer){
		if(x >= 0 && y >= 0 && x < w && y < h && layer >= 0 && layer < METADATA_LAYERS)
			return meta[layer * w * h + x + y * w];
		return 0; 
	}
	
	public void setMetadata(int x, int y, int layer, short b){
		if(x >= 0 && y >= 0 && x < w && y < h && layer >= 0 && layer < METADATA_LAYERS)
			meta[layer * w * h + x + y * w] = b;
	}
	
	public byte getHeight(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return height[x + y * w];
		return 0;
	}
	
	public void setHeight(int x, int y, byte b){
		if(x >= 0 && y >= 0 && x < w && y < h)
			height[x + y * w] = b;
	}
	
	public List<Entity> getEntitiesOn(int x, int y){
		if(x >= 0 && y >= 0 && x < w && y < h)
			return entityTileCache[x + y * w];
		return new ArrayList<Entity>();
	}
	
	public boolean canPlaceAloneSettableEntity(int x, int y){
		return getEntitiesIn((x >> 4) - 1, (y >> 4) - 1, 1, 1).isEmpty();
	}
	
	public List<Entity> getEntitiesIn(int x, int y, int w, int h){
		List<Entity> list = new ArrayList<Entity>();
			for(int i = 0; i < w; i++)
				for(int j = 0; j < h; j++){
					list.addAll(getEntitiesOn(x + i, y + j));
				}
		return list;
	}
	
	public static final Hitbox TILE_HITBOX = new Hitbox(16, 16);
	
	public PassingInfo tryPass(Entity e, int xx, int yy){
		boolean canPass = true;
		if(xx != 0 && yy != 0) throw new RuntimeException("trypass works only along one axis(for good collision check)  uwu");
		int ex = xx + e.x;
		int ey = yy + e.y;
		List<Entity> ents = getEntitiesIn((ex >> ENTITY_TILE_COORDSHIFT) - 1, (ey >> ENTITY_TILE_COORDSHIFT) - 1, 3, 3);
		List<Entity> bumped = new ArrayList<Entity>();
		for(int i = -1; i < 2; i++)
			for(int j = -1; j < 2; j++)
				if(!isTilePassableBy(e, (ex >> ENTITY_TILE_COORDSHIFT) + i, (ey >> ENTITY_TILE_COORDSHIFT) + j)){
					ents.add(new CollidableTileEntity((ex >> ENTITY_TILE_COORDSHIFT) + i, (ey >> ENTITY_TILE_COORDSHIFT) + j));
				}
		for(Entity ee : ents){
			if(!ee.isPassable(e)){
				if(ee != e){
					Rectangle intr = new Rectangle();
					if(Hitbox.intersects(ex, ey, e.hitbox, ee.x, ee.y, ee.hitbox, intr)){
						bumped.add(ee);
						if(xx != 0 && intr.width > 1)
							unstuckAlongX(e, ee.x, ee.hitbox, intr);
						else if(yy != 0 && intr.height > 1)
							unstuckAlongY(e, ee.y, ee.hitbox, intr);
						canPass = false;
					}
						
				}
			}
		}
		return new PassingInfo(canPass, bumped, getTile(ex >> ENTITY_TILE_COORDSHIFT, ey >> ENTITY_TILE_COORDSHIFT), ex >> ENTITY_TILE_COORDSHIFT, ey >> ENTITY_TILE_COORDSHIFT);
	}
	
	private void unstuckAlongX(Entity e, int x, Hitbox hitbox, Rectangle intr) {
		int move = intr.x < hitbox.getCenterX(x) ? -1 : 1;
		e.x += move;
	}

	private void unstuckAlongY(Entity e, int y, Hitbox hitbox, Rectangle intr) {
		int move = intr.y < hitbox.getCenterY(y) ? -1 : 1;
		e.y += move;
	}

	private static final Hitbox INTERACTION_HITBOX = new Hitbox(12, 12, -6, -6);
	
	public List<Entity> interact(Entity e, int ex, int ey){
		List<Entity> ents = getEntitiesIn((ex >> ENTITY_TILE_COORDSHIFT) - 1, (ey >> ENTITY_TILE_COORDSHIFT) - 1, 3, 3);
		List<Entity> bumped = new ArrayList<Entity>();
		for(Entity ee : ents){
				if(ee != e){
					if(Hitbox.intersects(ee.x, ee.y, ee.hitbox, ex, ey, INTERACTION_HITBOX, null))
						bumped.add(ee);					
				}
			}
		return bumped;
	}
	
	public boolean isTilePassableBy(Entity e, int x, int y){
		return Tile.Registry.get(getTile(x, y)).isPassable(this, x, y, e);
	}
	
	public static boolean isRectanglesIntersects(int x, int y, int w, int h, int xx, int yy, int ww, int hh, Rectangle output){
		int xmin = Math.max(x, xx);
		int xmax1 = x + w;
		int xmax2 = xx + ww;
		int xmax = Math.min(xmax1, xmax2);
	    if (xmax > xmin) {
	    	int ymin = Math.max(y, yy);
	    	int ymax1 = y + h;
	    	int ymax2 = yy + hh;
	    	int ymax = Math.min(ymax1, ymax2);
	        if (ymax > ymin) {
	        	if(output != null){
	        		output.x = xmin;
	        		output.y = ymin;
	        		output.width = xmax - xmin;
	        		output.height = ymax - ymin;
	        	}
	            return true;
	        }
	    }
	    return false;
	}
	
	public void updateEntitySpawningCycle(){
		if(player == null) return;
		int ents = 1;
		int j = 0;
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i) instanceof MobEntity && Main.GLOBAL_RANDOM.nextInt(3) == 0){
				j++;
				if(j >= 70){
					j = 0;
					if(player == null || entities.get(i).sqrDistanceTo(player) > 16 * 16 * 50 * 50)
						removeEntity(entities.get(i));
				}
			}
		}
		for(int i = 0; i < ents; i++){
			int xx = genEntityPos(player.x, w * 16);
			int yy = genEntityPos(player.y, h * 16);
			Entity ee = makeEntity(player.x >> 4, player.y >> 4);
			int attempts = 0;
			while(!isTilePassableBy(ee, xx >> 4, yy >> 4)){
				xx = genEntityPos(player.x, w * 16);
				yy = genEntityPos(player.y, h * 16);
				if(xx < 0 || yy < 0) return;
				attempts++;
				if(attempts > 10) return;
			}
			ee.x = xx;
			ee.y = yy;
			addEntity(ee);
			
		}
	}

	private Entity makeEntity(int x, int y) {
		int moisture = getMoisture(x, y);
		//int temperature = getTemperature(x, y);
		int height = getHeight(x, y);
		
		int seed = Main.GLOBAL_RANDOM.nextInt(10);
		if(seed < 2 && moisture > 18) return new SlimeEntity(2);
		if(seed < 4 && height > 27) return new SlimeEntity(3);
		if(seed < 6 && height > 3) return new SlimeEntity(1);
		return new SlimeEntity(0);
	}
	
	private int genEntityPos(int ply, int b){
		int g = Main.GLOBAL_RANDOM.nextInt(b);
		int attempts = 0;
		while(Math.abs(ply - g) < 30 * 16){
			g = Main.GLOBAL_RANDOM.nextInt(b);
			attempts++;
			if(attempts > 5) return -1;
		}
		return g;
	}
	
	public void throwItem(int xx, int yy, ItemSlot slot){
		ItemSlot single = slot.copy();
		single.count = 1;
		for(int i = 0; i < slot.count; i++){
			float fx = (float)(Main.GLOBAL_RANDOM.nextGaussian() * 0.8f);
			float fy = (float)(Main.GLOBAL_RANDOM.nextGaussian() * 0.8f);
			ItemEntity ie = new ItemEntity(single, fx, fy);
			ie.x = xx;
			ie.y = yy;
			addEntity(ie);
		}
	}

	public void damageParticle(int xx, int yy, int damage){
		DamageParticle ie = new DamageParticle(damage);
		ie.x = xx;
		ie.y = yy;
		addEntity(ie);
	}
	
	@Override
	public String toString(){
		return "World{Width=" + w + ", Height=" + h + ", Seed=" + seed + "}";
	}
}
