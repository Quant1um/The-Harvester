package net.quantium.harvester.render;

import static net.quantium.harvester.Main.SCALE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
	public final int w, h;
	public final Layer layer;
	private BufferedImage image;
	private int[] data;
	
	public Renderer(int w, int h){
		this.w = w;
		this.h = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.layer = new Layer(w, h);
	}
	
	public Layer get(){
		return layer;
	}
	
	public void renderImage(Graphics g, int ww, int hh){
		int w = this.w * SCALE;
		int h = this.h * SCALE;
		int x = (ww - w) / 2;
		int y = (hh - h) / 2;
		g.drawImage(image, x, y, w, h, null);
	}
	
	public void update(){
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++){
				short c = get().get(i, j);
				int cc = Color.get(c);
				if(cc >= 0)
				data[i + j * w] = cc;
			}
	}
	
	@Override
	public String toString(){
		return "Renderer{Width=" + w + ", Height=" + h + ", Layer=" + layer + ", Buffer=" + image + "}";
	}
}
