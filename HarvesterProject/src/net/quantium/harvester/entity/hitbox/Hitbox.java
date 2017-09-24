package net.quantium.harvester.entity.hitbox;

import java.awt.Rectangle;
import java.io.Serializable;

import net.quantium.harvester.world.World;

public class Hitbox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int xOffset, yOffset, width, height;

	public Hitbox(int width, int height, int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
	}

	public Hitbox(int i, int j) {
		this(i, j, 0, 0);
	}

	public int getOffsetX() {
		return xOffset;
	}

	public void setOffsetX(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getOffsetY() {
		return yOffset;
	}

	public void setOffsetY(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static boolean intersects(int x0, int y0, Hitbox h0, int x1, int y1, Hitbox h1, Rectangle out){
		return World.isRectanglesIntersects(x0 + h0.getOffsetX(), y0 + h0.getOffsetY(), h0.getWidth(), h0.getHeight(), x1 + h1.getOffsetX(), y1 + h1.getOffsetY(), h1.getWidth(), h1.getHeight(), out);
	}
	
	public static boolean contains(int x0, int y0, Hitbox h0, int x1, int y1){
		int xx = x0 + h0.getOffsetX();
		int yy = y0 + h0.getOffsetY();
		int xw = h0.getWidth() + xx;
		int yh = h0.getHeight() + yy;
		return x1 >= xx && x1 <= xw && y1 >= yy && y1 <= yh;
	}
	
	public int getCenterX(int x){
		return width / 2 + x + xOffset;
	}
	
	public int getCenterY(int y){
		return height / 2 + y + yOffset;
	}
}
