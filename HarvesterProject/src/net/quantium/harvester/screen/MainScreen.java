package net.quantium.harvester.screen;


import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public class MainScreen extends MenuScreen {

	public static final String[] names = new String[]{"startgame", "settings", "information", "exit"};
	
	final Screen[] screens = new Screen[]{ new GameScreen(), new SettingsScreen(), new InformationScreen() };
	
	@Override
	protected void init() {
		super.init();
		for(int i = 0; i < 3; i++){
			final Screen scr = screens[i];
			getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X, 80 + i * 20, MenuScreen.BUTTON_SIZE, names[i], i){

				@Override
				public void onClick(MouseState button) {
					service.setScreen(scr);
				}				
			});
		}
		
		getContainer().add(new Button(MenuScreen.BUTTON_CENTER_X, 140, MenuScreen.BUTTON_SIZE, names[3], 3){

			@Override
			public void onClick(MouseState button) {
				Main.instance().forceExit();
			}
		});
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawText(Main.instance().getRenderWidth() / 2 + MenuScreen.SHADOWS_OFFSET, 35 + MenuScreen.SHADOWS_OFFSET, FontSize.BIG, Main.NAME, 0, 0, 0, TextAlign.CENTER);
		render.get().drawText(Main.instance().getRenderWidth() / 2, 35, FontSize.BIG, Main.NAME, 474, 686, 353, TextAlign.CENTER);
	}
}
