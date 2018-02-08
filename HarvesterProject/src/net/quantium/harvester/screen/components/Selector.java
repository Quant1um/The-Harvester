package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public class Selector extends Component {
	
	private int selected = 0;
	public int getSelected() {
		return selected;
	}

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
		render.get().drawText(x + 4 * Layer.BLOCK_SIZE + (w - 8 * Layer.BLOCK_SIZE) / 2, y + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, names[selected], 888, TextAlign.CENTER);
		render.get().renderPseudo3DRect(x + w - 2 * Layer.BLOCK_SIZE, y, 2, 2, rColor, 777, 444, 666, rClick <= 0);
		render.get().drawColored(x + w - 2 * Layer.BLOCK_SIZE + rOffset, y + rOffset, 4, 2, 2, 2, bnd, "gui", 0);
	}
	
	@Override
	public void onMouseClick(int x, int y, int button, boolean selectedNow, boolean first) {
		if(x < this.x + 2 * Layer.BLOCK_SIZE)
			lClick = 5;
		else if(x > this.x + w - 2 * Layer.BLOCK_SIZE)
			rClick = 5;
	}

	@Override
	public boolean onKeyPress(Key key, boolean first) {
		if(first){
			if(key.code == KeyEvent.VK_LEFT){
				if(selected > 0) selected--;
				else selected = names.length - 1;
				onSelected();
				return true;
			}else if(key.code == KeyEvent.VK_RIGHT){
				if(selected < names.length - 1) selected++;
				else selected = 0;
				onSelected();
				return true;
			}
		}
		return false;
	}

	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {

	}

	@Override
	public void onMouseWheel(int ticks) {
		selected += ticks;
		if(selected < 0) selected = names.length - 1;
		if(selected > names.length - 1) selected = 0;
		onSelected();
	}

	@Override
	public void update(ScreenService scr) {
		lHover = scr.getInput().isMouseOverButton(x, y, 2);
		rHover = scr.getInput().isMouseOverButton(x + w - 2 * Layer.BLOCK_SIZE, y, 2);
		if(lClick > 0){
			lClick--;
			if(lClick <= 0){
				if(selected > 0) selected--;
				else selected = names.length - 1;
				onSelected();
			}
		}
		if(rClick > 0){
			rClick--;
			if(rClick <= 0){
				if(selected < names.length - 1) selected++;
				else selected = 0;
				onSelected();
			}
		}
	}

	@Override
	public void onSelect() {

	}

	public void setSelected(int d) {
		this.selected = d;
	}
	
	public void onSelected(){
		
	}
}
