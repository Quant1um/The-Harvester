package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;

public class CrashScreen extends Screen{

	private Throwable throwable;
	public CrashScreen(Throwable throwable){
		this.throwable = throwable;
		this.mustRenderGame = false;
		this.mustUpdateGame = false;
		this.showCursor = true;
	}
	
	@Override
	protected void init() {}

	@Override
	public void update() {}

	@Override 
	public void render(Renderer render) {
		render.get().fill(111);
		//render.get().drawText(0, 0, FontSize.NORMAL, "test", 999);
		render.get().drawText((Main.getInstance().getRenderWidth() - Localization.getWidth(FontSize.NORMAL, "crashheader")) / 2, 20, FontSize.NORMAL, "crashheader", 999);
		render.get().drawText((Main.getInstance().getRenderWidth() - Localization.getWidth(FontSize.NORMAL, "crashinfo")) / 2, 30, FontSize.NORMAL, "crashinfo", 888);
		String throwableMessage = throwable.getClass().getName();
		if(throwable.getMessage() != null) throwableMessage += ": " + throwable.getMessage();
		render.get().drawText((Main.getInstance().getRenderWidth() - Localization.getWidthUnlocalized(FontSize.NORMAL, throwableMessage)) / 2, 40, FontSize.NORMAL, throwableMessage, 888, false);
	}

	@Override
	public void dispose() {}
	
	@Override
	public void onMouseClick(int x, int y, int button, boolean first) {

	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {

	}

	@Override
	public void onMouseWheel(int ticks) {

	}

	@Override
	public void shown() {

	}
	
}
