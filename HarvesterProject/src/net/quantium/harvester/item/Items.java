package net.quantium.harvester.item;

import net.quantium.harvester.entity.BuildableEntity;
import net.quantium.harvester.entity.InactiveAnvilEntity;
import net.quantium.harvester.entity.InactiveFurnaceEntity;
import net.quantium.harvester.entity.InactiveWorkbenchEntity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ToolItem.ToolType;
import net.quantium.harvester.world.World;

public class Items {
	
	 public static byte workbench;
	 public static byte chest;
	 public static byte alloyFurnace;
	 public static byte anvil;
	 
	 public static byte nut;
	
	 public static byte coal;
	
	 public static byte oreIron;
	 public static byte oreCopper;
	 public static byte oreGold;
	 public static byte orePlumbum;
	
	 public static byte gemPurple;
	 public static byte gemGreen;
	 public static byte gemBlue;
	 public static byte gemRed;

	 public static byte gemPurpleDecrystalized;
	 public static byte gemGreenDecrystalized;
	 public static byte gemBlueDecrystalized;
	 public static byte gemRedDecrystalized;
	 
	 public static byte rock;
	 public static byte wood;
	
	 public static byte iron;
	 public static byte copper;
	 public static byte gold;
	 public static byte plumbum;
	 
	 public static byte stick;
	 
	 public static byte primitivePickaxe;
	 
	 public static byte ironPickaxe;
	 public static byte goldPickaxe;
	 public static byte copperPickaxe;
	 public static byte plumbumPickaxe;
	 public static byte redPickaxe;
	 public static byte bluePickaxe;
	 
	 public static byte ironSpade;
	 public static byte goldSpade;
	 public static byte copperSpade;
	 public static byte plumbumSpade;
	 public static byte redSpade;
	 public static byte blueSpade;
	 
	 public static byte ironDagger;
	 public static byte goldDagger;
	 public static byte copperDagger;
	 public static byte plumbumDagger;
	 public static byte redDagger;
	 public static byte blueDagger;
	 
	 public static byte ironAxe;
	 public static byte goldAxe;
	 public static byte copperAxe;
	 public static byte plumbumAxe;
	 public static byte redAxe;

	 public static byte ironPickaxeFragment;
	 public static byte goldPickaxeFragment;
	 public static byte copperPickaxeFragment;
	 public static byte plumbumPickaxeFragment;
	 public static byte redPickaxeFragment;
	 public static byte bluePickaxeFragment;
	 
	 public static byte ironSpadeFragment;
	 public static byte goldSpadeFragment;
	 public static byte copperSpadeFragment;
	 public static byte plumbumSpadeFragment;
	 public static byte redSpadeFragment;
	 public static byte blueSpadeFragment;
	 
	 public static byte ironDaggerFragment;
	 public static byte goldDaggerFragment;
	 public static byte copperDaggerFragment;
	 public static byte plumbumDaggerFragment;
	 public static byte redDaggerFragment;
	 public static byte blueDaggerFragment;
	 
	 public static byte ironAxeFragment;
	 public static byte goldAxeFragment;
	 public static byte copperAxeFragment;
	 public static byte plumbumAxeFragment;
	 public static byte redAxeFragment;
	 
	 public static void register(){
		 workbench = Item.Registry.register(new BuildableItem("workbench", 0));
		 chest = Item.Registry.register(new BuildableItem("workbench", 1));
		 alloyFurnace = Item.Registry.register(new BuildableItem("alloyfurnace", 2));
		 anvil = Item.Registry.register(new BuildableItem("anvil", 3));
		 nut = Item.Registry.register(new EatableItem(){

			@Override
			public int getHeal() {
				return 3;
			}

			@Override
			public int getIconX() {
				return 0;
			}

			@Override
			public int getIconY() {
				return 0;
			}

			@Override
			public int getMaxSizeInSlot() {
				return 12;
			}

			@Override
			public String getName() {
				return "nut";
			}
			
		});
		
		
		 coal = Item.Registry.register(new FuelItem("coal", 4, 5, 45, 1, 50));
		
		 oreIron = Item.Registry.register(new AbstractItem("ironore", 4, 6, 40, 1));
		 oreCopper = Item.Registry.register(new AbstractItem("copperore", 4, 7, 40, 1));
		 oreGold = Item.Registry.register(new AbstractItem("goldore", 5, 5, 40, 1));
		 orePlumbum = Item.Registry.register(new AbstractItem("plumbumore", 5, 6, 40, 1));
		
		 gemPurple = Item.Registry.register(new AbstractItem("purplegem", 5, 7, 30, 2));
		 gemGreen = Item.Registry.register(new AbstractItem("greengem", 6, 5, 30, 2));
		 gemBlue = Item.Registry.register(new AbstractItem("bluegem", 6, 6, 30, 2));
		 gemRed = Item.Registry.register(new AbstractItem("redgem", 6, 7, 30, 3));
		
		 rock = Item.Registry.register(new AbstractItem("rock", 7, 5, 45, 2){
			 	@Override
				public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
			 		if(mode == InteractionMode.RIGHT){
			 			BuildableEntity e = new InactiveFurnaceEntity();
			 			if(w.isTilePassableBy(e, x >> 4, y >> 4) && w.canPlaceAloneSettableEntity(x, y))
			 			if(slot.consume(5)){
			 				e.x = x;
			 				e.y = y;
			 				w.addEntity(e);
			 				return true;
			 			}
			 		}
					return false;
				}
		 });
		 wood = Item.Registry.register(new FuelItem("wood", 7, 6, 20, 3, 20));
		
		 iron = Item.Registry.register(new AbstractItem("iron", 8, 5, 20, 3){
			 	@Override
				public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
			 		if(mode == InteractionMode.RIGHT){
			 			BuildableEntity e = new InactiveAnvilEntity();
			 			if(w.isTilePassableBy(e, x >> 4, y >> 4) && w.canPlaceAloneSettableEntity(x, y))
			 			if(slot.consume(9)){
			 				e.x = x;
			 				e.y = y;
			 				w.addEntity(e);
			 				return true;
			 			}
			 		}
					return false;
				}
		 });
		 copper = Item.Registry.register(new AbstractItem("copper", 8, 6, 20, 3));
		 gold = Item.Registry.register(new AbstractItem("gold", 9, 5, 20, 3));
		 plumbum = Item.Registry.register(new AbstractItem("plumbum", 9, 6, 20, 3));
		 
		 stick = Item.Registry.register(new FuelItem("stick", 10, 6, 40, 3, 5){
			 	@Override
				public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
			 		if(mode == InteractionMode.RIGHT){
			 			BuildableEntity e = new InactiveWorkbenchEntity();
			 			if(w.isTilePassableBy(e, x >> 4, y >> 4) && w.canPlaceAloneSettableEntity(x, y))
			 			if(slot.consume(4)){
			 				e.x = x;
			 				e.y = y;
			 				w.addEntity(e);
			 				return true;
			 			}
			 		}
					return false;
				}
		 });
		 
		 primitivePickaxe = Item.Registry.register(new ToolItem("primitivepickaxe", 6, 4, 3, ToolType.PICKAXE, 50, 1));
		 
		 ironPickaxe = Item.Registry.register(new ToolItem("ironpickaxe", 1, 0, 3, ToolType.PICKAXE, 150, 1));
		 copperPickaxe = Item.Registry.register(new ToolItem("copperpickaxe", 1, 1, 3, ToolType.PICKAXE, 200, 2));
		 plumbumPickaxe = Item.Registry.register(new ToolItem("plumbumpickaxe", 1, 3, 4, ToolType.PICKAXE, 280, 3));
		 goldPickaxe = Item.Registry.register(new ToolItem("goldpickaxe", 1, 2, 3, ToolType.PICKAXE, 270, 4));
		 bluePickaxe = Item.Registry.register(new ToolItem("bluepickaxe", 1, 4, 5, ToolType.PICKAXE, 400, 5));
		 redPickaxe = Item.Registry.register(new ToolItem("redpickaxe", 1, 5, 4, ToolType.PICKAXE, 400, 6));
		 
		 ironAxe = Item.Registry.register(new ToolItem("ironaxe", 3, 7, 3, ToolType.AXE, 100, 1));
		 copperAxe = Item.Registry.register(new ToolItem("copperaxe", 1, 7, 3, ToolType.AXE, 200, 2));
		 plumbumAxe = Item.Registry.register(new ToolItem("plumbumaxe", 0, 7, 4, ToolType.AXE, 280, 3));
		 goldAxe = Item.Registry.register(new ToolItem("goldaxe", 1, 6, 3, ToolType.AXE, 270, 4));
		 redAxe = Item.Registry.register(new ToolItem("redaxe", 0, 6, 4, ToolType.AXE, 300, 6));
		 
		 ironSpade = Item.Registry.register(new ToolItem("ironspade", 5, 4, 3, ToolType.SHOVEL, 100, 1));
		 copperSpade = Item.Registry.register(new ToolItem("copperspade", 0, 4, 3, ToolType.SHOVEL, 200, 2));
		 plumbumSpade = Item.Registry.register(new ToolItem("plumbumspade", 0, 3, 4, ToolType.SHOVEL, 280, 3));
		 goldSpade = Item.Registry.register(new ToolItem("goldspade", 0, 1, 3, ToolType.SHOVEL, 270, 4));
		 blueSpade = Item.Registry.register(new ToolItem("bluespade", 0, 2, 4, ToolType.SHOVEL, 300, 6));
		 redSpade = Item.Registry.register(new ToolItem("redspade", 0, 5, 4, ToolType.SHOVEL, 350, 7));
		 
		 ironDagger = Item.Registry.register(new ToolItem("irondagger", 2, 4, 8, ToolType.DAGGER, 100, 1));
		 copperDagger = Item.Registry.register(new ToolItem("copperdagger", 2, 5, 9, ToolType.DAGGER, 200, 2));
		 plumbumDagger = Item.Registry.register(new ToolItem("plumbumdagger", 2, 7, 10, ToolType.DAGGER, 280, 3));
		 goldDagger = Item.Registry.register(new ToolItem("golddagger", 2, 6, 11, ToolType.DAGGER, 260, 4));
		 blueDagger = Item.Registry.register(new ToolItem("bluedagger", 3, 5, 13, ToolType.DAGGER, 300, 6));
		 redDagger = Item.Registry.register(new ToolItem("reddagger", 3, 6, 13, ToolType.DAGGER, 350, 7));
		 
		 ironPickaxeFragment = Item.Registry.register(new AbstractItem("ironpickaxefragment", 20, 0, 1, 3));
		 copperPickaxeFragment = Item.Registry.register(new AbstractItem("copperpickaxefragment", 21, 1, 1, 3));
		 plumbumPickaxeFragment = Item.Registry.register(new AbstractItem("plumbumpickaxefragment", 21, 3, 1, 4));
		 goldPickaxeFragment = Item.Registry.register(new AbstractItem("goldpickaxefragment", 21, 2, 1, 3));
		 bluePickaxeFragment = Item.Registry.register(new AbstractItem("bluepickaxefragment", 21, 4, 1, 5));
		 redPickaxeFragment = Item.Registry.register(new AbstractItem("redpickaxefragment", 21, 5, 1, 4));
		 
		 ironAxeFragment = Item.Registry.register(new AbstractItem("ironaxefragment", 23, 7, 1, 3));
		 copperAxeFragment = Item.Registry.register(new AbstractItem("copperaxefragment", 21, 7, 1, 3));
		 plumbumAxeFragment = Item.Registry.register(new AbstractItem("plumbumaxefragment", 20, 7, 1, 4));
		 goldAxeFragment = Item.Registry.register(new AbstractItem("goldaxefragment", 21, 6, 1, 3));
		 redAxeFragment = Item.Registry.register(new AbstractItem("redaxefragment", 20, 6, 1, 4));
		 
		 ironSpadeFragment = Item.Registry.register(new AbstractItem("ironspadefragment", 21, 0, 1, 3));
		 copperSpadeFragment = Item.Registry.register(new AbstractItem("copperspadefragment", 20, 4, 1, 3));
		 plumbumSpadeFragment = Item.Registry.register(new AbstractItem("plumbumspadefragment", 20, 3, 1, 4));
		 goldSpadeFragment = Item.Registry.register(new AbstractItem("goldspadefragment", 20, 1, 1, 3));
		 blueSpadeFragment = Item.Registry.register(new AbstractItem("bluespadefragment", 20, 2, 1, 4));
		 redSpadeFragment = Item.Registry.register(new AbstractItem("redspadefragment", 20, 5, 1, 4));
		 
		 
		 ironDaggerFragment = Item.Registry.register(new AbstractItem("irondaggerfragment", 22, 4, 1, 8));
		 copperDaggerFragment = Item.Registry.register(new AbstractItem("copperdaggerfragment", 22, 5, 1, 9));
		 plumbumDaggerFragment = Item.Registry.register(new AbstractItem("plumbumdaggerfragment", 22, 7, 1, 10));
		 goldDaggerFragment = Item.Registry.register(new AbstractItem("golddaggerfragment", 22, 6, 1, 11));
		 blueDaggerFragment = Item.Registry.register(new AbstractItem("bluedaggerfragment", 23, 5, 1, 13));
		 redDaggerFragment = Item.Registry.register(new AbstractItem("reddaggerfragment", 23, 6, 1, 13));
		 
		 gemPurpleDecrystalized = Item.Registry.register(new AbstractItem("purplegemdecrystal", 7, 7, 40, 3));
		 gemRedDecrystalized = Item.Registry.register(new AbstractItem("redgemdecrystal", 8, 7, 40, 3));
		 gemGreenDecrystalized = Item.Registry.register(new AbstractItem("greengemdecrystal", 9, 7, 40, 3));
		 gemBlueDecrystalized = Item.Registry.register(new AbstractItem("bluegemdecrystal", 10, 7, 40, 3));
	}
	
}


