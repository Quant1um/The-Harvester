package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.data.Session;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.BackButton;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public class GameScreen extends MenuScreen{
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		
		for(int i = 0; i < 7; i++){
			render.get().fillRect(MenuScreen.BUTTON_CENTER_X + MenuScreen.SHADOWS_OFFSET, 50 + i * 22 + MenuScreen.SHADOWS_OFFSET, MenuScreen.BUTTON_SIZE * Layer.BLOCK_SIZE, 18, 5000);
			render.get().fillRect(MenuScreen.BUTTON_CENTER_X, 50 + i * 22, MenuScreen.BUTTON_SIZE * Layer.BLOCK_SIZE, 18, 666);
			render.get().drawRect(MenuScreen.BUTTON_CENTER_X, 50 + i * 22, MenuScreen.BUTTON_SIZE * Layer.BLOCK_SIZE, 18, 555);
			String name = Session.getName(i);
			boolean haveSaving = name != null;
			render.get().drawText(MenuScreen.BUTTON_CENTER_X + 3, 50 + i * 22 + Layer.BLOCK_SIZE - 3, FontSize.NORMAL, haveSaving ? name : " - ", 888, TextAlign.LEFT);
		}
		
		getContainer().render(render);
	}


	@Override
	public void init() {
		getContainer().add(new BackButton(5, 5));
		
		for(int i = 0; i < 7; i++){
			String name = Session.getName(i);
			boolean haveSaving = name != null;
			final int j = i;
			if(haveSaving){
				getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X + (MenuScreen.BUTTON_SIZE - 2) * Layer.BLOCK_SIZE - 1, 51 + i * 22, 2, "", 6){

					@Override
					public void onClick(MouseState button) {
						service.setScreen(new DeleteScreen(j));
					}
				
				});
				
				getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X + (MenuScreen.BUTTON_SIZE - 4) * Layer.BLOCK_SIZE - 3, 51 + i * 22, 2, "", 7){

					@Override
					public void onClick(MouseState button) {
						Main.getInstance().setSession(Session.load(j));
						if(!Main.getInstance().hasSession()) return;
						service.setScreen(Main.getInstance().getSession().getPlayer().died ? 
										  new DeathScreen(Main.getInstance().getSession().getPlayer()) : null);
					}
				
				});
			}else{
				getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X + (MenuScreen.BUTTON_SIZE - 2) * Layer.BLOCK_SIZE - 1, 51 + i * 22, 2, "", 0){

					@Override
					public void onClick(MouseState button) {
						service.setScreen(new CreateScreen(j));
					}
				
				});
			}
		}
	}

}
