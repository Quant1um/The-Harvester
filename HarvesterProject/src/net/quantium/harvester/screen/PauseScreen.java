package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.utilities.IOContainer;

public class PauseScreen extends IngameScreen {

	@Override
	protected void init() {
		super.init();
		this.mustUpdateGame = false;
		getContainer().add(new Button(MainScreen.buttonCenterX, 111, MainScreen.buttonSize, "exitsave", 4){

			@Override
			public void onClick(MouseState button) {
				Main.getInstance().getSession().save();
				Main.getInstance().resetSession();
				service.setScreen(new MainScreen());
			}
		});
		
		getContainer().add(new Button(MainScreen.buttonCenterX, 93, MainScreen.buttonSize, "backgame", 5, 1){

			@Override
			public void onClick(MouseState button) {
				service.setScreen(null);
			}
		});
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, MainScreen.buttonCenterX - 2, 80, MainScreen.buttonSize * Layer.BLOCK_SIZE + 4, 50, "pause");
		super.render(render);
	}

	@Override
	public void shown() {
		if(!IOContainer.isSaving) Main.getInstance().getSession().save();
	}
}
