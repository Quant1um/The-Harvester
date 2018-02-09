package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;
import net.quantium.harvester.timehook.TimeHook;
import net.quantium.harvester.timehook.TimeHookManager;

public class CrashScreen extends Screen{

	private Throwable throwable;
	public CrashScreen(Throwable throwable){
		this.throwable = throwable;
		this.mustRenderGame = false;
		this.mustUpdateGame = false;
		this.showCursor = false;
	}
	
	@Override
	protected void init() {
		TimeHookManager.register(new TimeHook(5d, false){
			@Override
			public void elapsed() {
				Main.getInstance().forceExit();
			}			
		});
	}

	@Override
	public void update() {}

	@Override 
	public void render(Renderer render) {
		render.get().fill(111);
		//render.get().drawText(0, 0, FontSize.NORMAL, "test", 999);
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 20, FontSize.NORMAL, "crashheader", 999, TextAlign.CENTER);
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 30, FontSize.NORMAL, "crashinfo", 888, TextAlign.CENTER);
		String throwableMessage = throwable.getClass().getName();
		if(throwable.getMessage() != null) throwableMessage += ": " + throwable.getMessage();
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 40, FontSize.NORMAL, throwableMessage, 888, TextAlign.CENTER, false);
	}
}
