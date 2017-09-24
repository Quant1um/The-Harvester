package net.quantium.harvester.screen.components;

import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ItemSlot.MergedItem;
import net.quantium.harvester.render.Renderer;

public class InventoryLayout extends AbstractContainer<InventorySlot>{
	
	protected int selectedSlot = -1;
	protected int selectedSlot1 = -1;
	protected int clickd = 0;
	protected int clickmode = 0;
	
	protected Inventory inventory;
	
	public InventoryLayout(int x, int y, int w, int h, Inventory i){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.inventory = i;
	}
	
	@Override
	public void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first){
		if(button == 4){
			if(selectedSlot >= 0 && selectedSlot1 >= 0){
				if(clickmode == 1)
					separate(selectedSlot, selectedSlot1);
				else if(clickmode == 0)
					swap(selectedSlot, selectedSlot1);
				clickmode = -1;
				selectedSlot = -1;
				selectedSlot1 = -1;
			}
			return;
		}
		clickmode = button == 3 ? 1 : 0;
		boolean sNow = false;
		int c = getIndexOn(x, y);
		if(c >= 0){
			if(focused != c){
				focused = c;
				comps.get(c).onSelect();
				sNow = true;
			}
		}else{
			focused = -1;
		}
		if(focused >= 0 && focused < comps.size()){
			if(first) selectedSlot = focused;
			else selectedSlot1 = focused;
			comps.get(focused).onMouseClick(x, y, button, sNow, first);
		}
	}
	
	@Override
	public void render(Renderer render, boolean tfocused){
		super.render(render, tfocused);
		if(selectedSlot >= 0 && selectedSlot < this.comps.size() && selectedSlot1 >= 0 && selectedSlot1 < this.comps.size()){
			Component c0 = this.comps.get(selectedSlot);
			Component c1 = this.comps.get(selectedSlot1);
			render.get().renderLine(c0.x + 8, c0.y + 8, c1.x + 8, c1.y + 8, clickmode == 1 ? 939 : 993);
		}
	}
	
	public void swap(int slot0, int slot1){
		if(slot0 == slot1) return;
		if(slot0 >= 0 && slot0 < this.comps.size() && slot1 >= 0 && slot1 < this.comps.size()){
			InventorySlot c0 = this.comps.get(slot0);
			InventorySlot c1 = this.comps.get(slot1);
			if(c0.inv.get(c0.slot) != null && c0.inv.get(c0.slot).equalsIgnoreCount(c1.inv.get(c1.slot))){
				MergedItem temp = ItemSlot.merge(c0.inv.get(c0.slot), c1.inv.get(c1.slot));
				if(!temp.isMerged()){
					ItemSlot temp2 = c0.inv.get(c0.slot);
					c0.inv.set(c0.slot, c1.inv.get(c1.slot));
					c1.inv.set(c1.slot, temp2);
				}else{
					c0.inv.set(c0.slot, temp.getOther());
					c1.inv.set(c1.slot, temp.getSlot());
				}
			}else{
				ItemSlot temp = c0.inv.get(c0.slot);
				c0.inv.set(c0.slot, c1.inv.get(c1.slot));
				c1.inv.set(c1.slot, temp);
			}
		}
	}
	
	public void separate(int slot0, int slot1){
		if(slot0 == slot1) return;
		if(slot0 >= 0 && slot0 < this.comps.size() && slot1 >= 0 && slot1 < this.comps.size()){
			InventorySlot c0 = this.comps.get(slot0);
			InventorySlot c1 = this.comps.get(slot1);
			ItemSlot is0 = c0.inv.get(c0.slot);
			ItemSlot is1 = c1.inv.get(c1.slot);
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
						c1.inv.set(c1.slot, temp);
					}
				}
			}
		}
	}
}
