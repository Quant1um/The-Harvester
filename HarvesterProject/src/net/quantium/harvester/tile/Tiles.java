package net.quantium.harvester.tile;

import net.quantium.harvester.tile.Tile.Registry;

public class Tiles {
	
	public static byte rock;
	public static byte water;
	public static byte sand;
	public static byte grass;
	
	public static byte oreCoal;
	public static byte oreIron;
	public static byte oreCopper;
	public static byte oreGold;
	public static byte orePlumbum;
	
	public static byte gemPurple;
	public static byte gemGreen;
	public static byte gemBlue;
	public static byte gemRed;
	
	public static byte littleStone;
	
	public static void register(){
		rock = Registry.register(new TileRock());
		water = Registry.register(new TileWater());
		sand = Registry.register(new TileSand());
		grass = Registry.register(new TileGrass());
		oreCoal = Registry.register(new TileOre(0));
		oreIron = Registry.register(new TileOre(1));
		oreCopper = Registry.register(new TileOre(2));
		oreGold = Registry.register(new TileOre(3));
		orePlumbum = Registry.register(new TileOre(4));
		gemPurple = Registry.register(new TileOre(5));
		gemGreen = Registry.register(new TileOre(6));
		gemBlue = Registry.register(new TileOre(7));
		gemRed = Registry.register(new TileOre(8));
		
		littleStone = Registry.register(new TileLittleStone());
	}
}
