package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.data.Session;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.screen.components.BackButton;
import net.quantium.harvester.screen.components.Button;
import net.quantium.harvester.screen.components.Component;
import net.quantium.harvester.screen.components.InputField;
import net.quantium.harvester.screen.components.Selector;

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
		Component back = new BackButton(5, 5);
		Component confirm = new Button((Main.instance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 130, 15, "create", 7){

			@Override
			public void onClick(MouseState button) {
				if(name.getValue().length() > 0){
					Session session = new Session(slot, name.getValue(), sizes[size.getValue()], true);
					session.save();
					Main.instance().setSession(session);
					service.setScreen(null);
				}
			}
		};
		
		this.name = new InputField((Main.instance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 90, 15, "name");
		this.size = new Selector((Main.instance().getRenderWidth() - 15 * Layer.BLOCK_SIZE) / 2, 110, 15, new String[]{"256x256", "512x512", "1024x1024"});
		
		getContainer().add(back);
		getContainer().add(confirm);
		getContainer().add(size);
		getContainer().add(name);
	}
}
