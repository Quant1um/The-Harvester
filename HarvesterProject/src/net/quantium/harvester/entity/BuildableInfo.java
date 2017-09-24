package net.quantium.harvester.entity;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.FuelItem;
import net.quantium.harvester.item.Item;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.screen.AnvilScreen;
import net.quantium.harvester.screen.FurnaceScreen;
import net.quantium.harvester.screen.InventoryScreen;
import net.quantium.harvester.screen.WorkbenchScreen;
import net.quantium.harvester.screen.components.InventorySlot;
import net.quantium.harvester.world.World;

public class BuildableInfo {
	public static class Registry{
		private static SettableBehavior[] metaRegistry = new SettableBehavior[256];
		private static int cursor = 0; 

		public static void register(SettableBehavior meta){
			metaRegistry[cursor++] = meta;
		}
		
		public static SettableBehavior get(int id){
			return metaRegistry[id];
		}
	}
	
	public static abstract class SettableBehavior{
		public abstract void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent);
		public abstract int getBoxHeight();
		public abstract InventorySlot[] getLayout(Inventory inventory);
		public abstract int getInventorySize();
		public abstract String getName();
		public abstract void update(BuildableEntity e);
	}
	
	public static void register(){
		Registry.register(new SettableBehavior(){

			@Override
			public void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent) {
				 playerEntity.openScreen(new WorkbenchScreen(playerEntity.inventory, ent.inventory));
			}

			@Override
			public InventorySlot[] getLayout(Inventory inventory) {
				return new InventorySlot[]{ new InventorySlot(5, 15, inventory, 0), 
											new InventorySlot(5, 35, inventory, 1), 
											new InventorySlot(100, 25, inventory, 2) };
			}

			@Override
			public int getInventorySize() {
				return 3;
			}
			
			@Override
			public int getBoxHeight() {
				return 75;
			}

			@Override
			public String getName() {
				return "workbench";
			}

			@Override
			public void update(BuildableEntity e) {
				
			}
		});
		Registry.register(new SettableBehavior(){

			@Override
			public void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent) {
				 playerEntity.openScreen(new InventoryScreen(playerEntity.inventory, ent.inventory, this));
			}

			@Override
			public InventorySlot[] getLayout(Inventory inventory) {
				InventorySlot[] slots = new InventorySlot[getInventorySize()];
				for(int i = 0; i < getInventorySize(); i++){
					int x = InventoryScreen.EMPTYSPACE + (i % InventoryScreen.SLOTS_PER_ROW) * InventoryScreen.EMPTYSPACE * 8;
					int y = InventoryScreen.EMPTYSPACE + (i / InventoryScreen.SLOTS_PER_ROW) * InventoryScreen.EMPTYSPACE * 8 + 12;
					slots[i] = new InventorySlot(x, y, inventory, i);
				}
				return slots;
			}

			@Override
			public int getInventorySize() {
				return 20;
			}
			
			@Override
			public int getBoxHeight() {
				return (getInventorySize() / InventoryScreen.SLOTS_PER_ROW) * (InventoryScreen.EMPTYSPACE + 24) + 26;
			}
			
			@Override
			public String getName() {
				return "chest";
			}

			@Override
			public void update(BuildableEntity e) {
				
			}
			
			
		});
		
		Registry.register(new SettableBehavior(){

			@Override
			public void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent) {
				 playerEntity.openScreen(new FurnaceScreen(playerEntity.inventory, ent.inventory, ent));
			}

			@Override
			public InventorySlot[] getLayout(Inventory inventory) {
				return new InventorySlot[]{ new InventorySlot(5, 15, inventory, 0), 
											new InventorySlot(100, 15, inventory, 1), 
											new InventorySlot(55, 30, inventory, 2) };
			}

			@Override
			public int getInventorySize() {
				return 3;
			}
			
			@Override
			public int getBoxHeight() {
				return 75;
			}

			@Override
			public String getName() {
				return "furnace";
			}

			@Override
			public void update(BuildableEntity e) {
				if(e.data0 > 0 && Main.getInstance().getCounter() % 40 == 0) e.data0--;
				if(e.data0 < 500 && e.inventory.get(2) != null){
					if(Item.Registry.get(e.inventory.get(2).item) instanceof FuelItem){
						int ccc = ((FuelItem) Item.Registry.get(e.inventory.get(2).item)).getFuelValue();
						if(Main.getInstance().getCounter() % 50 == 0 && e.inventory.get(2).consume(1)){
							e.data0 += ccc;
							if(e.data0 > 500) e.data0 = 500;
						}
					}
				}
			}
		});
		Registry.register(new SettableBehavior(){

			@Override
			public void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent) {
				 playerEntity.openScreen(new AnvilScreen(playerEntity.inventory, ent.inventory));
			}

			@Override
			public InventorySlot[] getLayout(Inventory inventory) {
				return new InventorySlot[]{ new InventorySlot(5, 25, inventory, 0), 
						new InventorySlot(100, 25, inventory, 1) };
			}

			@Override
			public int getInventorySize() {
				return 2;
			}
			
			@Override
			public int getBoxHeight() {
				return 170;
			}

			@Override
			public String getName() {
				return "anvil";
			}

			@Override
			public void update(BuildableEntity e) {
				
			}
		});
	}
	
}
