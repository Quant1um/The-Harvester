package net.quantium.harvester.screen.components;

import com.sun.glass.events.KeyEvent;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.system.text.TextAlign;

public abstract class Button extends Component {
	private int click = 0;
	
	protected int color;
	protected int icon;
	protected String text;
	protected int iconFlag = 0;
	private MouseState button;
	
	public int getIconFlag() {
		return iconFlag;
	}

	public void setIconFlag(int iconflag) {
		this.iconFlag = iconflag;
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
		this.iconFlag = ic;
	}
	
	@Override
	public void render(Renderer render, boolean focused) {
		boolean clicked = isClicked();
		int color = isMouseOver() ? 666 : 555;
		int offset = clicked ? 1 : 0;
		render.get().renderPseudo3DRect(x, y, w / Layer.BLOCK_SIZE, 2, color, 777, 444, 666, !clicked);
		if(icon >= 0){
			int ix = icon % 5;
			int iy = icon / 5;
			render.get().drawColored(x + offset, y + offset, ix * 2 + 4, iy * 2, 2, 2, ColorBundle.get(-1, -1, -1, -1, -1, this.color), "gui", iconFlag);
		}
		render.get().drawText(x + w - 5 + offset, y + offset + Layer.BLOCK_SIZE - 4, FontSize.NORMAL, text, this.color, TextAlign.RIGHT);
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
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		click = 5;
		this.button = button;
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		if(key.code == KeyEvent.VK_SPACE){
			click = 5;
			button = MouseState.RELEASED;
		}
	}

	@Override
	public void onMouseWheel(int ticks) {}
	
	@Override
	public void update() {
		if(click > 0){
			click--;
			if(click <= 0)
				onClick(button);
		}
	}

	public boolean isClicked(){
		return click > 0;
	}
	
	public abstract void onClick(MouseState button);
	
	@Override
	public void onKeyWrite(char c, boolean backspace, boolean submit){
		if(submit) click = 5;
	}
}
