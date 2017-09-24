package net.quantium.harvester.item;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.world.World;

public abstract class Item {
	public static class Registry {
		private static Item[] itemRegistry = new Item[256]; //item.id - byte
		private static int cursor = 0; 
		public static byte register(Item item){
			item.id = (byte) cursor++;
			itemRegistry[cursor - 1] = item;
			return item.id;
		}
		
		public static Item get(byte id){
			return itemRegistry[id];
		}
		
	}
	
	private byte id;
	
	public byte getId(){
		return id;
	}
	
	public abstract int getIconX();
	
	public abstract int getIconY();
	
	public abstract int getMaxSizeInSlot();
	
	public abstract String getName();
	
	public abstract ItemType getType();
	
	public abstract int getPower();
	
	public abstract boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot);
	
	public enum ItemType {
		TOOL, CLOTHES, WEAPON, SETTABLE, OTHER
	}
	
	
}
