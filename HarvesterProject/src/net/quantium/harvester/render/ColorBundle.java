package net.quantium.harvester.render;

public class ColorBundle {
	private int[] colors = new int[6];
	
	public static ColorBundle get(int c0, int c1, int c2, int c3, int c4, int c5){
		ColorBundle bnd = new ColorBundle();
		bnd.colors[0] = c0;
		bnd.colors[1] = c1;
		bnd.colors[2] = c2;
		bnd.colors[3] = c3;
		bnd.colors[4] = c4;
		bnd.colors[5] = c5;
		return bnd;
	}
	
	public int get(int idx){
		return colors[idx];
	}
}
