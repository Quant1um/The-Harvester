package net.quantium.harvester.render;

import java.util.Stack;

import net.quantium.harvester.resources.ColorableImageContainer;
import net.quantium.harvester.resources.ImageContainer;
import net.quantium.harvester.resources.ResourceLoader;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Locale;
import net.quantium.harvester.system.text.Localization;
import net.quantium.harvester.system.text.TextAlign;

public class Layer {
	public static final int MIRRORFLAG_NONE = 0;
	public static final int MIRRORFLAG_HORIZONTAL = 1;
	public static final int MIRRORFLAG_VERTICAL = 2;
	public static final int MIRRORFLAG_BOTH = 3;
	public static final int BLOCK_SIZE = 8;
	public static final int BLOCKS_ROWSIZE = 16;
	public static final int MAX_STACK_SIZE = 128;
	
	private short data[];
	private final int w, h;
	public int clipX0, clipY0, clipX1, clipY1;	
	
	private Stack<Transform> transformStack = new Stack<Transform>();
	private Transform transform = Transform.IDENTITY;
	public Transform transform(){
		return transform;
	}
	
	public void push(){
		if(transformStack.size() >= MAX_STACK_SIZE) 
			throw new StackOverflowError();
		transformStack.push(new Transform(transform));
	}
	
	public void pop(){
		transform = transformStack.pop();
	}
	
	public Layer(int w, int h){
		this.w = w;
		this.h = h;
		this.data = new short[w * h];
		resetClip();
	}
	
	public short[] getData() {
		return data;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
	
	public void put(int x, int y, int c){
		if(c < 0) return;

		x -= transform.x;
		y -= transform.y;
		if(x < 0 || y < 0 || x >= w || y >= h) return;
		if(x < clipX0 || y < clipY0 || x >= clipX1 || y >= clipY1) return;
		
		if(transform.colorTint < 999)
			c = Color.multiply((short)c, transform.colorTint);
		
		if(c > 999)
			c = Color.lerp(data[x + y * w], (short)(c % 1000), c / 1000);
		
		data[x + y * w] = (short) c;
	}
	
	public short get(int x, int y){
		x -= transform.x;
		y -= transform.y;
		if(x < 0 || y < 0 || x >= w || y >= h) return 0;
		return data[x + y * w];
	}
	
	public void fill(int c){
		for(int i = 0; i < data.length; i++)
			data[i] = (short) c;
	}
	
	private void _drawLine(int x0, int y0, int x1, int y1, int c){
	     double deltax = x0 - x1;
	     double deltay = y0 - y1;
	     double delta = 0;
	     double deltaerr = Math.abs(deltay / deltax);           
	     int y = y0;
	     for (int x = x0; x < x1; x++){
	         put(x, y, c);
	         delta += deltaerr;
	         while (delta >= 0.5){
	         	put(x, y, c);
	             y += Math.signum(y1 - y0);
	             delta--;
	         }
	     }
	}

	public void drawLine(int x0, int y0, int x1, int y1, int c){
		boolean diff = Math.min(x0, x1) == x0;
		if(x0 == x1){
			fillRect(x0, Math.min(y0, y1), 1, Math.max(y0, y1) - Math.min(y0, y1), c);
			return;
		}
		_drawLine(Math.min(x0, x1), diff ? y0 : y1, Math.max(x0, x1), diff ? y1 : y0, c);
	}
	
	public void fillRect(int x, int y, int w, int h, int c){
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++)
				put(x + i, y + j, c);
	}
	
	public void drawRect(int x, int y, int w, int h, int c){
		for(int i = 0; i < w; i++){
			put(x + i, y, c);
			put(x + i, y + h - 1, c);
		}
			
		for(int j = 0; j < h; j++){
			put(x, y + j, c);
			put(x + w - 1, y + j, c);
		}	
	}
	
	//https://rosettacode.org/wiki/Bitmap/Midpoint_circle_algorithm#Java
	public void drawCircle(int centerX, int centerY, int radius, int c) {
		int d = (5 - radius * 4)/4;
		int x = 0;
		int y = radius;
 
		do {
			put(centerX + x, centerY + y, c);
			put(centerX + x, centerY - y, c);
			put(centerX - x, centerY + y, c);
			put(centerX - x, centerY - y, c);
			put(centerX + y, centerY + x, c);
			put(centerX + y, centerY - x, c);
			put(centerX - y, centerY + x, c);
			put(centerX - y, centerY - x, c);
			
			if (d < 0) {
				d += 2 * x + 1;
			} else {
				d += 2 * (x - y) + 1;
				y--;
			}
			x++;
		} while (x <= y);
 
	}
	
	public void fillCircle(int x, int y, int r, int c){
		for(int j= -r; j < r; j++)
		    for(int i= -r; i < r; i++)
		        if(i * i + j * j < r * r)
		            put(x + i, y + j, c);
	}
		
	public void setClip(int x0, int y0, int x1, int y1){
		this.clipX0 = x0;
		this.clipX1 = x1;
		this.clipY0 = y0;
		this.clipY1 = y1;
	}
	
	public void resetClip(){
		this.clipX0 = 0;
		this.clipX1 = w;
		this.clipY0 = 0;
		this.clipY1 = h;
	}
	
	public void draw(int x, int y, int tx, int ty, int w, int h, String sprId, int mirrorFlags){	
		boolean mirrorH = (mirrorFlags & MIRRORFLAG_HORIZONTAL) != 0;
		boolean mirrorV = (mirrorFlags & MIRRORFLAG_VERTICAL) != 0;
		
		ImageContainer cc = ResourceLoader.ImageLoader.get(sprId);
		if(cc == null) throw new RuntimeException("Render error: can\'t find sprite " + sprId);
		
		for(int i = 0; i < w * BLOCK_SIZE; i++){
			int xx = mirrorH ? w * BLOCK_SIZE - i - 1 : i;
			for(int j = 0; j < h * BLOCK_SIZE; j++){
				int yy = mirrorV ? h * BLOCK_SIZE - j - 1 : j;
				put(x + i, y + j, cc.get(tx * BLOCK_SIZE + xx, ty * BLOCK_SIZE + yy));
			}
		}
	}
	
	public void drawColored(int x, int y, int tx, int ty, int w, int h, ColorBundle colorBundle, String sprId, int mirrorFlags){
		boolean mirrorH = (mirrorFlags & MIRRORFLAG_HORIZONTAL) != 0;
		boolean mirrorV = (mirrorFlags & MIRRORFLAG_VERTICAL) != 0;
		
		ColorableImageContainer cc = (ColorableImageContainer) ResourceLoader.ImageLoader.get(sprId);
		if(cc == null) throw new RuntimeException("Render error: can\'t find sprite " + sprId);
		
		for(int i = 0; i < w * BLOCK_SIZE; i++){
			int xx = mirrorH ? w * BLOCK_SIZE - i - 1 : i;
			for(int j = 0; j < h * BLOCK_SIZE; j++){
				int yy = mirrorV ? h * BLOCK_SIZE - j - 1 : j;
				put(x + i, y + j, colorBundle.get(cc.get(tx * BLOCK_SIZE + xx, ty * BLOCK_SIZE + yy)));
			}
		}
	}

	public void drawText(int x, int y, FontSize font, String text, int color, TextAlign align){
		drawText(x, y, font, text, color, align, true);
	}
	
	public void drawText(int x, int y, FontSize font, String text, int color0, int color1, int color2, TextAlign align){
		drawText(x, y, font, text, color0, color1, color2, align, true);
	}
	
	public void drawText(int x, int y, FontSize font, String text, int color, TextAlign align, boolean localize){
		drawText(x, y, font, text, ColorBundle.get(-1, -1, -1, -1, -1, color), align, localize);
	}
	
	public void drawText(int x, int y, FontSize font, String text, int color0, int color1, int color2, TextAlign align, boolean localize){
		drawText(x, y, font, text, ColorBundle.get(-1, -1, -1, color2, color0, color1), align, localize);
	}
	
	public void drawText(int x, int y, FontSize font, String text, ColorBundle bundle, TextAlign align, boolean localize){	
		Locale loc = Localization.getCurrentLocale();
		text = text.toLowerCase();
		if(localize) 
			text = loc.getTranslation(text).toLowerCase();
		
		switch(align){
			case CENTER: x -= Localization.getWidth(font, text, localize) / 2; break;
			case RIGHT:  x -= Localization.getWidth(font, text, localize); break;
			default: break;
		}
		
		int bwidth = font == FontSize.BIG ? 2 : 1;
		int sumwidth = 0;
		for(int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			int idx = loc.getCharsetInfo(font.ordinal()) == null ? -1 : loc.getCharsetInfo(font.ordinal()).indexOf(c);
			
			int ix, iy;
			if(idx >= 0){
				switch(font){
					case SMALL: ix = idx % BLOCKS_ROWSIZE; iy = idx / BLOCKS_ROWSIZE; break;
					case BIG: ix = idx % (BLOCKS_ROWSIZE / 2) * 2; iy = 7 + idx / (BLOCKS_ROWSIZE / 2) * 2; break;
					default: ix = idx % BLOCKS_ROWSIZE; iy = 3 + idx / BLOCKS_ROWSIZE; break;
				}
				drawColored(x + sumwidth, y, ix, iy, bwidth, bwidth, bundle, loc.getSheet(), 0);
				sumwidth += loc.getWidth(font, c);
			}else{
				int idxx = Locale.DEFAULT.getCharsetInfo(font.ordinal()).indexOf(c);
				switch(font){
					case SMALL: ix = idxx % BLOCKS_ROWSIZE; iy = idxx / BLOCKS_ROWSIZE; break;
					case BIG: ix = idxx % (BLOCKS_ROWSIZE / 2) * 2; iy = 7 + idxx / (BLOCKS_ROWSIZE / 2) * 2; break;
					default: ix = idxx % BLOCKS_ROWSIZE; iy = 3 + idxx / BLOCKS_ROWSIZE; break;
				}
				if(idxx >= 0){
					drawColored(x + sumwidth, y, ix, iy, bwidth, bwidth, bundle, Locale.DEFAULT.getSheet(), 0);
					sumwidth += Locale.DEFAULT.getWidth(font, c);
				}
			}
		}
	}

	public void renderPseudo3DRect(int x, int y, int w, int h, int color, int lShade, int rShade, int nShade, boolean raised){
		ColorBundle bundle = ColorBundle.get(-1, rShade, color, nShade, lShade, -1);
		int offset = raised ? 0 : 3;
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++){
				if(i == 0 && j == 0)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 0, offset, 1, 1, bundle, "gui", 0);
				else if(i == w - 1 && j == h - 1)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 2, offset + 2, 1, 1, bundle, "gui", 0);
				else if(i == 0 && j == h - 1)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 0, offset + 2, 1, 1, bundle, "gui", 0);
				else if(i == w - 1 && j == 0)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 2, offset, 1, 1, bundle, "gui", 0);
				else if(i == 0)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 0, offset + 1, 1, 1, bundle, "gui", 0);
				else if(j == 0)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 1, offset, 1, 1, bundle, "gui", 0);
				else if(i == w - 1)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 2, offset + 1, 1, 1, bundle, "gui", 0);
				else if(j == h - 1)
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 1, offset + 2, 1, 1, bundle, "gui", 0);
				else
					drawColored(x + i * BLOCK_SIZE, y + j * BLOCK_SIZE, 1, 1, 1, 1, bundle, "gui", 0);
			}
	}
		
	public void drawUnderscore(int x, int y, FontSize font, char c, int color){
		int w = Localization.getWidth(font, c);
		for(int i = 0; i < w; i++)
			put(x + i, y, color);
	}
	
	@Override
	public String toString(){
		return "Layer{Width=" + w + ", Height=" + h + ", Transform=" + transform + ", Clip={" + clipX0 + ", " + clipY0 + ", " + clipX1 + ", " + clipY1 + "}}";
	}
}
