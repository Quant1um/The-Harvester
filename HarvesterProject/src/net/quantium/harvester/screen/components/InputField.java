package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.input.TextModifiers;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.Localization;
import net.quantium.harvester.text.TextAlign;

public class InputField extends Component implements IValueHolder<String>{
	protected String value = "";
	protected int cursor = 0;
	protected String placeholder;
	
	public InputField(int x, int y, int bw, String placeholder){
		this.x = x;
		this.y = y;
		this.w = bw * Layer.BLOCK_SIZE;
		this.h = 16;
		this.placeholder = placeholder;
	}
	
	@Override
	public void render(Renderer render) {
		super.render(render);
		render.get().renderPseudo3DRect(x, y, w / Layer.BLOCK_SIZE, 2, 222, 777, 444, 666, false);
		boolean renderCursor = isFocused() && (Main.instance().getCounter() & 32) == 0;
		if(value.length() > 0)
			render.get().drawText(x + 3, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, value, 888, TextAlign.LEFT, false);
		else
			render.get().drawText(x + 3, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, placeholder, 555, TextAlign.LEFT);
		if(renderCursor)
			render.get().drawUnderscore(x + Localization.getWidth(FontSize.NORMAL, value.substring(0, cursor), false) + 3, y + Layer.BLOCK_SIZE + 5, FontSize.NORMAL, cursor >= value.length() ? ' ' : value.charAt(cursor), 888);
	}

	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		int xx = x - this.x;
		if(xx < 0){
			cursor = 0;
			return;
		}else if(xx >= Localization.getWidth(FontSize.NORMAL, value, false)){
			cursor = value.length();
		}else{
			int i = 0;
			int j = 0;
			while(i < xx){
				i += Localization.getWidth(FontSize.NORMAL, value.charAt(j));
				j++;
			}
			cursor = j - 1;
		}
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		if(first){
			if(key.code == KeyEvent.VK_LEFT){
				if(cursor > 0) cursor--;
			}else if(key.code == KeyEvent.VK_RIGHT){
				if(cursor < value.length()) cursor++;
			}
		}
	}

	@Override
	public void onTextInput(char key, TextModifiers mod) {
		if(mod != TextModifiers.BACKSPACE){
			if(cursor < w / Layer.BLOCK_SIZE && Localization.isPresent(FontSize.NORMAL, Character.toLowerCase(key))){
				value = value.substring(0, cursor) + Character.toLowerCase(key) + value.substring(cursor, value.length());
				cursor++;
			}	
		}else if(value.length() > 0 && cursor > 0){
			value = value.substring(0, cursor - 1) + value.substring(cursor, value.length());
			cursor--;
		}
	}

	@Override
	public void update() {}
	
	@Override
	public String getValue(){
		return this.value;
	}
	
	@Override
	public void setValue(String value){
		this.value = value;
	}
	
	@Override
	public void onValueChanged(String value){}
}
