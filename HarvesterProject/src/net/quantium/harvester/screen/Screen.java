package net.quantium.harvester.screen;

import net.quantium.harvester.input.IInputListener;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public abstract class Screen implements IInputListener {
	protected ScreenService service;
	protected Screen parent;
	
	private boolean isInitialized = false;
	
	public boolean mustRenderGame = false;
	public boolean mustUpdateGame = false;
	public boolean showCursor = true;
	
	protected void init(){}
	public void shown(){}
	public abstract void update();
	public abstract void render(Renderer render);
	public void release(){}
	
	@Override public void onMouseClick(int x, int y, MouseState button, boolean first){}
	@Override public void onKeyPress(Key key, boolean first){}
	@Override public void onMouseWheel(int ticks){}
	public void onKeyWrite(char key, boolean backspace, boolean submit){}
	
	public void initialize(ScreenService service, Screen parent){
		if(isInitialized) return;
		init();
		isInitialized = true;
		this.service = service;
		this.parent = parent;
	}
	
	public static void renderBox(Renderer render, int x, int y, int w, int h, String name){
		render.get().fillRect(x + 2, y + 2, w, h, 5111);
		render.get().fillRect(x, y, w, h, 666);
		render.get().drawRect(x, y, w, h, 555);
		if(name != null) 
			render.get().drawText(x + 3, y + 3, FontSize.NORMAL, name, 888, TextAlign.LEFT);
	}
}
