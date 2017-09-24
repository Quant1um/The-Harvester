package net.quantium.harvester.screen.components;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;

public abstract class Component {
	protected int x, y, w, h;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}
	
	public abstract void render(Renderer render, boolean focused);
	
	public abstract void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first);
	
	public abstract boolean onKeyPress(Key key, boolean first);
	
	public abstract void onKeyWrite(char key, boolean backspace, boolean submit);
	
	public abstract void onMouseWheel(int ticks);
	
	public abstract void update(ScreenService scr);
	
	public abstract void onSelect();
	
}
