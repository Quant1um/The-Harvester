package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.Localization;

public abstract class Button extends Component {
	private boolean hover = false;
	private int click = 0;
	
	protected int color;
	protected int icon;
	protected String text;
	protected int iconflag = 0;
	private int button;
	
	public int getIconFlag() {
		return iconflag;
	}


	public void setIconFlag(int iconflag) {
		this.iconflag = iconflag;
	}


	public Button(int x, int y, int bw, String text, int icon){
		this.x = x;
		this.y = y;
		this.w = bw * Layer.BLOCK_SIZE;
		this.h = 16;
		this.text = text;
		this.icon = icon;
		this.color = 999;
	}
	
	public Button(int x, int y, int bw, String text, int icon, int ic){
		this(x, y, bw, text, icon);
		this.iconflag = ic;
	}
	
	
	@Override
	public void render(Renderer render, boolean focused) {
		boolean clicked = isClicked();
		int color = hover ? 666 : 555;
		int offset = clicked ? 1 : 0;
		render.get().renderPseudo3DRect(x, y, w / Layer.BLOCK_SIZE, 2, color, 777, 444, 666, !clicked);
		if(icon >= 0){
			int ix = icon % 5;
			int iy = icon / 5;
			render.get().drawColored(x + offset, y + offset, ix * 2 + 4, iy * 2, 2, 2, ColorBundle.get(-1, -1, -1, -1, -1, this.color), "gui", iconflag);
		}
		render.get().drawText(x + w - Localization.getWidth(FontSize.NORMAL, text) - 5 + offset, y + offset + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, text, this.color);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
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
		this.button = button;
	}

	@Override
	public boolean onKeyPress(Key key, boolean first) {
		if(key.code == KeyEvent.VK_SPACE){
			click = 5;
			button = 0;
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
			if(click <= 0)
				onClick(button);
		}
		hover = scr.getInput().isMouseOverButton(x, y, w / Layer.BLOCK_SIZE);
	}

	public boolean isHover(){
		return hover;
	}
	
	public boolean isClicked(){
		return click > 0;
	}
	
	public abstract void onClick(int button);
	
	@Override
	public void onSelect(){
		
	}
	
	@Override
	public void onKeyWrite(char c, boolean backspace, boolean submit){
		if(submit) click = 5;
	}
}
