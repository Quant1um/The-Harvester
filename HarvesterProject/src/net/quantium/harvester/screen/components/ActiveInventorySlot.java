package net.quantium.harvester.screen.components;

import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;

public class ActiveInventorySlot extends InventorySlot {


	public ActiveInventorySlot(int x, int y, Inventory inv, int slot) {
		super(x, y, inv, slot);
	}

	@Override
	public void render(Renderer render, boolean focused){
		render.get().renderPseudo3DRect(x, y, 2, 2, hover ? 686 : 575, 464, 797, 686, true);
		ItemSlot.renderItemSlot(render, x, y, inv.get(slot));
	}
}
