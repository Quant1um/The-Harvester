package net.quantium.harvester.render;

import java.util.Arrays;

public class Color {
	private static final int[] indexedPalette;

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
	
	public static int get(short idx){
		if(idx < 0 || idx >= 1000) return -1;
		return indexedPalette[idx];
	}
		
	public static short lerp(short original, short target, int transition){
		int[] o = decompose(original, 3);
		int[] t = decompose(target, 3);
		
		return (short) ((o[0] + (t[0] - o[0]) * transition / 10) * 100 + 
			            (o[1] + (t[1] - o[1]) * transition / 10) * 10 +
			            (o[2] + (t[2] - o[2]) * transition / 10));
	}
	
	public static short multiply(short a, short b){
		int[] o = decompose(a, 3);
		int[] t = decompose(b, 3);
		
		return (short) ((o[0] * t[0] / 9) * 100 + 
			            (o[1] * t[1] / 9) * 10 +
			            (o[2] * t[2] / 9));
	}
	
	public static short darker(short color, int c){
		int[] o = decompose(color, 3);
		for(int i = 0; i < 3; i++)
			o[i] = (o[i] <= c) ? 0 : o[i] - c;
		return (short)(o[0] * 100 + o[1] * 10 + o[2]);
	}
	
	public static short lighter(short color, int c){
		int[] o = decompose(color, 3);
		for(int i = 0; i < 3; i++)
			o[i] = (o[i] >= 9 - c) ? 9 : o[i] + c;
		return (short)(o[0] * 100 + o[1] * 10 + o[2]);
	}
	
	public static void main(String...strings){
		System.out.println("" + Arrays.toString(decompose(12345678, 8)));
	}
	
	//http://forum.arduino.cc/index.php?topic=167414.0
	private static int[] fastDivMod10(int in){
		int x = (in >> 1); 
		int q = x;

		q = (q >> 8) + x;
		q = (q >> 8) + x;
		q = (q >> 8) + x;
		q = (q >> 8) + x + 1;

		x = q;
		q = (q >> 1) + x;
		q = (q >> 3) + x;
		q = (q >> 1) + x;

		int div = (q >> 3);
		return new int[] { div, in - (((div << 2) + div) << 1) };
	}
	
	private static int[] decompose(int in, int digits){
		int[] arr;
		int[] result = new int[digits];
		for(int i = 0; i < digits; i++){
			arr = fastDivMod10(in);
			result[digits - 1 - i] = arr[1];
			in = arr[0];
		}
		return result;
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
}
