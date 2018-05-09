package net.quantium.harvester.item.instances;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.world.World;

public abstract class Item {
	public static class Registry {
		private static final Item[] itemRegistry = new Item[256]; //item.id - byte
		private static int cursor = 0; 
		
		private static byte register(Item item){
			byte id = (byte) cursor++;
			itemRegistry[id] = item;
			return id;
		}
		
		private static Item get(byte id){
			return itemRegistry[id];
		}
		
	}
	
	private byte id;
	
	public Item(){
		this.id = Registry.register(this);
	}
	
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
	
	public static Item get(byte id){
		return Registry.get(id);
	}
	
	public enum ItemType {
		TOOL, CLOTHES, WEAPON, BUILDABLE, OTHER
	}
}
