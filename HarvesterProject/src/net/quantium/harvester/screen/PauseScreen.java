package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Component;
import net.quantium.harvester.utilities.IOContainer;

public class PauseScreen extends IngameScreen {

	@Override
	protected void init() {
		super.init();
		this.mustUpdateGame = false;
		
		Component back = new Button(MenuScreen.BUTTON_CENTER_X, 93, MenuScreen.BUTTON_SIZE, "backgame", 5, 1){

			@Override
			public void onClick(MouseState button) {
				service.setScreen(null);
			}
		};
		
		Component save = new Button(MenuScreen.BUTTON_CENTER_X, 111, MenuScreen.BUTTON_SIZE, "exitsave", 4){

			@Override
			public void onClick(MouseState button) {
				Main.getInstance().getSession().save();
				Main.getInstance().resetSession();
				service.setScreen(new MainScreen());
			}
		};
		
		getContainer().add(back);
		getContainer().add(save);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, MenuScreen.BUTTON_CENTER_X - 2, 80, MenuScreen.BUTTON_SIZE * Layer.BLOCK_SIZE + 4, 50, "pause");
		super.render(render);
	}

	@Override
	public void shown() {
		if(!IOContainer.isSaving) Main.getInstance().getSession().save();
	}
}
