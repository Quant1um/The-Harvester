package net.quantium.harvester.text;

public enum FontSize {
	SMALL(4), NORMAL(6), BIG(17);
	
	public final int defaultWidth;
	private FontSize(int defaultWidth){
		this.defaultWidth = defaultWidth;
	}
}
