package net.quantium.harvester.input;

import net.quantium.harvester.input.InputService.Key;

public interface IInputListener {
	void onMouseClick(int x, int y, MouseState button, boolean first);
	void onKeyPress(Key key, boolean first);
	void onMouseWheel(int ticks); 
}
