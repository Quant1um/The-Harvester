package net.quantium.harvester.item;

public class FuelItem extends AbstractItem {

	private int fuelValue;
	
	public FuelItem(String name, int iconX, int iconY, int maxSize, int power, int fuelValue) {
		super(name, iconX, iconY, maxSize, power);
		this.fuelValue = fuelValue;
	}

	public int getFuelValue() {
		return fuelValue;
	}
}
