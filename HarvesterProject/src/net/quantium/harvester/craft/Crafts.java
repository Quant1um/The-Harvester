package net.quantium.harvester.craft;

import java.util.ArrayList;
import java.util.List;

import net.quantium.harvester.craft.AnvilCraft.HitType;
import net.quantium.harvester.entity.BuildableEntity;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;

//import net.quantium.pr01.item.Items;

public class Crafts {
	public static List<WorkbenchCraft> craft = new ArrayList<WorkbenchCraft>();
	public static List<AnvilCraft> anvil = new ArrayList<AnvilCraft>();
	public static List<FurnaceCraft> furnace = new ArrayList<FurnaceCraft>();
	
	static{
		craft.add(new WorkbenchCraft(new ItemSlot(Items.chest, 0, 1), new ItemSlot[]{new ItemSlot(Items.iron, 0, 1), new ItemSlot(Items.wood, 0, 15)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.primitivePickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.rock, 0, 1), new ItemSlot(Items.rock, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.blueDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.blueDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.redDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.redDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.goldDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.goldDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.copperDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.copperDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.ironDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.ironDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.leadDagger, 0, 1), new ItemSlot[]{new ItemSlot(Items.leadDaggerFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.blueSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.blueSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.redSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.redSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.goldSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.goldSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.copperSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.copperSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.ironSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.ironSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.leadSpade, 0, 1), new ItemSlot[]{new ItemSlot(Items.leadSpadeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.bluePickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.bluePickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.redPickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.redPickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.goldPickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.goldPickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.copperPickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.copperPickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.ironPickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.ironPickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.leadPickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.leadPickaxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		//craft.add(new WorkbenchCraft(new ItemSlot(Items.bluePickaxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.blueAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.redAxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.redAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.goldAxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.goldAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.copperAxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.copperAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.ironAxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.ironAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		craft.add(new WorkbenchCraft(new ItemSlot(Items.leadAxe, 0, 1), new ItemSlot[]{new ItemSlot(Items.leadAxeFragment, 0, 1), new ItemSlot(Items.stick, 0, 1)}));
		
		furnace.add(new FurnaceCraft(new ItemSlot(Items.iron, 0, 1), new ItemSlot(Items.oreIron, 0, 1), 40));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.copper, 0, 1), new ItemSlot(Items.oreCopper, 0, 1), 35));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.gold, 0, 1), new ItemSlot(Items.oreGold, 0, 1), 50));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.lead, 0, 1), new ItemSlot(Items.oreLead, 0, 1), 45));
		
		furnace.add(new FurnaceCraft(new ItemSlot(Items.gemBlueDecrystalized, 0, 1), new ItemSlot(Items.gemBlue, 0, 2), 70));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.gemPurpleDecrystalized, 0, 1), new ItemSlot(Items.gemPurple, 0, 2), 70));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.gemGreenDecrystalized, 0, 1), new ItemSlot(Items.gemGreen, 0, 2), 80));
		furnace.add(new FurnaceCraft(new ItemSlot(Items.gemRedDecrystalized, 0, 1), new ItemSlot(Items.gemRed, 0, 2), 80));
		
		final HitType[] PICKAXE_PATTERN = { HitType.HARD, HitType.HARD, HitType.DRAW };
		final HitType[] AXE_PATTERN =     { HitType.HARD, HitType.SOFT, HitType.DRAW };
		final HitType[] SPADE_PATTERN =   { HitType.SOFT, HitType.HARD, HitType.DRAW };
		final HitType[] DAGGER_PATTERN =  { HitType.SOFT, HitType.HARD, HitType.HARD };
		
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironPickaxeFragment, 0, 1), new ItemSlot(Items.iron, 0, 3), PICKAXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperPickaxeFragment, 0, 1), new ItemSlot(Items.copper, 0, 3), PICKAXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironPickaxeFragment, 0, 1), new ItemSlot(Items.gold, 0, 3), PICKAXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperPickaxeFragment, 0, 1), new ItemSlot(Items.lead, 0, 3), PICKAXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.redPickaxeFragment, 0, 1), new ItemSlot(Items.gemRedDecrystalized, 0, 5), PICKAXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.bluePickaxeFragment, 0, 1), new ItemSlot(Items.gemBlueDecrystalized, 0, 5), PICKAXE_PATTERN));
		
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironAxeFragment, 0, 1), new ItemSlot(Items.iron, 0, 3), AXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperAxeFragment, 0, 1), new ItemSlot(Items.copper, 0, 3), AXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironAxeFragment, 0, 1), new ItemSlot(Items.gold, 0, 3), AXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperAxeFragment, 0, 1), new ItemSlot(Items.lead, 0, 3), AXE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.redAxeFragment, 0, 1), new ItemSlot(Items.gemRedDecrystalized, 0, 5), AXE_PATTERN));
		
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironSpadeFragment, 0, 1), new ItemSlot(Items.iron, 0, 3), SPADE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperSpadeFragment, 0, 1), new ItemSlot(Items.copper, 0, 3), SPADE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironSpadeFragment, 0, 1), new ItemSlot(Items.gold, 0, 3), SPADE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperSpadeFragment, 0, 1), new ItemSlot(Items.lead, 0, 3), SPADE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.redSpadeFragment, 0, 1), new ItemSlot(Items.gemRedDecrystalized, 0, 5), SPADE_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.blueSpadeFragment, 0, 1), new ItemSlot(Items.gemBlueDecrystalized, 0, 5), SPADE_PATTERN));
		
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironDaggerFragment, 0, 1), new ItemSlot(Items.iron, 0, 3), DAGGER_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperDaggerFragment, 0, 1), new ItemSlot(Items.copper, 0, 3), DAGGER_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.ironDaggerFragment, 0, 1), new ItemSlot(Items.gold, 0, 3), DAGGER_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.copperDaggerFragment, 0, 1), new ItemSlot(Items.lead, 0, 3), DAGGER_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.redDaggerFragment, 0, 1), new ItemSlot(Items.gemRedDecrystalized, 0, 5), DAGGER_PATTERN));
		anvil.add(new AnvilCraft(new ItemSlot(Items.blueDaggerFragment, 0, 1), new ItemSlot(Items.gemBlueDecrystalized, 0, 5), DAGGER_PATTERN));
	}
	
	public static ItemSlot tryCraftOnWorkbench(ItemSlot it0, ItemSlot it1){
		if(it0 == null && it1 == null) return null;
		for(int i = 0; i < craft.size(); i++){
			WorkbenchCraft c = craft.get(i);
			boolean isNeeded0 = false, isNeeded1 = false;
			if(c.getNeeded(0) != null && it0 != null){
				if(it0.equalsIgnoreCount(c.getNeeded(0)) && it0.getCount() >= c.getNeeded(0).getCount()) isNeeded0 = true;
			}
			if(c.getNeeded(1) != null && it1 != null){
				if(it1.equalsIgnoreCount(c.getNeeded(1)) && it1.getCount() >= c.getNeeded(1).getCount()) isNeeded1 = true;
			}
			if(isNeeded0 && isNeeded1) {
				if(c.getNeeded(0) != null){
					it0.consume(c.getNeeded(0).getCount());
				}
				if(c.getNeeded(1) != null){
					it1.consume(c.getNeeded(1).getCount());
				}
				return c.getResult().copy();
			}
		}
		return null;
	}

	public static ItemSlot tryCraftInFurnace(ItemSlot itemSlot, BuildableEntity e) {
		if(itemSlot == null) return null;
		for(int i = 0; i < furnace.size(); i++){
			FurnaceCraft c = furnace.get(i);
			if(c.getPower() <= e.data0 && itemSlot.equalsIgnoreCount(c.getNeeded()))
				if(itemSlot.consume(c.getNeeded().getCount())){
					e.data0 -= c.getPower();
					return c.getResult().copy();
				}
		}
		return null;
	}
	
	public static ItemSlot tryCraftOnAnvil(ItemSlot itemSlot, HitType[] pattern) {
		if(itemSlot == null) return null;
		for(int i = 0; i < anvil.size(); i++){
			AnvilCraft c = anvil.get(i);
			if(c.getNeeded().equalsIgnoreCount(itemSlot) && ((pattern[0] == c.getPattern()[0]
														 &&   pattern[1] == c.getPattern()[1]
														 &&   pattern[2] == c.getPattern()[2])
														 ||	( pattern[0] == c.getPattern()[2]
														 &&   pattern[1] == c.getPattern()[1]
														 &&   pattern[2] == c.getPattern()[0]))){
				if(itemSlot.consume(c.getNeeded().getCount())){
					return c.getResult().copy();
				}
			}
		}
		return null;
	}
}
