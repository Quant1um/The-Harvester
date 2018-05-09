package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.data.Settings;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.resources.ResourceLoader;
import net.quantium.harvester.screen.components.BackButton;
import net.quantium.harvester.screen.components.Component;
import net.quantium.harvester.screen.components.Selector;
import net.quantium.harvester.screen.components.ToggleButton;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.Localization;
import net.quantium.harvester.text.TextAlign;

public class SettingsScreen extends MenuScreen {
	public static final String[] resolutionStrings;
	
	static{
		resolutionStrings = new String[Settings.resolutions.length];
		for(int i = 0; i < Settings.resolutions.length; i++)
			resolutionStrings[i] = Settings.resolutions[i][0] * Main.SCALE + "x" + Settings.resolutions[i][1] * Main.SCALE;
	}

	private Selector selector, language;
	
	private Settings settings(){
		return Main.instance().getSettings();
	}
	
	@Override
	public void init() {
		super.init();
		Component back = new BackButton(5, 5);
		Component shadows = new ToggleButton(MenuScreen.BUTTON_CENTER_X, 50, MenuScreen.BUTTON_SIZE, "shadows", settings().useShadows()){

			@Override
			public void onValueChanged(Boolean value) {
				settings().setShadows(value);
			}
			
		};
		
		selector = new Selector(MenuScreen.BUTTON_CENTER_X + 9 * 8, 70, 11, resolutionStrings);
		selector.setValue(Main.instance().getSettings().getResolutionId());
		String[] langs = new String[ResourceLoader.LocaleLoader.size()];
		for(int i = 0; i < langs.length; i++)
			langs[i] = ResourceLoader.LocaleLoader.get(i).getName();
		language = new Selector(MenuScreen.BUTTON_CENTER_X + 9 * 8, 90, 11, langs){
			
			@Override
			public void onValueChanged(Integer value){
				Localization.setCurrentLocaleID(value);
			}
		};
		
		language.setValue(settings().getLocalizationId());
		
		getContainer().add(back);
		getContainer().add(shadows);
		getContainer().add(selector);
		getContainer().add(language);
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().drawText(MainScreen.BUTTON_CENTER_X, 74, FontSize.NORMAL, "resolution", 888, TextAlign.LEFT);
		render.get().drawText(MainScreen.BUTTON_CENTER_X, 94, FontSize.NORMAL, "language", 888, TextAlign.LEFT);
	}

	@Override
	public void release() {
		Settings settings = settings();
		settings.setResolutionId(selector.getValue());
		settings.setLocalizationId(language.getValue());
		settings.save();
	}
}
