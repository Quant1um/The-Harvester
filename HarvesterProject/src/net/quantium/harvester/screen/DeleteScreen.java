package net.quantium.harvester.screen;


import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.system.Session;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class DeleteScreen extends MenuScreen {

	private final int slot;
	
	public DeleteScreen(int slot){
		this.slot = slot;
	}
	
	@Override
	protected void init() {
		super.init();
		Button back = new Button(5, 5, 7, "back", 5, 1){

			@Override
			public void onClick(int button) {
				service.back();
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		};
		Button confirm = new Button((Main.getInstance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 130, 15, "confirm", 6){

			@Override
			public void onClick(int button) {
				Session.delete(slot);
				service.back(new GameScreen());
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		};
		container.add(back);
		container.add(confirm);
	}

	@Override
	public void render(Renderer render) {
		super.render(render);
		
		String text0 = "deleteconf";
		String text1 = Session.getName(slot) + "?";
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 100, FontSize.NORMAL, text0, 888, TextAlign.CENTER);
		render.get().drawText(Main.getInstance().getRenderWidth() / 2, 110, FontSize.NORMAL, text1, 888, TextAlign.CENTER);
		
		container.render(render);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean first) {
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

	@Override
	public void shown() {
		// TODO Auto-generated method stub
		
	}

}
