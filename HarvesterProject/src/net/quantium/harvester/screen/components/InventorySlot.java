package net.quantium.harvester.screen.components;

import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;

public class InventorySlot extends Component {

	Inventory inv;
	int slot;
	boolean hover;
	int mx, my;
	
	public InventorySlot(int x, int y, Inventory inv, int slot){
		this.inv = inv;
		this.slot = slot;
		this.x = x;
		this.y = y;
		this.w = 16;
		this.h = 16;
	}
	
	@Override
	public void render(Renderer render, boolean focused) {
		render.get().renderPseudo3DRect(x, y, 2, 2, hover ? 666 : 555, 444, 777, 666, true);
		ItemSlot.renderItemSlot(render, x, y, inv.get(slot));
	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first) {

	}

	@Override
	public boolean onKeyPress(Key key, boolean first) {
		return false;
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {

	}

	@Override
	public void onMouseWheel(int ticks) {

	}

	@Override
	public void update(ScreenService scr) {
		hover = scr.getInput().isMouseOverButton(x, y, 2);
		mx = scr.getInput().getMouseX();
		my = scr.getInput().getMouseY();
	}

	@Override
	public void onSelect() {

	}

}
