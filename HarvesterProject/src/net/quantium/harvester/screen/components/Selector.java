package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class Selector extends Component implements IValueHolder<Integer>{
	
	private int value = 0;
	private final String[] names;
	private boolean lHover, rHover;
	private int lClick, rClick;
	
	public Selector(int x, int y, int bw, String[] names){
		this.x = x;
		this.y = y;
		this.w = bw * Layer.BLOCK_SIZE;
		this.h = 16;
		this.names = names;
	}
	
	@Override
	public void render(Renderer render, boolean focused) {
		ColorBundle bnd = ColorBundle.get(-1, -1, -1, -1, -1, 888);
		int lColor = lHover ? 666 : 555;
		int lOffset = lClick > 0 ? 1 : 0;
		int rColor = rHover ? 666 : 555;
		int rOffset = rClick > 0 ? 1 : 0;
		render.get().renderPseudo3DRect(x, y, 2, 2, lColor, 777, 444, 666, lClick <= 0);
		render.get().drawColored(x + lOffset, y + lOffset, 4, 2, 2, 2, bnd, "gui", 1);
		render.get().renderPseudo3DRect(x + 2 * Layer.BLOCK_SIZE, y, w / Layer.BLOCK_SIZE - 4, 2, 222, 777, 444, 666, false);
		render.get().drawText(x + 4 * Layer.BLOCK_SIZE + (w - 8 * Layer.BLOCK_SIZE) / 2, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, names[value], 888, TextAlign.CENTER);
		render.get().renderPseudo3DRect(x + w - 2 * Layer.BLOCK_SIZE, y, 2, 2, rColor, 777, 444, 666, rClick <= 0);
		render.get().drawColored(x + w - 2 * Layer.BLOCK_SIZE + rOffset, y + rOffset, 4, 2, 2, 2, bnd, "gui", 0);
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		if(x < this.x + 2 * Layer.BLOCK_SIZE)
			lClick = 5;
		else if(x > this.x + w - 2 * Layer.BLOCK_SIZE)
			rClick = 5;
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		if(first){
			if(key.code == KeyEvent.VK_LEFT){
				if(value > 0) value--;
				else value = names.length - 1;
				onValueChanged(value);
			}else if(key.code == KeyEvent.VK_RIGHT){
				if(value < names.length - 1) value++;
				else value = 0;
				onValueChanged(value);
			}
		}
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {

	}

	@Override
	public void onMouseWheel(int ticks) {
		value += ticks;
		if(value < 0) value = names.length - 1;
		if(value > names.length - 1) value = 0;
		onValueChanged(value);
	}

	@Override
	public void update() {
		lHover = Main.getInstance().getInputService().isMouseOverRectangle(x, y, 2 * Layer.BLOCK_SIZE, 2 * Layer.BLOCK_SIZE);
		rHover = Main.getInstance().getInputService().isMouseOverRectangle(x + w - 2 * Layer.BLOCK_SIZE, y, 2 * Layer.BLOCK_SIZE, 2 * Layer.BLOCK_SIZE);
		if(lClick > 0){
			lClick--;
			if(lClick <= 0){
				if(value > 0) value--;
				else value = names.length - 1;
				onValueChanged(value);
			}
		}
		if(rClick > 0){
			rClick--;
			if(rClick <= 0){
				if(value < names.length - 1) value++;
				else value = 0;
				onValueChanged(value);
			}
		}
	}

	public Integer getValue(){
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public void onValueChanged(Integer value){}
}
