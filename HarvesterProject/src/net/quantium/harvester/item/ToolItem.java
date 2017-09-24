package net.quantium.harvester.item;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.world.World;

public class ToolItem extends AbstractItem {
	private ToolType type;
	private int durability;
	public int level;
	
	
	public ToolItem(String name, int iconX, int iconY, int power, ToolType type, int durability, int level) {
		super(name, iconX, iconY, 1, power);
		this.type = type;
		this.durability = durability;
		this.level = level;
	}

	@Override
	public ItemType getType() {
		return type == ToolType.DAGGER ? ItemType.WEAPON : ItemType.TOOL;
	}

	public ToolType getToolType() {
		return type;
	}

	@Override
	public boolean interact(World w, int x, int y, PlayerEntity ply, InteractionMode mode, ItemSlot slot) {
		if(mode == InteractionMode.LEFT){
			//System.out.println(slot);
			ply.hitSelected(level, type);
			if(Main.GLOBAL_RANDOM.nextInt(5) == 0)
				slot.damage(1);
			if(slot.getMeta() >= durability) slot.anullate();
			return true;
		}
		return false;
	}


	public int getDurability(){
		return durability;
	}
	
	public enum ToolType {
		SHOVEL, AXE, PICKAXE, DAGGER, NONE
	}
}
