package net.quantium.harvester.craft;

import net.quantium.harvester.item.ItemSlot;

public class AnvilCraft {
	private final ItemSlot result;
	private final ItemSlot needed;
	private final int[] pattern;
	
	public ItemSlot getResult() {
		return result;
	}

	public ItemSlot getNeeded() {
		return needed;
	}

	public AnvilCraft(ItemSlot result, ItemSlot needed, int[] pattern) {
		this.result = result;
		this.needed = needed;
		this.pattern = pattern;
	}

	public int[] getPattern() {
		return pattern;
	}
}
