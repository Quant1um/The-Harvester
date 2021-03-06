package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.craft.AnvilCraft.HitType;
import net.quantium.harvester.craft.Crafts;
import net.quantium.harvester.entity.buildable.BuildableInfo;
import net.quantium.harvester.entity.buildable.BuildableInfo.BuildableType;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;

public class AnvilScreen extends InventoryScreen {

	public AnvilScreen(Inventory inventory, Inventory additionalInventory) {
		super(inventory, additionalInventory, BuildableInfo.Registry.get(BuildableType.ANVIL));
	}
	private HitType[] pattern = new HitType[3];
	public void init(){
		super.init();
		getContainer().addFirst(new Button(Main.instance().getRenderWidth() - 162, 200, 13, "create", 0){

			@Override
			public void onClick(MouseState button) {
				if(additionalInventory.get(1) != null) return;
				ItemSlot cc = Crafts.tryCraftOnAnvil(additionalInventory.get(0), pattern);
				if(cc == null) return;
				additionalInventory.set(1, cc);
			}
			
		});
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				final int l = j;
				final HitType hit = HitType.values()[j];
				final int k = i;
				getContainer().addFirst(new Button(Main.instance().getRenderWidth() - 137 + i * 20, 120 + j * 20, 2, "", l + 21){

					@Override
					public void onClick(MouseState button) {
						pattern[k] = hit;
					}	
				});
			}
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawColored(Main.instance().getRenderWidth() - 120, 75, 3, 4, 4, 2, ColorBundle.get(-1, -1, -1, -1, -1, 888), "gui", 0);
		for(int i = 0; i < 3; i++)
			render.get().drawRect(Main.instance().getRenderWidth() - 137 + i * 20, 120 + pattern[i].offsetX * 20, 17, 17, 373);
	}
}
