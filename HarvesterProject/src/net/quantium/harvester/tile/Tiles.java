package net.quantium.harvester.tile;

public class Tiles {
	
	public static final Tile rock = new TileRock();
	public static final Tile water = new TileWater();
	public static final Tile sand = new TileSand();
	public static final Tile grass = new TileGrass();
	
	public static final Tile oreCoal = new TileOre(0);
	public static final Tile oreIron = new TileOre(1);
	public static final Tile oreCopper = new TileOre(2);
	public static final Tile oreGold = new TileOre(3);
	public static final Tile orePlumbum = new TileOre(4);
	
	public static final Tile gemPurple = new TileOre(5);
	public static final Tile gemGreen = new TileOre(6);
	public static final Tile gemBlue = new TileOre(7);
	public static final Tile gemRed = new TileOre(8);
	
	public static final Tile littleStone = new TileLittleStone();
}
