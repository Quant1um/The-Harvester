package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;
import net.quantium.harvester.system.text.TextAlign;

public class InputField extends Component{

	public String text = "";
	public int cursor = 0;
	private int tick = 0;
	private String placeholder;
	public InputField(int x, int y, int bw, String placeholder){
		this.x = x;
		this.y = y;
		this.w = bw * Layer.BLOCK_SIZE;
		this.h = 16;
		this.placeholder = placeholder;
	}
	
	@Override
	public void render(Renderer render, boolean focused) {
		render.get().renderPseudo3DRect(x, y, w / Layer.BLOCK_SIZE, 2, 222, 777, 444, 666, false);
		boolean renderCursor = focused && tick >= 25;
		if(text.length() > 0)
			render.get().drawText(x + 3, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, text, 888, TextAlign.LEFT, false);
		else
			render.get().drawText(x + 3, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, placeholder, 555, TextAlign.LEFT);
		if(renderCursor)
			render.get().drawUnderscore(x + Localization.getWidth(FontSize.NORMAL, text.substring(0, cursor), false) + 3, y + Layer.BLOCK_SIZE + 5, FontSize.NORMAL, cursor >= text.length() ? ' ' : text.charAt(cursor), 888);
	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first) {
		int xx = x - this.x;
		if(xx < 0){
			cursor = 0;
			return;
		}else if(xx >= Localization.getWidth(FontSize.NORMAL, text, false)){
			cursor = text.length();
		}else{
			int i = 0;
			int j = 0;
			while(i < xx){
				i += Localization.getWidth(FontSize.NORMAL, text.charAt(j));
				j++;
			}
			cursor = j - 1;
		}
	}

	@Override
	public boolean onKeyPress(Key key, boolean first) {
		if(first){
			if(key.code == KeyEvent.VK_LEFT){
				if(cursor > 0) cursor--;
				return true;
			}else if(key.code == KeyEvent.VK_RIGHT){
				if(cursor < text.length()) cursor++;
				return true;
			}
		}
		return false;
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		if(!backspace){
			if(cursor < w / Layer.BLOCK_SIZE && Localization.isPresent(FontSize.NORMAL, Character.toLowerCase(key))){
				text = text.substring(0, cursor) + Character.toLowerCase(key) + text.substring(cursor, text.length());
				cursor++;
			}	
		}else if(text.length() > 0 && cursor > 0){
			text = text.substring(0, cursor - 1) + text.substring(cursor, text.length());
			cursor--;
		}
	}

	@Override
	public void onMouseWheel(int ticks) {
		
	}

	@Override
	public void update(ScreenService scr) {
		tick++;
		if(tick >= 50) tick = 0;
	}

	@Override
	public void onSelect() {
		
	}
}
