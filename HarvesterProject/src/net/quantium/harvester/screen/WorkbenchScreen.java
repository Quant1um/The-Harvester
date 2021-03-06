package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.craft.Crafts;
import net.quantium.harvester.entity.buildable.BuildableInfo;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableType;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;

public class WorkbenchScreen extends InventoryScreen {

	public WorkbenchScreen(Inventory inventory, Inventory additionalInventory) {
		super(inventory, additionalInventory, BuildableInfo.Registry.get(BuildableType.WORKBENCH));
	}
	
	public void init(){
		super.init();
		getContainer().addFirst(new Button(Main.instance().getRenderWidth() - 162, 105, 13, "create", 0){

			@Override
			public void onClick(MouseState button) {
				if(additionalInventory.get(2) != null) return;
				ItemSlot cc = Crafts.tryCraftOnWorkbench(additionalInventory.get(0), additionalInventory.get(1));
				if(cc == null) return;
				additionalInventory.set(2, cc);
			}
		});
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawColored(Main.instance().getRenderWidth() - 120, 75, 3, 4, 4, 2, ColorBundle.get(-1, -1, -1, -1, -1, 888), "gui", 0);
	}
}
