package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;

public class DeathScreen extends IngameScreen {

	private PlayerEntity player;	
	public DeathScreen(PlayerEntity player){
		this.player = player;
	}
	
	@Override
	protected void init() {
		super.init();
		getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X, 111, MenuScreen.BUTTON_SIZE, "exitsave", 4){

			@Override
			public void onClick(MouseState button) {
				goToMainMenu();
			}
		});
		
		getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X, 93, MenuScreen.BUTTON_SIZE, "respawn", 5, 1){

			@Override
			public void onClick(MouseState button) {
				player.respawn();
				service.setScreen(null);
			}
		});
	}

	private void goToMainMenu(){
		Main.instance().getSession().save();
		Main.instance().resetSession();
		service.setScreen(new MainScreen());
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, MenuScreen.BUTTON_CENTER_X - 2, 80, MenuScreen.BUTTON_SIZE * Layer.BLOCK_SIZE + 4, 50, "death");
		super.render(render);
	}
}
