package net.quantium.harvester.screen;


import net.quantium.harvester.Main;
import net.quantium.harvester.data.Session;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.BackButton;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Component;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public class DeleteScreen extends MenuScreen {

	private final int slot;
	
	public DeleteScreen(int slot){
		this.slot = slot;
	}
	
	@Override
	protected void init() {
		super.init();
		Component back = new BackButton(5, 5);
		Component confirm = new Button((Main.instance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 130, 15, "confirm", 6){

			@Override
			public void onClick(MouseState button) {
				Session.delete(slot);
				service.back();
			}
		};
		getContainer().add(back);
		getContainer().add(confirm);
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		
		String text0 = "deleteconf";
		String text1 = Session.getName(slot) + "?";
		render.get().drawText(Main.instance().getRenderWidth() / 2, 100, FontSize.NORMAL, text0, 888, TextAlign.CENTER);
		render.get().drawText(Main.instance().getRenderWidth() / 2, 110, FontSize.NORMAL, text1, 888, TextAlign.CENTER, false);
		
		getContainer().render(render);
	}
}
