package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class InformationScreen extends MenuScreen {

private Button back;
	
	@Override
	public void init() {
		back = new Button(5, 5, 7, "back", 5, 1){

			@Override
			public void onClick(MouseState button) {
				service.back();
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		};
	}

	@Override
	public void update() {
		back.update();
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		
		final int xx = (Main.getInstance().getRenderWidth() - 55 * 6) / 2;
		final int yy = 40;
		render.get().drawText(xx, yy,      FontSize.NORMAL,  "infoline0", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 10, FontSize.NORMAL,  "infoline1", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 20, FontSize.NORMAL,  "infoline2", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 30, FontSize.NORMAL,  "infoline3", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 40, FontSize.NORMAL,  "infoline4", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 50, FontSize.NORMAL,  "infoline5", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 60, FontSize.NORMAL,  "infoline6", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 70, FontSize.NORMAL,  "infoline7", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 80, FontSize.NORMAL,  "infoline8", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 90, FontSize.NORMAL,  "infoline9", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 100, FontSize.NORMAL, "infoline10", 888, TextAlign.LEFT);
		
		render.get().drawText(xx, yy + 120, FontSize.NORMAL, "infoline11", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 130, FontSize.NORMAL, "infoline12", 888, TextAlign.LEFT);

		render.get().drawText(xx, yy + 150, FontSize.NORMAL, "infoline13", 888, TextAlign.LEFT);
		render.get().drawText(xx, yy + 160, FontSize.NORMAL, "infoline14", 888, TextAlign.LEFT);
		
		back.render(render, false);
	}

	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		back.onMouseClick(x, y, button, first);
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		back.onKeyPress(key, first);
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		back.onKeyWrite(key, backspace, submit);
	}

	@Override
	public void onMouseWheel(int ticks) {
		back.onMouseWheel(ticks);
	}

	@Override
	public void shown() {

	}
}
