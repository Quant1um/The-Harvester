package net.quantium.harvester.screen.components;

import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;

public class InventorySlot extends Component implements IValueHolder<ItemSlot> {
	private Inventory inventory;
	private int slot;
	
	public InventorySlot(int x, int y, Inventory inv, int slot){
		this.inventory = inv;
		this.slot = slot;
		this.x = x;
		this.y = y;
		this.w = 16;
		this.h = 16;
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().renderPseudo3DRect(x, y, 2, 2, isMouseOver() ? 666 : 555, 444, 777, 666, true);
		ItemSlot.renderItemSlot(render, x, y, getValue());
	}

	@Override
	public void update() {}
	
	public int getSlotId(){
		return slot;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	@Override
	public ItemSlot getValue() {
		return inventory.get(slot);
	}

	@Override
	public void setValue(ItemSlot value) {
		inventory.set(slot, value);
	}

	@Override
	public void onValueChanged(ItemSlot value) {}
}
