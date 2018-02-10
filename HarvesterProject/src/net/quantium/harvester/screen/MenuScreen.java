package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public abstract class MenuScreen extends AbstractContainerScreen { 
	private static final ColorBundle LIGHT_COLOR_BUNDLE = ColorBundle.get(-1, 222, 333, 444, 555, 666);
	public static final int BUTTON_SIZE = 20;
	public static final int BUTTON_CENTER_X = (Main.getInstance().getRenderWidth() - BUTTON_SIZE * Layer.BLOCK_SIZE) / 2;
	public static final String ALPHA_TEXT = "infoalphaversion";
	public static final String AUTHOR_TEXT = "infocreatedby";
	public static final int SHADOWS_OFFSET = 4;
	
	
	@Override
	public void render(Renderer render) {
		render.get().fill(111);
		render.get().drawText(5, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, ALPHA_TEXT, 666, TextAlign.LEFT);
		render.get().drawText(Main.getInstance().getRenderWidth() - 5, Main.getInstance().getRenderHeight() - 9, FontSize.SMALL, AUTHOR_TEXT, 666, TextAlign.RIGHT);
		render.get().drawColored(0, 0, 0, 0, 16, 16, LIGHT_COLOR_BUNDLE, "light", 0);
		super.render(render);
	}
	
	@Override
	public void update(){
		super.update();
	}
}
