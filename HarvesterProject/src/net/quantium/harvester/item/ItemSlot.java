package net.quantium.harvester.item;

import java.io.Serializable;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;

public class ItemSlot implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public byte item;
	public int meta;
	public int count;
	
	public ItemSlot(byte item, int meta, int count) {
		this.item = item;
		this.meta = meta;
		this.count = count;
	}
	
	public byte getItem() {
		return item;
	}
	public void setItem(byte item) {
		this.item = item;
	}
	public int getMeta() {
		return meta;
	}
	public void setMeta(int meta) {
		this.meta = meta;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		if(count > Item.Registry.get(item).getMaxSizeInSlot()) count = Item.Registry.get(item).getMaxSizeInSlot();
			this.count = count;
	}
	
	public boolean consume(int count){
		if(count > this.count) return false;
		this.count -= count;
		return true;
	}
	
	@Override
	public boolean equals(Object e){
		if(equalsIgnoreCount(e)) return ((ItemSlot)e).getCount() == getCount();
		return false;
	}
	
	public boolean equalsIgnoreCount(Object e){
		if(e instanceof ItemSlot){
			ItemSlot i = (ItemSlot) e;
			return i.getItem() == getItem() && i.getMeta() == getMeta();
		}
		return false;
	}
	
	public static void renderItemSlot(Renderer render, int x, int y, ItemSlot item){
		if(item == null) return;
		Item itemd = Item.Registry.get(item.getItem());
		if(itemd == null) return;
		render.get().draw(x, y, itemd.getIconX() * 2, itemd.getIconY() * 2, 2, 2, "sheet0", 0);
		if(item.getCount() > 1){
			render.get().drawText(x + 2 * Layer.BLOCK_SIZE - 4 + 1, y + 2 * Layer.BLOCK_SIZE - 4 + 1, FontSize.NORMAL, String.valueOf(item.getCount()), 000);
			render.get().drawText(x + 2 * Layer.BLOCK_SIZE - 4, y + 2 * Layer.BLOCK_SIZE - 4, FontSize.NORMAL, String.valueOf(item.getCount()), 777);
		}else if(itemd instanceof ToolItem){
			int percentage = 100 - (int)(((float)item.getMeta() / ((ToolItem) itemd).getDurability()) * 100f);
			String ss = percentage >= 97 ? "s" : percentage >= 85 ? "a" : percentage >= 60 ? "b" : percentage >= 35 ? "c" : percentage >= 10 ? "d" : "f";
			
			render.get().drawText(x + 2 * Layer.BLOCK_SIZE - 8 + 1, y + 2 * Layer.BLOCK_SIZE - 8 + 1, FontSize.NORMAL, ss, 000);
			render.get().drawText(x + 2 * Layer.BLOCK_SIZE - 8, y + 2 * Layer.BLOCK_SIZE - 8, FontSize.NORMAL, ss, 777);
		}
		if(Main.getInstance().getDebugMode() == 3){
			render.get().drawText(x, y, FontSize.SMALL, String.valueOf(item.meta), 358);
		}
	}
	
	public static MergedItem merge(ItemSlot it0, ItemSlot it1){
		ItemSlot i0 = it0;
		ItemSlot i1 = it1;
		if(i1 == null) return new MergedItem(i0, i1, true);
		if(i0 == null){
			i0 = i1;
			i1 = null;
			return new MergedItem(i0, i1, true);
		}
		if(i0.item != i1.item) return new MergedItem(i0, i1, false);
		if(i0.meta != i1.meta) return new MergedItem(i0, i1, false);
		int sum = i0.count + i1.count;
		int red = 0;
		if(sum > Item.Registry.get(i0.item).getMaxSizeInSlot()){
			int c = Item.Registry.get(i0.item).getMaxSizeInSlot();
			red = sum - c;
			sum = c;
		}
		i0.count = sum;
		i1.count = red;
		if(i0.count <= 0) i0 = null;
		if(i1.count <= 0) i1 = null;
		return new MergedItem(i0, i1, true);
	}

	public static class MergedItem{
		private ItemSlot slot;
		private ItemSlot other;
		
		public MergedItem(ItemSlot slot, ItemSlot other, boolean merged) {
			this.slot = slot;
			this.other = other;
			this.merged = merged;
		}

		private boolean merged;
		
		public ItemSlot getOther() {
			return other;
		}
		
		public ItemSlot getSlot() {
			return slot;
		}

		public boolean isMerged() {
			return merged;
		}
		
		public String toString(){
			return "MergedItem{" + merged + ", First=" + slot + ", Second=" + other + "}";
		}
	}
	public ItemSlot copy() {
		return new ItemSlot(item, meta, count);
	}

	public void anullate() {
		this.count = 0;
	}
	
	public void damage(int meta){
		this.meta += meta;
	}
	
	public String toString(){
		return "Item{" + (Item.Registry.get(item) == null ? "null" : Item.Registry.get(item).getName()) + ", Count=" + count + ", Meta=" + meta + "}";
	}
	
}
