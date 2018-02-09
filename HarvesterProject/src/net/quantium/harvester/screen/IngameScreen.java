package net.quantium.harvester.screen;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;

public abstract class IngameScreen extends AbstractContainerScreen {

	@Override
	protected void init(){
		this.mustRenderGame = true;
		this.mustUpdateGame = true;
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
	}
	
	@Override
	public void onKeyPress(Key key, boolean first) {
		super.onKeyPress(key, first);
		if(key.code == KeyEvent.VK_ESCAPE && first)
			service.back();
	}
}
