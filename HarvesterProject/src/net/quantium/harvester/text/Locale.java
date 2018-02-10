package net.quantium.harvester.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.quantium.harvester.resources.ResourceLoader;

public class Locale {
	private String sheet = "font";
	private String[] charsetsInfo = new String[3];
	private int[][] widthInfo = new int[3][0];
	private String name = "unnamed";
	protected Hashtable<String, String> translation = new Hashtable<String, String>();
	
	public Locale(String infoFile) throws IOException, URISyntaxException, NullPointerException{
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
					else if(name.equalsIgnoreCase("localeInfo.charsetLow")){
						this.charsetsInfo[0] = value;
						this.widthInfo[0] = new int[value.length()];
						for(int j = 0; j < value.length(); j++){
							this.widthInfo[0][j] = 4; 
						}
					}else if(name.equalsIgnoreCase("localeInfo.charsetMedium")){
						this.charsetsInfo[1] = value;
						this.widthInfo[1] = new int[value.length()];
						for(int j = 0; j < value.length(); j++){
							this.widthInfo[1][j] = 6; 
						}
					}else if(name.equalsIgnoreCase("localeInfo.charsetHigh")){
						this.charsetsInfo[2] = value;
						this.widthInfo[2] = new int[value.length()];
						for(int j = 0; j < value.length(); j++){
							this.widthInfo[2][j] = 17; 
						}
					}else if(name.toLowerCase().startsWith("localeinfo.widthlow.")){
						char val = name.charAt(name.length() - 1);
						int idx = this.charsetsInfo[0].indexOf(val);
						if(idx >= 0)
							try{
								this.widthInfo[0][idx] = Integer.parseInt(value);
							}catch(NumberFormatException e){
								e.printStackTrace();
							}
					}else if(name.toLowerCase().startsWith("localeinfo.widthmedium.")){
						char val = name.charAt(name.length() - 1);
						int idx = this.charsetsInfo[1].indexOf(val);
						if(idx >= 0)
							try{
								this.widthInfo[1][idx] = Integer.parseInt(value);
							}catch(NumberFormatException e){
								e.printStackTrace();
							}
					}else if(name.toLowerCase().startsWith("localeinfo.widthhigh.")){
						char val = name.charAt(name.length() - 1);
						int idx = this.charsetsInfo[2].indexOf(val);
						if(idx >= 0)
							try{
								this.widthInfo[2][idx] = Integer.parseInt(value);
							}catch(NumberFormatException e){
								e.printStackTrace();
							}
					}else{
						translation.put(name, value);
					}
				}
			}
		}
		System.out.println("Loaded locale: " + name);
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

	public String getCharsetInfo(int i) {
		return charsetsInfo[i];
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
			int idx = charsetsInfo[font.ordinal()].indexOf(Character.toLowerCase(c));
			return widthInfo[font.ordinal()][idx];
		}
		return 0;
	}

	public boolean isPresent(FontSize font, char c) {
		if(charsetsInfo[font.ordinal()] == null) return false;
		return charsetsInfo[font.ordinal()].indexOf(Character.toLowerCase(c)) >= 0;
	}
	
	public int getWidth(FontSize font, String s){
		s = getTranslation(s.toLowerCase()).toLowerCase();
		int summary = 0;
		for(int i = 0; i < s.length(); i++) summary += getWidth(font, s.charAt(i));
		return summary;
	}
}
