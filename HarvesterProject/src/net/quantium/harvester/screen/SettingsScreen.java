package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.resources.ResourceLoader;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Selector;
import net.quantium.harvester.screen.components.ToggleButton;
import net.quantium.harvester.system.Settings;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;
import net.quantium.harvester.system.text.TextAlign;

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
		getContainer().add(new ToggleButton(MainScreen.buttonCenterX, 50, 20, "shadows", Main.getInstance().useShadows()){

			@Override
			public void onValueChanged(Boolean value) {
				Main.getInstance().getSettings().useShadows = value;
			}
			
		});
		
		selector = new Selector(MainScreen.buttonCenterX + 9 * 8, 70, 11, resolutionStrings);
		selector.setValue(Main.getInstance().getSettings().resolution);
		getContainer().add(selector);
		
		String[] langs = new String[ResourceLoader.LocaleLoader.size()];
		for(int i = 0; i < langs.length; i++)
			langs[i] = ResourceLoader.LocaleLoader.get(i).getName();
		language = new Selector(MainScreen.buttonCenterX + 9 * 8, 90, 11, langs){
			
			@Override
			public void onValueChanged(Integer value){
				Localization.setCurrentLocaleID(value);
			}
			
		};
		language.setValue(Main.getInstance().getSettings().localization);
		getContainer().add(language);
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawText(MainScreen.buttonCenterX, 74, FontSize.NORMAL, "resolution", 888, TextAlign.LEFT);
		render.get().drawText(MainScreen.buttonCenterX, 94, FontSize.NORMAL, "language", 888, TextAlign.LEFT);
	}

	@Override
	public void release() {
		Settings settings = Main.getInstance().getSettings();
		settings.resolution = selector.getValue();
		settings.localization = language.getValue();
		settings.save();
	}

	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		getContainer().onMouseClick(x, y, button, first);
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		getContainer().onKeyPress(key, first);
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		getContainer().onKeyWrite(key, backspace, submit);
	}

	@Override
	public void onMouseWheel(int ticks) {
		getContainer().onMouseWheel(ticks);
	}

	@Override
	public void shown() {
		
	}

}
