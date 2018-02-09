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
		getContainer().add(new Button(MainScreen.buttonCenterX, 111, MainScreen.buttonSize, "exitsave", 4){

			@Override
			public void onClick(MouseState button) {
				goToMainMenu();
			}
		});
		
		getContainer().add(new Button(MainScreen.buttonCenterX, 93, MainScreen.buttonSize, "respawn", 5, 1){

			@Override
			public void onClick(MouseState button) {
				player.respawn();
				service.setScreen(null);
			}
		});
	}

	private void goToMainMenu(){
		Main.getInstance().getSession().save();
		Main.getInstance().resetSession();
		service.setScreen(new MainScreen());
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, MainScreen.buttonCenterX - 2, 80, MainScreen.buttonSize * Layer.BLOCK_SIZE + 4, 50, "death");
		super.render(render);
	}
}
