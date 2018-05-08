package net.quantium.harvester.item;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.entity.buildable.BuildableEntity;
import net.quantium.harvester.entity.buildable.InactiveAnvilEntity;
import net.quantium.harvester.entity.buildable.InactiveFurnaceEntity;
import net.quantium.harvester.entity.buildable.InactiveWorkbenchEntity;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableType;
import net.quantium.harvester.item.instances.AbstractItem;
import net.quantium.harvester.item.instances.BuildableItem;
import net.quantium.harvester.item.instances.EatableItem;
import net.quantium.harvester.item.instances.FuelItem;
import net.quantium.harvester.item.instances.Item;
import net.quantium.harvester.item.instances.ToolItem;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.world.World;

public class Items {
	
	 public static final Item workbench = new BuildableItem("workbench", BuildableType.WORKBENCH);
	 public static final Item chest = new BuildableItem("chest", BuildableType.CHEST);
	 public static final Item alloyFurnace = new BuildableItem("furnace", BuildableType.FURNACE);
	 public static final Item anvil = new BuildableItem("anvil", BuildableType.ANVIL);
	 
	 public static final Item nut = new EatableItem("acorn", 0, 0, 30, 3);
	
	 public static final Item coal = new FuelItem("coal", 4, 5, 45, 1, 50);
	
	 public static final Item oreIron = new AbstractItem("ironore", 4, 6, 40, 1);
	 public static final Item oreCopper = new AbstractItem("copperore", 4, 7, 40, 1);
	 public static final Item oreGold = new AbstractItem("goldore", 5, 5, 40, 1);
	 public static final Item oreLead = new AbstractItem("leadore", 5, 6, 40, 1);
	
	 public static final Item gemPurple = new AbstractItem("purplegem", 5, 7, 30, 2);
	 public static final Item gemGreen = new AbstractItem("greengem", 6, 5, 30, 2);
	 public static final Item gemBlue = new AbstractItem("bluegem", 6, 6, 30, 2);
	 public static final Item gemRed = new AbstractItem("redgem", 6, 7, 30, 3);

	 public static final Item gemPurpleDecrystalized = new AbstractItem("purplegemdecrystal", 7, 7, 40, 3);
	 public static final Item gemGreenDecrystalized = new AbstractItem("redgemdecrystal", 8, 7, 40, 3);
	 public static final Item gemBlueDecrystalized = new AbstractItem("greengemdecrystal", 9, 7, 40, 3);
	 public static final Item gemRedDecrystalized = new AbstractItem("bluegemdecrystal", 10, 7, 40, 3);
	 
	 public static final Item rock = new AbstractItem("rock", 7, 5, 45, 2){
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
	 };
	 
	 public static final Item wood = new FuelItem("wood", 7, 6, 20, 3, 20);
	
	 public static final Item iron = new AbstractItem("iron", 8, 5, 20, 3){
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
	 };
	 
	 public static final Item copper = new AbstractItem("copper", 8, 6, 20, 3);
	 public static final Item gold = new AbstractItem("gold", 9, 5, 20, 3);
	 public static final Item lead = new AbstractItem("lead", 9, 6, 20, 3);
	 
	 public static final Item stick = new FuelItem("stick", 10, 6, 40, 3, 5){
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
	 };
	 
	 public static final Item primitivePickaxe = new ToolItem("primitivepickaxe", 6, 4, 3, ToolType.PICKAXE, 50, 1);
	 
	 public static final Item ironPickaxe = new ToolItem("ironpickaxe", 1, 0, 3, ToolType.PICKAXE, 150, 1);
	 public static final Item copperPickaxe = new ToolItem("copperpickaxe", 1, 1, 3, ToolType.PICKAXE, 200, 2);
	 public static final Item leadPickaxe = new ToolItem("leadpickaxe", 1, 3, 4, ToolType.PICKAXE, 280, 3);
	 public static final Item goldPickaxe = new ToolItem("goldpickaxe", 1, 2, 3, ToolType.PICKAXE, 270, 4);
	 public static final Item bluePickaxe = new ToolItem("bluepickaxe", 1, 4, 5, ToolType.PICKAXE, 400, 5);
	 public static final Item redPickaxe = new ToolItem("redpickaxe", 1, 5, 4, ToolType.PICKAXE, 400, 6);
	 
	 public static final Item ironSpade = new ToolItem("ironspade", 5, 4, 3, ToolType.SHOVEL, 100, 1);
	 public static final Item copperSpade = new ToolItem("copperspade", 0, 4, 3, ToolType.SHOVEL, 200, 2);
	 public static final Item leadSpade = new ToolItem("leadspade", 0, 3, 4, ToolType.SHOVEL, 280, 3);
	 public static final Item goldSpade = new ToolItem("goldspade", 0, 1, 3, ToolType.SHOVEL, 270, 4);
	 public static final Item blueSpade = new ToolItem("bluespade", 0, 2, 4, ToolType.SHOVEL, 300, 6);
	 public static final Item redSpade = new ToolItem("redspade", 0, 5, 4, ToolType.SHOVEL, 350, 7);
	 
	 public static final Item ironDagger = new ToolItem("irondagger", 2, 4, 8, ToolType.DAGGER, 100, 1);
	 public static final Item copperDagger = new ToolItem("copperdagger", 2, 5, 9, ToolType.DAGGER, 200, 2);
	 public static final Item leadDagger = new ToolItem("leaddagger", 2, 7, 10, ToolType.DAGGER, 280, 3);
	 public static final Item goldDagger = new ToolItem("golddagger", 2, 6, 11, ToolType.DAGGER, 260, 4);
	 public static final Item blueDagger = new ToolItem("bluedagger", 3, 5, 13, ToolType.DAGGER, 300, 6);
	 public static final Item redDagger = new ToolItem("reddagger", 3, 6, 13, ToolType.DAGGER, 350, 7);
	 
	 public static final Item ironAxe = new ToolItem("ironaxe", 3, 7, 3, ToolType.AXE, 100, 1);
	 public static final Item copperAxe = new ToolItem("copperaxe", 1, 7, 3, ToolType.AXE, 200, 2);
	 public static final Item leadAxe = new ToolItem("leadaxe", 0, 7, 4, ToolType.AXE, 280, 3);
	 public static final Item goldAxe = new ToolItem("goldaxe", 1, 6, 3, ToolType.AXE, 270, 4);
	 public static final Item redAxe = new ToolItem("redaxe", 0, 6, 4, ToolType.AXE, 300, 6);

	 public static final Item ironPickaxeFragment = new AbstractItem("ironpickaxefragment", 20, 0, 1, 3);
	 public static final Item copperPickaxeFragment = new AbstractItem("copperpickaxefragment", 21, 1, 1, 3);
	 public static final Item leadPickaxeFragment = new AbstractItem("leadpickaxefragment", 21, 3, 1, 4);
	 public static final Item goldPickaxeFragment = new AbstractItem("goldpickaxefragment", 21, 2, 1, 3);
	 public static final Item bluePickaxeFragment = new AbstractItem("bluepickaxefragment", 21, 4, 1, 5);
	 public static final Item redPickaxeFragment = new AbstractItem("redpickaxefragment", 21, 5, 1, 4);
	 
	 public static final Item ironSpadeFragment = new AbstractItem("ironspadefragment", 21, 0, 1, 3);
	 public static final Item copperSpadeFragment = new AbstractItem("copperspadefragment", 20, 4, 1, 3);
	 public static final Item leadSpadeFragment = new AbstractItem("leadspadefragment", 20, 3, 1, 4);
	 public static final Item goldSpadeFragment = new AbstractItem("goldspadefragment", 20, 1, 1, 3);
	 public static final Item blueSpadeFragment = new AbstractItem("bluespadefragment", 20, 2, 1, 4);
	 public static final Item redSpadeFragment = new AbstractItem("redspadefragment", 20, 5, 1, 4);
	 
	 public static final Item ironDaggerFragment = new AbstractItem("irondaggerfragment", 22, 4, 1, 8);
	 public static final Item copperDaggerFragment = new AbstractItem("copperdaggerfragment", 22, 5, 1, 9);
	 public static final Item leadDaggerFragment = new AbstractItem("leaddaggerfragment", 22, 7, 1, 10);
	 public static final Item goldDaggerFragment = new AbstractItem("golddaggerfragment", 22, 6, 1, 11);
	 public static final Item blueDaggerFragment = new AbstractItem("bluedaggerfragment", 23, 5, 1, 13);
	 public static final Item redDaggerFragment = new AbstractItem("reddaggerfragment", 23, 6, 1, 13);
	 
	 public static final Item ironAxeFragment = new AbstractItem("ironaxefragment", 23, 7, 1, 3);
	 public static final Item copperAxeFragment = new AbstractItem("copperaxefragment", 21, 7, 1, 3);
	 public static final Item leadAxeFragment = new AbstractItem("leadaxefragment", 20, 7, 1, 4);
	 public static final Item goldAxeFragment = new AbstractItem("goldaxefragment", 21, 6, 1, 3);
	 public static final Item redAxeFragment = new AbstractItem("redaxefragment", 20, 6, 1, 4);
}


