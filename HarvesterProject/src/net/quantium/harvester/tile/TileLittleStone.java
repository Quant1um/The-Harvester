package net.quantium.harvester.tile;

import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class TileLittleStone extends TileGrass {
	@Override
	public void render(Renderer render, World w, int x, int y) {
		super.render(render, w, x, y);
		int flag = (w.getMetadata(x, y, 0) & 32) == 0 ? 1 : 0;
		int shape = (w.getMetadata(x, y, 0) >> 2) & 3;
		int xx = 9;
		int yy = 6;
		switch(shape){
			case 0: xx = 9; yy = 6; break;
			case 1: xx = 10; yy = 6; break;
			case 2: xx = 9; yy = 7; break;
			case 3: xx = 10; yy = 7; break;
			default: break;
		}
		switch(w.getMetadata(x, y, 0) & 3){
			case 0: render.get().draw(x * 16, y * 16, xx, yy, 1, 1, "sheet0", flag);  break;
			case 1: render.get().draw(x * 16 + 8, y * 16, xx, yy, 1, 1, "sheet0", flag); break;
			case 2: render.get().draw(x * 16, y * 16 + 8, xx, yy, 1, 1, "sheet0", flag); break;
			case 3: render.get().draw(x * 16 + 8, y * 16 + 8, xx, yy, 1, 1, "sheet0", flag); break;
			default: break;
		}
	}
	
	@Override
	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type) {
		world.setMetadata(i, j, 1, (byte) (world.getMetadata(i, j, 1) + damage));
		world.damageParticle(i * 16, j * 16, damage);
		if(world.getMetadata(i, j, 1) >= 3){
			world.setMetadata(i, j, 1, (byte) 0);
			world.throwItem(i * 16, j * 16, new ItemSlot(Items.rock, 0, 1));
			world.setTile(i, j, Tiles.grass);
		}
	}
}
