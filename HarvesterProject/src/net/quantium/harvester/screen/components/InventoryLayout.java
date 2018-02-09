package net.quantium.harvester.screen.components;

import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ItemSlot.MergedItem;
import net.quantium.harvester.render.Renderer;

public class InventoryLayout extends AbstractContainer<InventorySlot>{
	
	protected int selectedSlot0 = -1;
	protected int selectedSlot1 = -1;
	protected int clicked = 0;
	protected InventoryManipulationMode mode = InventoryManipulationMode.NONE;
	
	protected Inventory inventory;
	
	public InventoryLayout(int x, int y, int w, int h, Inventory i){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.inventory = i;
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first){
		if(button == MouseState.RELEASED){
			if(selectedSlot0 >= 0 && selectedSlot1 >= 0){
				switch(mode){
					case SEPARATE: separate(selectedSlot0, selectedSlot1); break;
					case MERGE_SWAP: swap(selectedSlot1, selectedSlot1); break;
					default: break;
				}
				mode = InventoryManipulationMode.NONE;
				selectedSlot0 = -1;
				selectedSlot1 = -1;
			}
			return;
		}
		mode = button == MouseState.RIGHT ? InventoryManipulationMode.SEPARATE :
							 				InventoryManipulationMode.MERGE_SWAP;
		int c = getIndexOn(x, y);
		if(c >= 0){
			if(focused != c){
				focused = c;
				comps.get(c).onSelect();
			}
		}else{
			focused = -1;
		}
		if(focused >= 0 && focused < comps.size()){
			if(first) selectedSlot0 = focused;
			else selectedSlot1 = focused;
			comps.get(focused).onMouseClick(x, y, button, first);
		}
	}
	
	@Override
	public void render(Renderer render, boolean tfocused){
		super.render(render, tfocused);
		if(selectedSlot0 >= 0 && selectedSlot0 < this.comps.size() && selectedSlot1 >= 0 && selectedSlot1 < this.comps.size()){
			Component c0 = this.comps.get(selectedSlot0);
			Component c1 = this.comps.get(selectedSlot1);
			render.get().drawLine(c0.x + 8, c0.y + 8, c1.x + 8, c1.y + 8, mode == InventoryManipulationMode.SEPARATE ? 939 : 993);
		}
	}
	
	public void swap(int slot0, int slot1){
		if(slot0 == slot1) return;
		if(slot0 >= 0 && slot0 < this.comps.size() && slot1 >= 0 && slot1 < this.comps.size()){
			InventorySlot c0 = this.comps.get(slot0);
			InventorySlot c1 = this.comps.get(slot1);
			if(c0.getValue() != null && c0.getValue().equalsIgnoreCount(c1.getValue())){
				MergedItem temp = ItemSlot.merge(c0.getValue(), c1.getValue());
				if(!temp.isMerged()){
					ItemSlot temp2 = c0.getValue();
					c0.setValue(c1.getValue());
					c1.setValue(temp2);
				}else{
					c0.setValue(temp.getOther());
					c1.setValue(temp.getSlot());
				}
			}else{
				ItemSlot temp2 = c0.getValue();
				c0.setValue(c1.getValue());
				c1.setValue(temp2);
			}
		}
	}
	
	public void separate(int slot0, int slot1){
		if(slot0 == slot1) return;
		if(slot0 >= 0 && slot0 < this.comps.size() && slot1 >= 0 && slot1 < this.comps.size()){
			InventorySlot c0 = this.comps.get(slot0);
			InventorySlot c1 = this.comps.get(slot1);
			ItemSlot is0 = c0.getValue();
			ItemSlot is1 = c1.getValue();
			//if(c0.inv.get(c0.slot) != null && c1.inv.get(c1.slot) != null) return;
			if(is0 != null && (is1 == null || is0.equalsIgnoreCount(is1))){		
				if(is0.getCount() <= 1){
					swap(slot0, slot1);
					return;
				}
				if(is1 != null){
					if(is0.consume(1)) is1.count += 1;
				}else{
					if(is0.consume(1)){
						ItemSlot temp = is0.copy();
						is0.count = 1;
						c1.setValue(temp);
					}
				}
			}
		}
	}
	
	public enum InventoryManipulationMode{
		NONE, SEPARATE, MERGE_SWAP
	}
}
