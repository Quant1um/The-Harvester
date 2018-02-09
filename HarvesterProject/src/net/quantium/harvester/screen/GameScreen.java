package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.system.Session;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class GameScreen extends MenuScreen{
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		
		for(int i = 0; i < 7; i++){
			render.get().fillRect(MainScreen.buttonCenterX + MainScreen.shadowOffset, 50 + i * 22 + MainScreen.shadowOffset, MainScreen.buttonSize * Layer.BLOCK_SIZE, 18, 000);
			render.get().fillRect(MainScreen.buttonCenterX, 50 + i * 22, MainScreen.buttonSize * Layer.BLOCK_SIZE, 18, 666);
			render.get().drawRect(MainScreen.buttonCenterX, 50 + i * 22, MainScreen.buttonSize * Layer.BLOCK_SIZE, 18, 555);
			String name = Session.getName(i);
			boolean haveSaving = name != null;
			render.get().drawText(MainScreen.buttonCenterX + 3, 50 + i * 22 + Layer.BLOCK_SIZE - 3, FontSize.NORMAL, haveSaving ? name : " - ", 888, TextAlign.LEFT);
		}
		
		getContainer().render(render);
	}


	@Override
	public void init() {
		getContainer().add(new Button(5, 5, 7, "back", 5, 1){
			@Override
			public void onClick(MouseState button) {
				service.back();
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		});
		
		for(int i = 0; i < 7; i++){
			String name = Session.getName(i);
			boolean haveSaving = name != null;
			final int j = i;
			if(haveSaving){
				getContainer().add(new Button(MainScreen.buttonCenterX + (MainScreen.buttonSize - 2) * Layer.BLOCK_SIZE - 1, 51 + i * 22, 2, "", 6){

					@Override
					public void onClick(MouseState button) {
						service.setScreen(new DeleteScreen(j));
					}
				
				});
				
				getContainer().add(new Button(MainScreen.buttonCenterX + (MainScreen.buttonSize - 4) * Layer.BLOCK_SIZE - 3, 51 + i * 22, 2, "", 7){

					@Override
					public void onClick(MouseState button) {
						Main.getInstance().setSession(Session.load(j));
						if(!Main.getInstance().hasSession()) return;
						service.setScreen(Main.getInstance().getSession().getPlayer().died ? 
										  new DeathScreen(Main.getInstance().getSession().getPlayer()) : null);
					}
				
				});
			}else{
				getContainer().add(new Button(MainScreen.buttonCenterX + (MainScreen.buttonSize - 2) * Layer.BLOCK_SIZE - 1, 51 + i * 22, 2, "", 0){

					@Override
					public void onClick(MouseState button) {
						service.setScreen(new CreateScreen(j));
					}
				
				});
			}
		}
	}

}
