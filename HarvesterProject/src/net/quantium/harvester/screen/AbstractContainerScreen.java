package net.quantium.harvester.screen;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Container;

public abstract class AbstractContainerScreen extends Screen {

	private Container container = new Container();
	
	protected Container getContainer(){
		return container;
	}
	
	@Override
	public void render(Renderer render) {
		container.render(render);
	}
	
	@Override
	public void update(){
		container.update();
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		container.onMouseClick(x, y, button, first);
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		container.onKeyPress(key, first);
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		container.onKeyWrite(key, backspace, submit);
	}

	@Override
	public void onMouseWheel(int ticks) {
		container.onMouseWheel(ticks);
	}
}
