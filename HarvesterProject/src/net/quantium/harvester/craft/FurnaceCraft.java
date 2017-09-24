package net.quantium.harvester.craft;

import net.quantium.harvester.item.ItemSlot;

public class FurnaceCraft {
	private final ItemSlot result;
	private final ItemSlot needed;
	private final int power;
	
	public FurnaceCraft(ItemSlot result, ItemSlot needed, int neededFuelPower){
		this.result = result;
		this.needed = needed;
		this.power = neededFuelPower;
	}

	public ItemSlot getResult() {
		return result;
	}

	public ItemSlot getNeeded() {
		return needed;
	}

	public int getPower() {
		return power;
	}
}
