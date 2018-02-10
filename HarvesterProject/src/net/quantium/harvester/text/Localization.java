package net.quantium.harvester.text;

import net.quantium.harvester.resources.ResourceLoader;

public class Localization {
	private static int currentLocale = 0;

	
	public static int getCurrentLocaleID() {
		return currentLocale;
	}
	
	public static Locale getCurrentLocale() {
		return ResourceLoader.LocaleLoader.get(currentLocale);
	}

	public static int getWidth(FontSize font, char c){
			if(getCurrentLocale().isPresent(font, c))
				return getCurrentLocale().getWidth(font, c);
			else if(Locale.DEFAULT.isPresent(font, c))
				return Locale.DEFAULT.getWidth(font, c);
			else
				return 0;
	}

	public static boolean isPresent(FontSize font, char c) {
		return Locale.DEFAULT.isPresent(font, c) || getCurrentLocale().isPresent(font, c);
	}
	
	public static int getWidth(FontSize font, String s, boolean localize){
		s = (localize ? getTranslation(s.toLowerCase()) : s).toLowerCase();
		int summary = 0;
		for(int i = 0; i < s.length(); i++) summary += getWidth(font, s.charAt(i));
		return summary;
	}
	
	public static String getTranslation(String name){
		return getCurrentLocale().getTranslation(name);
	}

	public static void setCurrentLocaleID(int selected) {
		if(selected < 0 || selected >= ResourceLoader.LocaleLoader.size()) return;
		currentLocale = selected;
	}
}
