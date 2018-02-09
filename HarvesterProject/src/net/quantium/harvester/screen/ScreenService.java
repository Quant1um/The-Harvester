package net.quantium.harvester.screen;

public class ScreenService {
	private Screen screen;
	
	public void setScreen(Screen screen){
		if(this.screen != null)
			this.screen.release();
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
			this.screen.release();
		if(screen != null){
			screen.service = this;
			screen.initialize(this, null);
			screen.shown();
		}
		this.screen = screen;
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
				this.screen.release();
				backScreen.parent = this.screen.parent == null ? null : this.screen.parent.parent;
				this.screen = backScreen;
			}
		}
	}
}
