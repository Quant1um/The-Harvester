package net.quantium.harvester.world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.quantium.harvester.entity.TreeEntity;
import net.quantium.harvester.tile.Tile;
import net.quantium.harvester.world.SimplexNoise.SimplexOctave;

public class WorldGenerator {
	public static void generate(World world, int seed){
		SimplexOctave.update(seed);
		Random rnd = new Random(seed);
		SimplexNoise noise = new SimplexNoise(8, 1, 0.5);
		for(int x = 0; x < world.w; x++)
			for(int y = 0; y < world.h; y++){
				world.height	  [x + y * world.w] = (byte) (noise.octavedNoise(x +       0, y +       0, world.w, world.h) * 127);
				world.temperature [x + y * world.w] = (byte) (noise.octavedNoise(x + world.w, y - world.h, world.w, world.h) * 127);
				world.moisture	  [x + y * world.w] = (byte) (noise.octavedNoise(x - world.w, y + world.h, world.w, world.h) * 127);
				world.map		  [x + y * world.w] = get(world.height[x + y * world.w], world.temperature[x + y * world.w], world.moisture[x + y * world.w], rnd).getId();
				
				if(world.height[x + y * world.w] > 3 && world.height[x + y * world.w] < 35)
					if((world.moisture[x + y * world.w] > 20 && world.height[x + y * world.w] > 15 && rnd.nextInt(15) == 0) || rnd.nextInt(100) == 0){
						TreeEntity tree = new TreeEntity(rnd, world.temperature[x + y * world.w], (int) (((world.moisture[x + y * world.w] + 128) / 256f * 3)));
						tree.x = x * 16;
						tree.y = y * 16;
						world.addEntity(tree);
					}
				if(world.map[x + y * world.w] == Tile.littleStone.getId()){
					world.meta[x + y * world.w] = (short) rnd.nextInt(32);
				}
			}
	}
	
	private static Tile get(byte b, byte c, byte d, Random rnd) {
		if(b < -7) return Tile.water;
		if(b < 2) return Tile.sand;
		if(b < 39){
			if(rnd.nextInt(70) == 0) return Tile.littleStone;
			return Tile.grass;
		}
		if(d > 20 && rnd.nextInt(150) == 0) return Tile.gemPurple;
		if(d < 25 && rnd.nextInt(200) == 0) return Tile.gemGreen;
		if(c > 20 && rnd.nextInt(220) == 0) return Tile.gemBlue;
		if(c < 25 && rnd.nextInt(280) == 0) return Tile.gemRed;
		if(rnd.nextInt(40) == 0) return Tile.oreCoal;
		if(rnd.nextInt(60) == 0) return Tile.oreCopper;
		if(rnd.nextInt(140) == 0) return Tile.oreGold;
		if(rnd.nextInt(90) == 0) return Tile.oreIron;
		if(rnd.nextInt(100) == 0) return Tile.orePlumbum;
		return Tile.rock;
	}
	
	public static void main(String[] args){
		int w = 256;
		int h = 256;
		
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		World ww = new World(w, h);
		int[] pixels = new int[w * h];
		for(int i = 0; i < w; i++)
			for(int j = 0; j < h; j++){
				switch(ww.getTileId(i, j)){
					case 0: pixels[i + j * w] = 0x555555; break;
					case 1: pixels[i + j * w] = 0x0000AA; break;
					case 2: pixels[i + j * w] = 0xFFAA00; break;
					case 3: pixels[i + j * w] = 0x00AA00; break;
					case 4: pixels[i + j * w] = 0xFF0000; break;
					case 5: pixels[i + j * w] = 0xFFFF00; break;
					case 6: pixels[i + j * w] = 0x333333; break;
					case 7: pixels[i + j * w] = 0x887777; break;
					case 8: pixels[i + j * w] = 0x999944; break;
					case 9: pixels[i + j * w] = 0xFFFF99; break;
					case 10: pixels[i + j * w] = 0x666699; break;
					case 11: pixels[i + j * w] = 0xFF00FF; break;
					case 12: pixels[i + j * w] = 0x0000FF; break;
					case 13: pixels[i + j * w] = 0x00FF00; break;
					case 14: pixels[i + j * w] = 0xFF0000; break;
					default: break;
				}
			}
				
		
		img.setRGB(0, 0, w, h, pixels, 0, w);
		JOptionPane.showMessageDialog(null, null, "OK", JOptionPane.CLOSED_OPTION, new ImageIcon(img.getScaledInstance((int) (w * 2f), (int) (h * 2f), Image.SCALE_SMOOTH)));
	}
}
