package net.quantium.harvester.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import net.quantium.harvester.resources.ResourceLoader;

public class Locale {
	private String sheet = "font";
	private EnumMap<FontSize, String> charsetsInfo = new EnumMap<>(FontSize.class);
	private EnumMap<FontSize, HashMap<Character, Integer>> widthInfo = new EnumMap<>(FontSize.class);
	private String name = "unnamed";
	protected Hashtable<String, String> translation = new Hashtable<String, String>();
	
	public Locale(String infoFile) throws IOException, URISyntaxException, NullPointerException{
		for(FontSize font : FontSize.values())
			widthInfo.put(font, new HashMap<>());
		
		List<String> list = new ArrayList<String>();
		InputStreamReader in = new InputStreamReader(ResourceLoader.class.getResourceAsStream(infoFile),
		          StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(in);
		br.mark(1);
		if (br.read() != 0xFEFF)
		  br.reset();
		for (String line; (line = br.readLine()) != null; ) {
	        list.add(line);
	    }
		for(int i = 0; i < list.size(); i++){
			String s = list.get(i);
			s.replace(String.valueOf((char)0xFEFF), "");
			if(s.contains("=")){
				String[] splitted = s.split("=", 2);
				if(splitted.length > 1){
					String name = splitted[0];
					String value = splitted[1];
					if(name.equalsIgnoreCase("localeInfo.name"))
						this.name = value;
					else if(name.equalsIgnoreCase("localeInfo.sheet"))
						this.sheet = value;
					else if(name.equalsIgnoreCase("localeInfo.charsetSmall")){
						this.charsetsInfo.put(FontSize.SMALL, value);
					}else if(name.equalsIgnoreCase("localeInfo.charsetNormal")){
						this.charsetsInfo.put(FontSize.NORMAL, value);
					}else if(name.equalsIgnoreCase("localeInfo.charsetBig")){
						this.charsetsInfo.put(FontSize.BIG, value);
					}else if(name.toLowerCase().startsWith("localeinfo.widthsmall.")){
						char val = name.charAt(name.length() - 1);
						int width = Integer.parseInt(value);
						this.widthInfo.get(FontSize.SMALL).put(val, width);
					}else if(name.toLowerCase().startsWith("localeinfo.widthnormal.")){
						char val = name.charAt(name.length() - 1);
						int width = Integer.parseInt(value);
						this.widthInfo.get(FontSize.NORMAL).put(val, width);
					}else if(name.toLowerCase().startsWith("localeinfo.widthbig.")){
						char val = name.charAt(name.length() - 1);
						int width = Integer.parseInt(value);
						this.widthInfo.get(FontSize.BIG).put(val, width);
					}else{
						translation.put(name, value);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void dump() {
		StringBuilder builder = new StringBuilder();
		builder.append("=== LOCALE DUMP ").append(getName().toUpperCase()).append(" ===\n");
		for(FontSize font : FontSize.values()){
			builder.append(font.name() + ": \n");
			builder.append("\t").append("CHARSET: ").append(getCharsetInfo(font)).append("\n");
			builder.append("\t").append("WIDTHS: ").append("\n");
			for(Entry<Character, Integer> entry : this.widthInfo.get(font).entrySet())
				builder.append("\t\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}
		builder.append("=== END OF LOCALE DUMP ===\n");
		System.out.println(builder.toString());
	}

	public static final Locale DEFAULT;
	static{
		try {
			DEFAULT = new Locale("default.locale"){
				
				@Override
				public String getTranslation(String name){
					String s = this.translation.get(name);
					return s == null ? name : s;
				}
			};
		} catch(Exception e) {
			throw new RuntimeException("cannot load default.locale");
		}
	}

	public String getName() {
		return name;
	}

	public String getCharsetInfo(FontSize size) {
		return charsetsInfo.get(size);
	}

	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public String getTranslation(String name){
		String s = translation.get(name);
		return s == null ? Locale.DEFAULT.getTranslation(name) : s;
	}
	
	public int getWidth(FontSize font, char c){
		if(isPresent(font, c)){
			if(widthInfo.get(font).containsKey(c))
				return widthInfo.get(font).get(c);
			return font.defaultWidth;
		}
		return 0;
	}

	public boolean isPresent(FontSize font, char c) {
		if(charsetsInfo.get(font) == null) return false;
		return charsetsInfo.get(font).indexOf(Character.toLowerCase(c)) >= 0;
	}
	
	public int getWidth(FontSize font, String s){
		s = getTranslation(s.toLowerCase()).toLowerCase();
		int summary = 0;
		for(int i = 0; i < s.length(); i++) summary += getWidth(font, s.charAt(i));
		return summary;
	}
}
