package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Container;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;

public abstract class MenuScreen extends Screen {
	private static final ColorBundle LIGHT_COLOR_BUNDLE = ColorBundle.get(-1, 222, 333, 444, 555, 666);
	protected Container container;
	
	@Override
	protected void init(){
		container = new Container();
	}
	
	@Override
	public void render(Renderer render) {
		render.get().fill(111);
		
		render.get().drawText(5, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, MainScreen.alphaText, 666);
		render.get().drawText(Main.getInstance().getRenderWidth() - Localization.getWidth(FontSize.SMALL, MainScreen.authorText) - 4, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, MainScreen.authorText, 666);
		
		render.get().drawColored(0, 0, 0, 0, 16, 16, LIGHT_COLOR_BUNDLE, "light", 0);
	}
	
	@Override
	public void update(){
		container.update(service);
	}
}
