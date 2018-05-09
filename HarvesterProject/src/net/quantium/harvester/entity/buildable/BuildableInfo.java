package net.quantium.harvester.entity.buildable;

import java.util.EnumMap;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.item.instances.FuelItem;
import net.quantium.harvester.item.instances.Item;
import net.quantium.harvester.screen.AnvilScreen;
import net.quantium.harvester.screen.FurnaceScreen;
import net.quantium.harvester.screen.InventoryScreen;
import net.quantium.harvester.screen.WorkbenchScreen;
import net.quantium.harvester.screen.components.InventorySlot;
import net.quantium.harvester.world.World;

public class BuildableInfo {
	public static class Registry{
		private static final EnumMap<BuildableType, BuildableBehavior> registry = new EnumMap<>(BuildableType.class);

		public static void register(BuildableType type, BuildableBehavior meta){
			if(registry.containsKey(type)) 
				throw new java.lang.IllegalStateException("Buildable info for type " + type + " already registered!");
			registry.put(type, meta);
		}
		
		public static BuildableBehavior get(BuildableType type){
			return registry.get(type);
		}
	}
	
	public static abstract class BuildableBehavior{
		
		public final int inventorySize;
		public final int boxHeight;
		public final String name;
		public final Item item;
		public final int spriteOffset;
		public final Class<?> containerClass;
		
		public BuildableBehavior(String name, int inventorySize, int boxHeight, Item item, int spriteOffset) {
			this(name, inventorySize, boxHeight, item, spriteOffset, null);
		}
		
		public BuildableBehavior(String name, int inventorySize, int boxHeight, Item item, int spriteOffset, Class<?> containerClass) {
			this.inventorySize = inventorySize;
			this.boxHeight = boxHeight;
			this.name = name;
			this.item = item;
			this.spriteOffset = spriteOffset;
			this.containerClass = containerClass;
		}
		
		public abstract void onInteract(World world, PlayerEntity playerEntity, InteractionMode im, ItemSlot item, BuildableEntity ent);
		public abstract InventorySlot[] getLayout(Inventory inventory);
		public abstract void update(BuildableEntity e);
		public abstract ItemSlot[] getRequiredItems();
		
		public Object instantiateContainer() {
			try {
				return containerClass == null ? null : containerClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
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
		
		Registry.register(BuildableType.FURNACE, new BuildableBehavior("furnace", 3, 75, Items.alloyFurnace, 2, FurnaceContainer.class){

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
				FurnaceContainer container = (FurnaceContainer) e.container;
				if(container.fuel > 0 && Main.instance().getCounter() % 40 == 0) container.fuel--;
				if(container.fuel< 500 && e.inventory.get(2) != null){
					if(e.inventory.get(2).getItem() instanceof FuelItem){
						int ccc = ((FuelItem) e.inventory.get(2).getItem()).getFuelValue();
						if(Main.instance().getCounter() % 50 == 0 && e.inventory.get(2).consume(1)){
							container.fuel += ccc;
							if(container.fuel > 500) container.fuel = 500;
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
						new ItemSlot(Items.lead,   0, 2),
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
