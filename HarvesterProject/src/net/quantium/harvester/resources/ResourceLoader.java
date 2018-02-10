package net.quantium.harvester.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import net.quantium.harvester.text.Locale;

public class ResourceLoader {
	public static class ImageLoader{

		private static Hashtable<String, ImageContainer> sprites = new Hashtable<String, ImageContainer>();
		
		static{
			sprites.put("font.png", new ColorableImageContainer(load("font.png")));
			sprites.put("sheet0", new ImageContainer(load("sheet0.png")));
			sprites.put("ambient", new ColorableImageContainer(load("ambient0.png")));
			sprites.put("gui", new ColorableImageContainer(load("gui.png")));
			sprites.put("water", new ImageContainer(load("water.png")));
			sprites.put("light", new ColorableImageContainer(load("mlight.png")));
		}
		
		public static ImageContainer get(String id){
			return sprites.get(id);
		}
		
		private static BufferedImage load(String name){
			try {
				BufferedImage img = ImageIO.read(ResourceLoader.class.getResource(name));
				return img;
			} catch (IOException e) {
				throw new RuntimeException("Sprite loading error: cannot load sprite " + name);
			}
		}
	}
	
	public static class LocaleLoader{
		private static ArrayList<Locale> locales = new ArrayList<Locale>();
		
		static{
			try {
				locales.add(Locale.DEFAULT);
				Locale rus = new Locale("russian.locale");
				locales.add(rus);
				ImageLoader.sprites.put(rus.getSheet(), new ColorableImageContainer(ImageLoader.load(rus.getSheet())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static Locale get(int i){
			if(i < 0 || i >= locales.size()) return Locale.DEFAULT;
			return locales.get(i);
		}

		public static int size() {
			return locales.size();
		}
	}
}
