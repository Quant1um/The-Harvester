package net.quantium.harvester.screen.components;

import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.ItemSlot.MergedItem;
import net.quantium.harvester.render.Renderer;

public class InventoryLayout extends AbstractContainer<InventorySlot>{
	
	protected final InventorySlot[] selectedSlots = new InventorySlot[2];
	protected int clicked = 0;
	protected InventoryManipulationMode mode = InventoryManipulationMode.NONE;

	public InventoryLayout(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first){
		if(button == MouseState.RELEASED){
			if(isTwoSlotsSelected()){
				switch(mode){
					case SEPARATE: separate(selectedSlots[0], selectedSlots[1]); break;
					case MERGE_SWAP: swap(selectedSlots[0], selectedSlots[1]); break;
					default: break;
				}
				mode = InventoryManipulationMode.NONE;
				resetSelectedSlots();
			}
			return;
		}
		mode = button == MouseState.RIGHT ? InventoryManipulationMode.SEPARATE :
							 				InventoryManipulationMode.MERGE_SWAP;

		super.onMouseClick(x, y, button, first);
		if(hasFocused()){
			selectedSlots[first ? 0 : 1] = getFocused();
		}
	}
	
	@Override
	public void render(Renderer render){
		super.render(render);
		if(isTwoSlotsSelected())
			render.get().drawLine(selectedSlots[0].x + 8, 
								  selectedSlots[0].y + 8, 
								  selectedSlots[1].x + 8, 
								  selectedSlots[1].y + 8, 
								  mode == InventoryManipulationMode.SEPARATE ? 939 : 993);
	}
	
	public void swap(InventorySlot first, InventorySlot second){
		if(first == second) return;
		if(isTwoSlotsSelected()){
			if(first.getValue() != null && first.getValue().equalsIgnoreCount(second.getValue())){
				MergedItem temp = ItemSlot.merge(first.getValue(), second.getValue());
				if(!temp.isMerged()){
					ItemSlot temp2 = first.getValue();
					first.setValue(second.getValue());
					second.setValue(temp2);
				}else{
					first.setValue(temp.getOther());
					second.setValue(temp.getSlot());
				}
			}else{
				ItemSlot temp2 = first.getValue();
				first.setValue(second.getValue());
				second.setValue(temp2);
			}
		}
	}
	
	public void separate(InventorySlot first, InventorySlot second){
		if(first == second) return;
		if(isTwoSlotsSelected()){			
			ItemSlot is0 = first.getValue();
			ItemSlot is1 = second.getValue();
			//if(c0.inv.get(c0.slot) != null && c1.inv.get(c1.slot) != null) return;
			if(is0 != null && (is1 == null || is0.equalsIgnoreCount(is1))){		
				if(is0.getCount() <= 1){
					swap(first, second);
					return;
				}
				if(is1 != null){
					if(is0.consume(1)) is1.setCount(is1.getCount() + 1);
				}else{
					if(is0.consume(1)){
						ItemSlot temp = is0.copy();
						is0.setCount(1);
						second.setValue(temp);
					}
				}
			}
		}
	}
	
	public boolean isTwoSlotsSelected(){
		for(int i = 0; i < selectedSlots.length; i++)
			if(selectedSlots[i] == null)
				return false;
		return true;
	}
	
	public void resetSelectedSlots(){
		for(int i = 0; i < selectedSlots.length; i++)
			selectedSlots[i] = null;
	}
	
	public enum InventoryManipulationMode{
		NONE, SEPARATE, MERGE_SWAP
	}
}
