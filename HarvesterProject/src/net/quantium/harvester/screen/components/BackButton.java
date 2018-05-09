package net.quantium.harvester.screen.components;

import net.quantium.harvester.Main;
import net.quantium.harvester.input.MouseState;

public class BackButton extends Button {
	public BackButton(int x, int y) {
		super(x, y, 7, "back", 5, 1);
	}

	@Override
	public void onClick(MouseState button) {
		Main.instance().getScreenService().back();
	}
}
