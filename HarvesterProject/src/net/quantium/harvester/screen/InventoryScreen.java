package net.quantium.harvester.screen;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableBehavior;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.ActiveInventorySlot;
import net.quantium.harvester.screen.components.InventoryLayout;
import net.quantium.harvester.screen.components.InventorySlot;

public class InventoryScreen extends IngameScreen {

	protected Inventory inventory, additionalInventory;
	protected BuildableBehavior behavior;
	protected boolean isDual;
	
	public static final int SLOTS_PER_ROW = 4;
	public static final int EMPTYSPACE = (120 - SLOTS_PER_ROW * 24) / SLOTS_PER_ROW - 2;
	
	public InventoryScreen(Inventory inventory, Inventory additionalInventory, BuildableBehavior behavior){
		this.isDual = additionalInventory != null && behavior != null;
		this.additionalInventory = additionalInventory;
		this.inventory = inventory;
		this.behavior = behavior;
	}
	
	@Override
	protected void init() {
		super.init();
		InventoryLayout first = new InventoryLayout(0, 0, Main.instance().getRenderWidth(), Main.instance().getRenderHeight());
		first.add(new ActiveInventorySlot(50 + EMPTYSPACE, 50 + EMPTYSPACE + 12, inventory, 0));
		for(int i = 1; i < inventory.size(); i++){
			int x = 50 + EMPTYSPACE + (i % SLOTS_PER_ROW) * EMPTYSPACE * 8;
			int y = 50 + EMPTYSPACE + (i / SLOTS_PER_ROW) * EMPTYSPACE * 8 + 12;
			first.add(new InventorySlot(x, y, inventory, i));
		}
		if(isDual){
			InventorySlot[] slots = behavior.getLayout(additionalInventory);
			if(slots != null){
				for(int i = 0; i < slots.length; i++){
					slots[i].setX(slots[i].getX() + Main.instance().getRenderWidth() - 170);
					slots[i].setY(slots[i].getY() + 50);
					first.add(slots[i]);
				}
			}
		}
		getContainer().add(first);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, 50, 50, 120, (inventory.size() / SLOTS_PER_ROW) * (EMPTYSPACE + 24) + 26, "inventory");
		if(isDual){
			renderBox(render, Main.instance().getRenderWidth() - 170, 50, 120, behavior.boxHeight, behavior.name);
		}
		super.render(render);
	}
	
	@Override
	public void onKeyPress(Key key, boolean first){
		super.onKeyPress(key, first);
		if(key.code == KeyEvent.VK_E && first)
			service.back();
	}
}
