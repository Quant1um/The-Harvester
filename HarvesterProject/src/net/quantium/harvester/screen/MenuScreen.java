package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public abstract class MenuScreen extends AbstractContainerScreen { 
	private static final ColorBundle LIGHT_COLOR_BUNDLE = ColorBundle.get(-1, 222, 333, 444, 555, 666);
	
	@Override
	public void render(Renderer render) {
		render.get().fill(111);
		render.get().drawText(5, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, MainScreen.alphaText, 666, TextAlign.LEFT);
		render.get().drawText(Main.getInstance().getRenderWidth() - 5, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, MainScreen.authorText, 666, TextAlign.RIGHT);
		render.get().drawColored(0, 0, 0, 0, 16, 16, LIGHT_COLOR_BUNDLE, "light", 0);
		super.render(render);
	}
	
	@Override
	public void update(){
		super.update();
	}
}
