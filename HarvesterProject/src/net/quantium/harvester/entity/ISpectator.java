package net.quantium.harvester.entity;

import net.quantium.harvester.input.IInputListener;

public interface ISpectator extends IInputListener {
	int getXOffset();
	int getYOffset();
}
