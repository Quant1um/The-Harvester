package net.quantium.harvester.entity;

import net.quantium.harvester.input.InputService.Key;

public interface ISpectator {
	public int getXOffset();
	public int getYOffset();
	
	public void onMouseClick(int x, int y, int button);
	public void onKeyPress(Key key, boolean first);
	public void onMouseWheel(int ticks);
}
