package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.craft.Crafts;
import net.quantium.harvester.entity.buildable.BuildableEntity;
import net.quantium.harvester.entity.buildable.BuildableInfo;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableType;
import net.quantium.harvester.entity.buildable.FurnaceContainer;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;

public class FurnaceScreen extends InventoryScreen {

	private BuildableEntity entity;

	public FurnaceScreen(Inventory inventory, Inventory additionalInventory, BuildableEntity ent) {
		super(inventory, additionalInventory, BuildableInfo.Registry.get(BuildableType.FURNACE));
		this.entity = ent;
	}
	
	public void init(){
		super.init();
		getContainer().addFirst(new Button(Main.getInstance().getRenderWidth() - 162, 105, 13, "create", 0){

			@Override
			public void onClick(MouseState button) {
				if(additionalInventory.get(1) != null) return;
				ItemSlot cc = Crafts.tryCraftInFurnace(additionalInventory.get(0), entity);
				if(cc == null) return;
				additionalInventory.set(1, cc);
			}
			
		});
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawColored(Main.getInstance().getRenderWidth() - 122, 65, 3, 4, 4, 2, ColorBundle.get(-1, -1, -1, -1, -1, 888), "gui", 0);
		render.get().fillRect(Main.getInstance().getRenderWidth() - 160, 100, 100, 2, 444);
		render.get().fillRect(Main.getInstance().getRenderWidth() - 160, 100, ((FurnaceContainer)entity.container).fuel / 5, 2, 822);
	}
	
	@Override
	public void update(){
		super.update();
	}

}
