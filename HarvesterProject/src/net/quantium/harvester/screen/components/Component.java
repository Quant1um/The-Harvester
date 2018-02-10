package net.quantium.harvester.screen.components;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.IInputListener;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;

public abstract class Component implements IInputListener {
	protected int x, y, w, h;
	private boolean focused;

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
	
	public boolean isFocused(){
		return focused;
	}
	
	void setFocused(boolean focused){
		if(this.focused != focused){
			this.focused = focused;
			if(this.focused)
				onFocused();
			else
				onBlurred();
		}
	}
	
	public void render(Renderer render){}
	public void update(){}
	public void onFocused(){}
	public void onBlurred(){}
	public void onKeyWrite(char key, boolean backspace, boolean submit){}
	
	@Override public void onMouseClick(int x, int y, MouseState button, boolean first){}
	@Override public void onKeyPress(Key key, boolean first){}
	@Override public void onMouseWheel(int ticks){}

	public boolean isMouseOver(){
		return Main.getInstance().getInputService().isMouseOverRectangle(x, y, w, h);
	}
}
