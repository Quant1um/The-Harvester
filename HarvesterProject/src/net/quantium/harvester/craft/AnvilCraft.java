package net.quantium.harvester.craft;

import net.quantium.harvester.item.ItemSlot;

public class AnvilCraft {
	private final ItemSlot result;
	private final ItemSlot needed;
	private final HitType[] pattern;
	
	public ItemSlot getResult() {
		return result;
	}

	public ItemSlot getNeeded() {
		return needed;
	}

	public AnvilCraft(ItemSlot result, ItemSlot needed, HitType[] pattern) {
		this.result = result;
		this.needed = needed;
		this.pattern = pattern;
	}

	public HitType[] getPattern() {
		return pattern;
	}
	
	public enum HitType{
		HARD(0), SOFT(1), DRAW(2);
		
		public final int offsetX;
		
		private HitType(int offsetX){
			this.offsetX = offsetX;
		}
	}
}
