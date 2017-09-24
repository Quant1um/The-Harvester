package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.InputField;
import net.quantium.harvester.screen.components.Selector;
import net.quantium.harvester.system.Session;

public class CreateScreen extends MenuScreen{
	private final int slot;
	
	private InputField name;
	private Selector size;
	
	private static final int[] sizes = {256, 512, 1024};
	
	public CreateScreen(int slot){
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
		Button confirm = new Button((Main.getInstance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 130, 15, "create", 7){

			@Override
			public void onClick(int button) {
				if(name.text.length() > 0){
					Session s = new Session(slot, name.text, sizes[size.getSelected()], true);
					s.save();
					Main.getInstance().session = s;
					service.setScreen(null);
				}
			}
			
			@Override
			public void render(Renderer render, boolean focused){
				render.get().fillRect(x + MainScreen.shadowOffset, y + MainScreen.shadowOffset, w, h, 000);
				super.render(render, focused);
			}
		};
		
		this.name = new InputField((Main.getInstance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 90, 15, "name");
		this.size = new Selector((Main.getInstance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 110, 15, new String[]{"256x256", "512x512", "1024x1024"});
		
		container.add(back);
		container.add(confirm);
		container.add(size);
		container.add(name);
	}

	@Override
	public void render(Renderer render){
		super.render(render);
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
