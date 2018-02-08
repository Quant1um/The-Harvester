package net.quantium.harvester.screen;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public abstract class Screen {
	protected ScreenService service;
	protected Screen parent;
	
	private boolean isInitialized = false;
	
	public boolean mustRenderGame = false;
	public boolean mustUpdateGame = false;
	public boolean showCursor = true;
	
	protected abstract void init();
	
	public abstract void update();
	
	public abstract void render(Renderer render);
	
	public abstract void dispose();
	
	public abstract void onMouseClick(int x, int y, int button, boolean first);
	
	public abstract void onKeyPress(Key key, boolean first);
	
	public abstract void onKeyWrite(char key, boolean backspace, boolean submit);
	
	public abstract void onMouseWheel(int ticks);
	
	public void initialize(ScreenService service, Screen parent){
		if(isInitialized) return;
		init();
		isInitialized = true;
		this.service = service;
		this.parent = parent;
	}
	
	public abstract void shown();
	
	public static void renderBox(Renderer render, int x, int y, int w, int h, String name){
		render.get().fillRect(x + 2, y + 2, w, h, 111);
		render.get().fillRect(x, y, w, h, 666);
		render.get().drawRect(x, y, w, h, 555);
		if(name != null) render.get().drawText(x + 3, y + 3, FontSize.NORMAL, name, 888, TextAlign.LEFT);
	}
}
