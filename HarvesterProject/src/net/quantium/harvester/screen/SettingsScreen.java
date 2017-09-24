package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.resources.ResourceLoader;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Selector;
import net.quantium.harvester.screen.components.ToggleButton;
import net.quantium.harvester.system.Settings;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;

public class SettingsScreen extends MenuScreen {

	
	public static final String[] resolutionStrings;
	
	static{
		resolutionStrings = new String[Settings.resolutions.length];
		for(int i = 0; i < Settings.resolutions.length; i++)
			resolutionStrings[i] = Settings.resolutions[i][0] * Main.SCALE + "x" + Settings.resolutions[i][1] * Main.SCALE;
	}

	private Selector selector, language;
	
	@Override
	public void init() {
		super.init();
		container.add(new Button(5, 5, 7, "back", 5, 1){

			@Override
			public void onClick(int button) {
				service.back();
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		});
		container.add(new ToggleButton(MainScreen.buttonCenterX, 50, 20, "shadows", Main.getInstance().useShadows()){

			@Override
			public void onToggle(boolean value) {
				Main.getInstance().settings.useShadows = value;
			}
			
		});
		
		selector = new Selector(MainScreen.buttonCenterX + 9 * 8, 70, 11, resolutionStrings);
		selector.setSelected(Main.getInstance().settings.resolution);
		container.add(selector);
		
		String[] langs = new String[ResourceLoader.LocaleLoader.size()];
		for(int i = 0; i < langs.length; i++)
			langs[i] = ResourceLoader.LocaleLoader.get(i).getName();
		language = new Selector(MainScreen.buttonCenterX + 9 * 8, 90, 11, langs){
			
			@Override
			public void onSelected(){
				Localization.setCurrentLocaleID(this.getSelected());
			}
			
		};
		language.setSelected(Main.getInstance().settings.localization);
		container.add(language);
	}

	@Override
	public void update() {
		container.update(service);
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		container.render(render);
		render.get().drawText(MainScreen.buttonCenterX, 74, FontSize.NORMAL, "resolution", 888);
		render.get().drawText(MainScreen.buttonCenterX, 94, FontSize.NORMAL, "language", 888);

	}

	@Override
	public void dispose() {
		Settings settings = Main.getInstance().settings;
		settings.resolution = selector.getSelected();
		settings.localization = language.getSelected();
		settings.save();
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
