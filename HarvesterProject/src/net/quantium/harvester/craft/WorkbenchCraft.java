package net.quantium.harvester.craft;

import net.quantium.harvester.item.ItemSlot;

public class WorkbenchCraft {
	private final ItemSlot result;
	private final ItemSlot[] needed;
	
	public WorkbenchCraft(ItemSlot result, ItemSlot[] items){
		this.result = result;
		this.needed = items;
	}
	
	public ItemSlot getNeeded(int i) {
		return needed[i];
	}
	
	public ItemSlot getResult() {
		return result;
	}
	
}
