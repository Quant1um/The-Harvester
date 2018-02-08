package net.quantium.harvester.entity;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.FuelItem;
import net.quantium.harvester.item.Item;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.screen.AnvilScreen;
import net.quantium.harvester.screen.FurnaceScreen;
import net.quantium.harvester.screen.InventoryScreen;
import net.quantium.harvester.screen.WorkbenchScreen;
import net.quantium.harvester.screen.components.InventorySlot;
import net.quantium.harvester.world.World;

public class BuildableInfo {
	public static class Registry{
		private static BuildableBehavior[] registry = new BuildableBehavior[BuildableType.values().length];

		public static void register(BuildableType type, BuildableBehavior meta){
			if(registry[type.ordinal()] != null) throw new java.lang.IllegalStateException("Buildable info for type " + type + " already registered!");
			registry[type.ordinal()] = meta;
		}
		
		public static BuildableBehavior get(BuildableType type){
			return registry[type.ordinal()];
		}
	}
	
	public static abstract class BuildableBehavior{
		
		public final int inventorySize;
		public final int boxHeight;
		public final String name;
		public final byte item;
		public final int spriteOffset;
		
		public BuildableBehavior(String name, int inventorySize, int boxHeight, byte item, int spriteOffset) {
			this.inventorySize = inventorySize;
			this.boxHeight = boxHeight;
			this.name = name;
			this.item = item;
			this.spriteOffset = spriteOffset;
		}
		public abstract void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent);
		public abstract InventorySlot[] getLayout(Inventory inventory);
		public abstract void update(BuildableEntity e);
		public abstract ItemSlot[] getRequiredItems();
	}
	
	public static void register(){
		Registry.register(BuildableType.WORKBENCH, new BuildableBehavior("workbench", 3, 75, Items.workbench, 0){

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
			public void update(BuildableEntity e) {}

			@Override
			public ItemSlot[] getRequiredItems() {
				return new ItemSlot[]{
						new ItemSlot(Items.wood,  0, 4),
						new ItemSlot(Items.stick, 0, 2),
						new ItemSlot(Items.wood,  0, 6),
						new ItemSlot(Items.rock,  0, 4),
						new ItemSlot(Items.stick, 0, 2),
						new ItemSlot(Items.wood,  0, 2),
				};
			}
		});
		
		Registry.register(BuildableType.CHEST, new BuildableBehavior("chest", 20, (20 / InventoryScreen.SLOTS_PER_ROW) * (InventoryScreen.EMPTYSPACE + 24) + 26, Items.chest, 1){

			@Override
			public void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent) {
				 playerEntity.openScreen(new InventoryScreen(playerEntity.inventory, ent.inventory, this));
			}

			@Override
			public InventorySlot[] getLayout(Inventory inventory) {
				InventorySlot[] slots = new InventorySlot[this.inventorySize];
				for(int i = 0; i < slots.length; i++){
					int x = InventoryScreen.EMPTYSPACE + (i % InventoryScreen.SLOTS_PER_ROW) * InventoryScreen.EMPTYSPACE * 8;
					int y = InventoryScreen.EMPTYSPACE + (i / InventoryScreen.SLOTS_PER_ROW) * InventoryScreen.EMPTYSPACE * 8 + 12;
					slots[i] = new InventorySlot(x, y, inventory, i);
				}
				return slots;
			}
			
			@Override
			public void update(BuildableEntity e) {}
			
			@Override
			public ItemSlot[] getRequiredItems() {
				return new ItemSlot[]{
						new ItemSlot(Items.wood,   0, 4),
						new ItemSlot(Items.copper, 0, 2),
						new ItemSlot(Items.wood,   0, 6),
						new ItemSlot(Items.rock,   0, 4),
						new ItemSlot(Items.wood,   0, 2),
						new ItemSlot(Items.iron,   0, 2),
				};
			}
		});
		
		Registry.register(BuildableType.FURNACE, new BuildableBehavior("furnace", 3, 75, Items.alloyFurnace, 2){

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
			
			@Override
			public ItemSlot[] getRequiredItems() {
				return new ItemSlot[]{
						new ItemSlot(Items.rock, 0, 8),
						new ItemSlot(Items.rock, 0, 12),
						new ItemSlot(Items.coal, 0, 6),
						new ItemSlot(Items.wood, 0, 4),
						new ItemSlot(Items.rock, 0, 12),
						new ItemSlot(Items.rock, 0, 10),
				};
			}
		});
		Registry.register(BuildableType.ANVIL, new BuildableBehavior("anvil", 2, 170, Items.anvil, 3){

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
			public void update(BuildableEntity e) {}
			
			@Override
			public ItemSlot[] getRequiredItems() {
				return new ItemSlot[]{
						new ItemSlot(Items.iron,   0, 4),
						new ItemSlot(Items.lead, 0, 2),
						new ItemSlot(Items.wood,   0, 6),
						new ItemSlot(Items.rock,   0, 4),
						new ItemSlot(Items.wood,   0, 2),
						new ItemSlot(Items.iron,   0, 2),
				};
			}
		});
	}
	
	public enum BuildableType {
		WORKBENCH, CHEST, FURNACE, ANVIL
	}
}
