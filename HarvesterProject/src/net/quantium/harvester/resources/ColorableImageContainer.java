package net.quantium.harvester.resources;

import java.awt.image.BufferedImage;

public class ColorableImageContainer extends ImageContainer {

	public ColorableImageContainer(BufferedImage img) {
		super(img);
	}
	
	@Override
	public short handle(int in){
		int blue = in & 255;
		int step = 255 / 6;
		blue /= step;
		if(blue > 5) blue = 5;
		return (short) blue;
	}
	
	public short get(int x, int y){
		if(x < 0 || y < 0 || x >= w || y >= h) return 0;
		return buffer[x + y * w];
	}
}
