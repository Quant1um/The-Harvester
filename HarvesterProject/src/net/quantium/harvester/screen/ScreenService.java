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
		setScreen(this.screen == null ? null : this.screen.parent);
	}

	@Override
	public String toString(){
		return "ScreenService{" + screen + "}";
	}
}
