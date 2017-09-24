package net.quantium.harvester.screen;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService;

public class ScreenService {
	private Screen screen;
	private Main main;
	
	public ScreenService(Main main){
		this.main = main;
	}
	
	public void setScreen(Screen screen){
		if(this.screen != null)
			this.screen.dispose();
		if(screen != null){
			screen.initialize(this, this.screen);
			screen.shown();
		}
		this.screen = screen;
	}
	
	public Screen current(){
		return screen;
	}
	
	public boolean isScreenActive(){
		return screen != null;
	}
	
	public void back(){
		back(this.screen == null ? null : this.screen.parent);
	}

	public void setScreenUnreferenced(MainScreen screen) {
		if(this.screen != null)
			this.screen.dispose();
		if(screen != null){
			screen.service = this;
			screen.initialize(this, null);
			screen.shown();
		}
		this.screen = screen;
	}
	
	public InputService getInput(){
		return main.getInputService();
	}
	
	@Override
	public String toString(){
		return "ScreenService{" + screen + "}";
	}

	public void back(Screen backScreen) {
		if(backScreen == null){
			setScreenUnreferenced(null);
		}else{
			if(this.screen != null){
				this.screen.dispose();
				backScreen.parent = this.screen.parent == null ? null : this.screen.parent.parent;
				this.screen = backScreen;
			}
		}
	}
}
