package net.quantium.harvester.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.ISpectator;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.tile.Tiles;
import net.quantium.harvester.utilities.IOContainer;
import net.quantium.harvester.world.World;

public class Session {
	private PlayerEntity player;
	private World world;
	private final String name;
	private final int size;
	private final int slot;
	
	private static final String[] names = new String[7];
	
	private static final IOContainer[] ios = new IOContainer[7];
	
	@Override
	public String toString(){
		return "Session{" + slot + ", Size=" + size + ", Name=" + name + "}";
	}
	
	public Session(int slot, String name, int size, boolean createWorld){
		this.slot = slot;
		this.name = name;
		this.size = size;
		if(createWorld){
			this.player = new PlayerEntity();
			this.world = new World(size, size);
			boolean found = false;
			Random r = new Random(world.seed);
			while(!found){
				int x = r.nextInt(world.w);
				int y = r.nextInt(world.h);
				if(world.getTile(x, y) == Tiles.grass){
					this.player.x = x * 16;
					this.player.y = y * 16;
					found = true;
				}
			}
			this.world.addEntity(this.player);
		}
		names[slot] = name;
	}
	
	public void update(){
		world.update();
	}
	
	public void render(Renderer render){
		render.get().push();
		render.get().transform().translate(player.getXOffset(), player.getYOffset());
		//if(Main.getInstance().getDebugMode() != 0) render.get().transform().tint(Color.lerp((short)338, (short)999, (world.time < 43200 ? world.time : 43200 - (world.time - 43200)) / 4320));
		for(int i = 0; i < 3; i++)
			world.renderLayer(render, i);
		render.get().pop();

	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public int getSlot() {
		return slot;
	}
	
	public World getWorld() {
		return world;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}
	
	public ISpectator getSpectator(){
		return player;
	}
	
	@SuppressWarnings("unchecked")
	public static Session load(int slot){
		if(slot < 0 && slot >= 7) return null;
		try{
			IOContainer io;
			if(ios[slot] == null){
				io = new IOContainer("saves" + File.separator + "save" + (slot + 1) + ".dat");
				io.load();
				ios[slot] = io;
			}else
				io = ios[slot];
			
			if(!io.get().containsKey("version")) return null;
			String name = (String) io.get().get("name");
			int size = (int) io.get().get("size");
			Session session = new Session(slot, name, size, false);
			session.world = (World) io.get().get("world");
			session.player = (PlayerEntity) io.get().get("player");
			session.world.player = session.player;
			
			session.world.aiTargetMap = new byte[session.world.w * session.world.h];
			session.world.entityTileCache = new List[session.world.w * session.world.h];
			for(int i = 0; i < session.world.entityTileCache.length; i++)
				session.world.entityTileCache[i] = new ArrayList<Entity>();
			for(Entity e : session.world.entities){
				e.init();
				session.world.cacheEntity(e.x >> World.ENTITY_TILE_COORDSHIFT, e.y >> World.ENTITY_TILE_COORDSHIFT, e);
			}
			session.world.player.init();
			return session;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void save(){
		save(false);
	}
	
	public void save(boolean sync){
		try{
			IOContainer io = new IOContainer("saves" + File.separator + "save" + (slot + 1) + ".dat");
			io.get().put("version", Main.VERSION); //nope
			io.get().put("name", name);
			io.get().put("size", size);
			io.get().put("world", world);
			io.get().put("player", player);
			if(sync) io.saveSynchronized();
			else io.save();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getName(int slot){
		if(slot < 0 && slot >= 7) return null;
		return names[slot];
	}
	
	public static void updateNames(){
		for(int i = 0; i < 7; i++){
			IOContainer io = new IOContainer("saves" + File.separator + "save" + (i + 1) + ".dat");
			if(new File(IOContainer.DATA_FOLDER + File.separator + "saves" + File.separator + "save" + (i + 1) + ".dat").exists()){
				io.load();
				if(io.get().containsKey("version")){
					names[i] = (String) io.get().get("name");
				}else{
					names[i] = null;
				}
			}
		}
	}
	
	public static void delete(int slot){
		File e = new File(IOContainer.DATA_FOLDER + File.separator + "saves" + File.separator + "save" + (slot + 1) + ".dat");
		try {
			Files.deleteIfExists(Paths.get(e.toURI()));
			names[slot] = null;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
