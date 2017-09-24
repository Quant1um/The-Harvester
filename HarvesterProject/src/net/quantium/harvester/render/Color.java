package net.quantium.harvester.render;


public class Color {
	private static int[] indexedPalette;
	
	public static final int[] treeColors;
	
	static{ 
		indexedPalette = new int[1000];
		int step = 255 / 10;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				for(int k = 0; k < 10; k++){
					java.awt.Color c = new java.awt.Color(step * i, step * j, step * k);
					indexedPalette[i * 100 + j * 10 + k] = (c.getRed() + step / 2) << 16 | (c.getGreen() + step / 2) << 8 | (c.getBlue() + step / 2);
				}
			}
		}
	}
	
	static{
		treeColors = new int[256];
		for(int i = 0; i < 256; i++){
			treeColors[i] = (int) Math.min(Math.max(((1 - (i / 256f)) * 3), 0), 9) * 100 + (int) Math.min(Math.max(((i / 256f) * 8), 0), 9) * 10;
		}
	}
	
	public static int get(short idx){
		if(idx < 0 || idx >= 1000) return -1;
		return indexedPalette[idx];
	}
		
	public static short blend(short first, short second, float w0, float w1){
		float wSum = w1 + w0;
		if(wSum == 0) return 0;
		float a0 = w0 / wSum;
		float a1 = w1 / wSum;
		
		int r = Math.min(9, Math.max((int)(((first / 100) % 10) * a0 + ((second / 100) % 10) * a1), 0));
		int g = Math.min(9, Math.max((int)(((first / 10) % 10) 	* a0 + ((second / 10) % 10)  * a1), 0));
		int b = Math.min(9, Math.max((int)(((first / 1) % 10) 	* a0 + ((second / 1) % 10)   * a1), 0));
		return (short)(r * 100 + g * 10 + b);
	}
	
	public static short interpolate(short first, short second, float transition){
		float a0 = 1 - transition;
		float a1 = transition;
		
		int r = Math.min(9, Math.max((int)(((first / 100) % 10) * a0 + ((second / 100) % 10) * a1), 0));
		int g = Math.min(9, Math.max((int)(((first / 10) % 10) 	* a0 + ((second / 10) % 10)  * a1), 0));
		int b = Math.min(9, Math.max((int)(((first / 1) % 10) 	* a0 + ((second / 1) % 10)   * a1), 0));
		return (short)(r * 100 + g * 10 + b);
	}
	
	public static short darker(short color, int c){
		int r = color / 100 % 10 - c;
		int g = color / 10 % 10 - c;
		int b = color % 10 - c;
		if(r < 0) r = 0;
		if(g < 0) g = 0;
		if(b < 0) b = 0;
		return (short)(r * 100 + g * 10 + b);
	}
	
	public static short moreRed(short color, int c){
		int r = color / 100 % 10 + c;
		int g = color / 10 % 10 - c;
		int b = color % 10 - c;
		if(r > 9) r = 9;
		if(g < 0) g = 0;
		if(b < 0) b = 0;
		return (short)(r * 100 + g * 10 + b);
	}
	
	public static short lighter(short color, int c){
		int r = color / 100 % 10 + c;
		int g = color / 10 % 10 + c;
		int b = color % 10 + c;
		if(r > 9) r = 9;
		if(g > 9) g = 9;
		if(b > 9) b = 9;
		return (short)(r * 100 + g * 10 + b);
	}
	
	public static short inverse(short color){
		return (short) (999 - color);
	}
	
	public static short convertColor(int color){
		int step = 255 / 10;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color) & 255;
		r /= step;
		if(r > 9) r = 9;
		g /= step;
		if(g > 9) g = 9;
		b /= step;
		if(b > 9) b = 9;
		return (short) (r * 100 + g * 10 + b);
	}

	public static short moreYellow(short color, int c) {
		int r = color / 100 % 10 + c;
		int g = color / 10 % 10 + c;
		int b = color % 10 - c;
		if(r > 9) r = 9;
		if(g > 9) g = 9;
		if(b < 0) b = 0;
		return (short)(r * 100 + g * 10 + b);
	}
	
}
