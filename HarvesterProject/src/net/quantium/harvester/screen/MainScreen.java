package net.quantium.harvester.screen;


import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class MainScreen extends MenuScreen {

	public static final String[] names = new String[]{"startgame", "settings", "information", "exit"};
	
	public static final int buttonSize = 20;
	public static final int buttonCenterX = (Main.getInstance().getRenderWidth() - buttonSize * Layer.BLOCK_SIZE) / 2;
	public static final int shadowOffset = 5;
	public static final String alphaText = "infoalphaversion";
	public static final String authorText = "infocreatedby";
	
	final Screen[] screens = new Screen[]{new GameScreen(), new SettingsScreen(), new InformationScreen()};
	
	@Override
	protected void init() {
		super.init();
		for(int i = 0; i < 3; i++){
			final Screen scr = screens[i];
			container.add(new Button(buttonCenterX, 80 + i * 20, buttonSize, names[i], i){

				@Override
				public void onClick(int button) {
					service.setScreen(scr);
				}
				
				@Override
				public void render(Renderer render, boolean focused){
					render.get().fillRect(x + shadowOffset, y + shadowOffset, w, h, 000);
					super.render(render, focused);
				}
				
			});
		}
		
		container.add(new Button(buttonCenterX, 140, buttonSize, names[3], 3){

			@Override
			public void onClick(int button) {
				Main.getInstance().forceExit();
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + shadowOffset, y + shadowOffset, w, h, 000);
				super.render(render, focused);
			}
			
		});
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		
		render.get().drawText(Main.getInstance().getRenderWidth() / 2 + shadowOffset, 35 + shadowOffset, FontSize.BIG, Main.NAME, 0, 0, 0, TextAlign.CENTER);
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 35, FontSize.BIG, Main.NAME, 474, 686, 353, TextAlign.CENTER);
		
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
	}

	@Override
	public void onMouseWheel(int ticks) {
		container.onMouseWheel(ticks);
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		container.onKeyWrite(key, backspace,  submit);
	}

	@Override
	public void shown() {
	}
}
