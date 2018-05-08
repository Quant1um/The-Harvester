package net.quantium.harvester.world;

import java.util.List;

import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.tile.Tile;

public class PassingInfo {
	private final boolean passed;
	private final List<Entity> bump;
	private final Tile steppedOn;
	private final int tileX, tileY;
	
	public PassingInfo(boolean passed, List<Entity> bump, Tile tile, int tileX, int tileY) {
		this.passed = passed;
		this.bump = bump;
		this.steppedOn = tile;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	public boolean isPassed() {
		return passed;
	}
	
	public List<Entity> getBumped() {
		return bump;
	}
	
	public Tile getSteppedOn() {
		return steppedOn;
	}
	
	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
}
