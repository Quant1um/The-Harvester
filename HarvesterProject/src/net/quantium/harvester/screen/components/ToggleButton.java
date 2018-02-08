package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public abstract class ToggleButton extends Component {
	private boolean hover = false;
	private int click = 0;
	
	protected int color;
	protected String text;

	public boolean value;

	public ToggleButton(int x, int y, int bw, String text){
		this.x = x;
		this.y = y;
		this.w = bw * Layer.BLOCK_SIZE;
		this.h = 16;
		this.text = text;
		this.color = 999;
	}
	
	public ToggleButton(int x, int y, int bw, String text, boolean value){
		this(x, y, bw, text);
		this.value = value;
	}
	@Override
	public void render(Renderer render, boolean focused) {
		boolean clicked = isClicked();
		int color = hover ? 666 : 555;
		int offset = clicked ? 1 : 0;
		render.get().renderPseudo3DRect(x, y, w / Layer.BLOCK_SIZE, 2, color, 777, 444, 666, !clicked);
		render.get().drawText(x + w - 5 + offset, y + offset + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, text, this.color, TextAlign.RIGHT);
		render.get().drawColored(x + 3 + offset, y + offset + Layer.BLOCK_SIZE - 4, 3, 0, 1, 1, ColorBundle.get(-1, value ? 161 : 611, value ? 383 : 833, value ? 484 : 844, value ? 595 : 955, -1), "gui", 0);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first) {
		click = 5;
	}

	@Override
	public boolean onKeyPress(Key key, boolean first) {
		if(key.code == KeyEvent.VK_SPACE){
			click = 5;
			return true;
		}
		return false;
	}

	@Override
	public void onMouseWheel(int ticks) {

	}

	@Override
	public void update(ScreenService scr) {
		if(click > 0){
			click--;
			if(click <= 0){
				value = !value;
				onToggle(value);
			}
				
				
		}
		hover = scr.getInput().isMouseOverButton(x, y, w / Layer.BLOCK_SIZE);
	}

	public boolean isHover(){
		return hover;
	}
	
	public boolean isClicked(){
		return click > 0;
	}
	
	public abstract void onToggle(boolean value);
	
	@Override
	public void onSelect(){
		
	}
	
	@Override
	public void onKeyWrite(char c, boolean backspace, boolean submit){
		if(submit) click = 5;
	}
}
