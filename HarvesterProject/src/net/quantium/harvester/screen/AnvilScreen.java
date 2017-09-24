package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.craft.Crafts;
import net.quantium.harvester.entity.BuildableInfo;
import net.quantium.harvester.entity.inventory.Inventory;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;

public class AnvilScreen extends InventoryScreen {

	public AnvilScreen(Inventory inventory, Inventory additionalInventory) {
		super(inventory, additionalInventory, BuildableInfo.Registry.get(3));
	}
	private int[] patternSel = new int[3];
	public void init(){
		super.init();
		container.addFirst(new Button(Main.getInstance().getRenderWidth() - 162, 200, 13, "create", 0){

			@Override
			public void onClick(int button) {
				if(additionalInventory.get(1) != null) return;
				ItemSlot cc = Crafts.tryCraftOnAnvil(additionalInventory.get(0), patternSel);
				if(cc == null) return;
				additionalInventory.set(1, cc);
			}
			
		});
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				final int l = j;
				final int k = i;
				container.addFirst(new Button(Main.getInstance().getRenderWidth() - 137 + i * 20, 120 + j * 20, 2, "", l + 21){

					@Override
					public void onClick(int button) {
						patternSel[k] = l;
					}
					
				});
			}
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawColored(Main.getInstance().getRenderWidth() - 120, 75, 3, 4, 4, 2, ColorBundle.get(-1, -1, -1, -1, -1, 888), "gui", 0);
		for(int i = 0; i < 3; i++){
			int patternSeld = patternSel[i];
			render.get().drawRect(Main.getInstance().getRenderWidth() - 137 + i * 20, 120 + patternSeld * 20, 17, 17, 373);
		}
	}

}
