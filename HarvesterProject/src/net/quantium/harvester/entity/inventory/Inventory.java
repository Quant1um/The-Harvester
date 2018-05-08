package net.quantium.harvester.entity.inventory;

import java.io.Serializable;

import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ItemSlot.MergedItem;

public class Inventory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final ItemSlot[] slots;
	
	public Inventory(int slots){
		this.slots = new ItemSlot[slots];
	}
	
	public ItemSlot add(ItemSlot item){
		if(item == null) return item;
		int idx = indexOf(item);
		if(idx >= 0){
			for(int i = 0; i < size(); i++){
				if(get(i) != null && get(i).equalsIgnoreCount(item)){
					MergedItem mg = ItemSlot.merge(get(i), item);
					if(mg.isMerged()){
						item = mg.getOther();
						set(i, mg.getSlot());
						if(item == null) return null;
					}
				}
			}
		}
		
		//System.out.println(item);
		for(int i = 0; i < size(); i++){
			if(get(i) == null){
				set(i, item);
				item = null;
				return item;
			}else if(get(i).getItem() == item.getItem() && get(i).getMeta() == item.getMeta()){
				MergedItem mg = ItemSlot.merge(get(i), item);
				if(mg.isMerged()){
					item = mg.getOther();
					set(i, mg.getSlot());
					if(item == null) return null;
				}
			}
		}
		return item;
	}
	
	public boolean reduce(ItemSlot item){
		if(item == null) return false;
		for(int i = 0; i < size(); i++){
			if(get(i) != null)
				if(get(i).getItem() == item.getItem() && get(i).getMeta() == item.getMeta()){
					if(get(i).getCount() >= item.getCount()){
						get(i).consume(item.getCount());
						return true;
					}
				}
		}
		return false;
	}
	
	public int indexOf(ItemSlot slot){
		for(int i = 0; i < size(); i++){
			if(get(i) != null && get(i).getItem() == slot.getItem() && get(i).getMeta() == slot.getMeta())
				return i;
		}
		return -1;
	}
	
	public boolean contains(ItemSlot item){
		return indexOf(item) >= 0;
	}
	
	
	public void swap(int slot, int slot0){
		ItemSlot temp = get(slot);
		set(slot, get(slot0));
		set(slot0, temp);
	}
	
	public ItemSlot get(int slot){
		if(slot < 0 || slot >= size()) return null;
		//System.out.println(slot);
		ItemSlot slotd = slots[slot];
		if(slots[slot] != null && slotd.getCount() <= 0) slots[slot] = null;
		return slots[slot];
	}
	
	public void set(int slot, ItemSlot slotd){
		if(slot < 0 || slot >= size()) return;
		slots[slot] = slotd;
	}
	
	public int size(){
		return slots.length;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Inventory{Size=").append(size()).append(", Items={");
		for(int i = 0; i < size(); i++) {
			sb.append(get(i));
			if(i < size() - 1) sb.append(", ");
		}
		return sb.append("}}").toString();
	}
}
