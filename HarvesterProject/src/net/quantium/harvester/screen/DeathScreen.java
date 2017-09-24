package net.quantium.harvester.screen;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Container;

public class DeathScreen extends Screen {

	Container container;
	private PlayerEntity player;
	
	public DeathScreen(PlayerEntity player){
		this.player = player;
	}
	
	@Override
	protected void init() {
		this.mustRenderGame = true;
		container = new Container();
		container.add(new Button(MainScreen.buttonCenterX, 111, MainScreen.buttonSize, "exitsave", 4){

			@Override
			public void onClick(int button) {
				goToMainMenu();
			}
			
		});
		
		container.add(new Button(MainScreen.buttonCenterX, 93, MainScreen.buttonSize, "respawn", 5, 1){

			@Override
			public void onClick(int button) {
				player.respawn();
				service.setScreen(null);
			}
			
		});
	}

	private void goToMainMenu(){
		Main.getInstance().session.save();
		Main.getInstance().session = null;
		service.setScreen(new MainScreen());
	}
	
	@Override
	public void update() {
		container.update(service);
	}

	@Override
	public void render(Renderer render) {
		renderBox(render, MainScreen.buttonCenterX - 2, 80, MainScreen.buttonSize * Layer.BLOCK_SIZE + 4, 50, "death");
		container.render(render);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean first) {
		container.onMouseClick(x, y, button, first);
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		container.onKeyPress(key, first);
		if(key.code == KeyEvent.VK_ESCAPE && first)
			goToMainMenu();
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		container.onKeyWrite(key, backspace, submit);
	}

	@Override
	public void onMouseWheel(int ticks) {
		container.onMouseWheel(ticks);
	}

	@Override
	public void shown() {
		
	}
}
