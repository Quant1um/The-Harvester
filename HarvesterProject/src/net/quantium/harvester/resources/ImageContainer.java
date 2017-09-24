package net.quantium.harvester.resources;

import java.awt.image.BufferedImage;

import net.quantium.harvester.render.Color;

public class ImageContainer {
	public short[] buffer;
	public int w, h;
	
	public static final int TRANSPARENT_COLOR = 255 << 8;
	
	public ImageContainer(BufferedImage img){
		this.w = img.getWidth();
		this.h = img.getHeight();
		buffer = new short[w * h];
		int[] toHandle = img.getRGB(0, 0, w, h, null, 0, w);
		
		for(int i = 0; i < toHandle.length; i++)
			buffer[i] = handle(toHandle[i]);
			
	}
	
	public short handle(int in){
		if(in == java.awt.Color.GREEN.getRGB()) return -1;
		return Color.convertColor(in);
	}
	
	public short get(int x, int y){
		if(x < 0 || y < 0 || x >= w || y >= h) return 0;
		return buffer[x + y * w];
	}
}
