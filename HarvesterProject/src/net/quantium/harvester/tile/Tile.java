package net.quantium.harvester.tile;


import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public abstract class Tile {
	public static class Registry {
		private static Tile[] tileRegistry = new Tile[256]; //tile.id - byte
		private static int cursor = 0; 
		public static byte register(Tile tile){
			tile.id = (byte) cursor++;
			tileRegistry[cursor - 1] = tile;
			return tile.id;
		}
		
		public static Tile get(byte id){
			return tileRegistry[id];
		}
		
	}
	
	private byte id;
	
	public abstract void render(Renderer render, World w, int x, int y);
	
	public abstract void randomTick(World w, int x, int y);
	
	public abstract boolean isPassable(World w, int x, int y, Entity e);
	
	public abstract void onInteract(World w, int x, int y, Entity e, InteractionMode mode);
	
	public byte getId(){
		return id;
	}
	
	public abstract boolean isConnectable(World w, int x, int y, int xx, int yy);
	
	public static boolean canConnect(World w, int x, int y, int xx, int yy){
		return Tile.Registry.get(w.getTile(x, y)).isConnectable(w, x, y, xx, yy);
	}

	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type){
		
	}
	
	public void renderShadows(Renderer render, World w, int x, int y){
		
	}
	
}
