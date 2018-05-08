package net.quantium.harvester.tile;


import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public abstract class Tile {
	public static class Registry {
		private static Tile[] tileRegistry = new Tile[256]; //tile.id - byte
		private static int cursor = 0; 
		private static byte register(Tile tile){
			byte id = (byte) cursor++;
			tileRegistry[id] = tile;
			return id;
		}
		
		public static Tile get(byte id){
			return tileRegistry[id];
		}
		
	}
	
	private byte id;
	protected Tile(){
		this.id = Registry.register(this);
	}
	
	public abstract void render(Renderer render, World w, int x, int y);
	public abstract void randomTick(World w, int x, int y);
	public abstract boolean isPassable(World w, int x, int y, Entity e);
	public abstract void onInteract(World w, int x, int y, Entity e, InteractionMode mode);
	
	public byte getId(){
		return id;
	}
	
	public abstract boolean isConnectable(World w, int x, int y, int xx, int yy);
	
	public static boolean canConnect(World w, int x, int y, int xx, int yy){
		return w.getTile(x, y).isConnectable(w, x, y, xx, yy);
	}

	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type){
		
	}
	
	public void renderShadows(Renderer render, World w, int x, int y){
		
	}
	
}
