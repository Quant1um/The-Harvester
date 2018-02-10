package net.quantium.harvester.screen.components;

import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Layer;
import net.quantium.harvester.render.Renderer;

public abstract class ToggleButton extends Button implements IValueHolder<Boolean>{
	private boolean value;

	public ToggleButton(int x, int y, int bw, String text){
		super(x, y, bw, text, -1);
	}
	
	public ToggleButton(int x, int y, int bw, String text, boolean value){
		this(x, y, bw, text);
		this.value = value;
	}
	
	@Override
	public void render(Renderer render, boolean focused) {
		super.render(render, focused);
		boolean clicked = isClicked();
		int offset = clicked ? 1 : 0;
		render.get().drawColored(x + 3 + offset, y + offset + Layer.BLOCK_SIZE - 4, 3, 0, 1, 1, ColorBundle.get(-1, value ? 161 : 611, value ? 383 : 833, value ? 484 : 844, value ? 595 : 955, -1), "gui", 0);
	}

	@Override
	public void onValueChanged(Boolean value){}
	
	@Override
	public void setValue(Boolean value){
		this.value = value;
	}
	
	@Override
	public Boolean getValue(){
		return value;
	}
	
	@Override
	public void onClick(MouseState button){
		this.value = !this.value;
		onValueChanged(this.value);
	}
}
