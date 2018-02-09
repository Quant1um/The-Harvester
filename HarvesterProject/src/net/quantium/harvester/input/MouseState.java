package net.quantium.harvester.input;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public enum MouseState {
	UNDEFINED, LEFT, RIGHT, MIDDLE, RELEASED;
	
	public static MouseState fromMouseEvent(MouseEvent event){
		if(SwingUtilities.isLeftMouseButton(event)) return LEFT;
		if(SwingUtilities.isRightMouseButton(event)) return RIGHT;
		if(SwingUtilities.isMiddleMouseButton(event)) return MIDDLE;
		return UNDEFINED;
	}
}
