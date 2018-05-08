package net.quantium.harvester.text;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.quantium.harvester.resources.ResourceLoader;

public class Localization {
	private static int currentLocale = 0;
	
	private static final EnumMap<FontSize, Map<Character, Locale>> charLocaleMap = new EnumMap<>(FontSize.class);
	static{
		for(FontSize font : FontSize.values())
			charLocaleMap.put(font, new HashMap<>());
	}
	
	public static int getCurrentLocaleID() {
		return currentLocale;
	}
	
	public static Locale getCurrentLocale() {
		return ResourceLoader.LocaleLoader.get(currentLocale);
	}

	public static Locale getLocaleWhereCharacterPresents(FontSize size, char c){
		if(charLocaleMap.get(size).containsKey(c)) 
			return charLocaleMap.get(size).get(c);
		
		Locale loc = null;
		Iterator<Locale> it = ResourceLoader.LocaleLoader.iterator();
		while(it.hasNext())
		{
			Locale _loc = it.next();
			if(_loc.isPresent(size, c)){
				loc = _loc;
				break;
			}
		}
		
		charLocaleMap.get(size).put(c, loc);
		return loc;
	}
	
	public static int getWidth(FontSize font, char c){
		if(!isPresent(font, c)) return 0;
		return getLocaleWhereCharacterPresents(font, c).getWidth(font, c);
	}

	public static boolean isPresent(FontSize font, char c) {
		return getLocaleWhereCharacterPresents(font, c) != null;	
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
