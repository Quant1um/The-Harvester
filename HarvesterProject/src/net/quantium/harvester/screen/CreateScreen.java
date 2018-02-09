package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;
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
			public void onClick(MouseState button) {
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
			public void onClick(MouseState button) {
				if(name.text.length() > 0){
					Session session = new Session(slot, name.text, sizes[size.getValue()], true);
					session.save();
					Main.getInstance().setSession(session);
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
		
		getContainer().add(back);
		getContainer().add(confirm);
		getContainer().add(size);
		getContainer().add(name);
	}
}
