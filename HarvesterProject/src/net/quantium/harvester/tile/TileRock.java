package net.quantium.harvester.tile;

import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class TileRock extends Tile{
	
	public static final int[] pattern = {0, 2, 1};
	public static final int[] pattern0 = {1, 0, 2};
	@Override
	public void render(Renderer render, World w, int x, int y) {
		int spos = pattern[x < 0 ? -x % 3 : x % 3];
		int spos0 = pattern0[x < 0 ? -x % 3 : x % 3];
		boolean down = isConnectable(w, x, y, x, y + 1);	
		boolean right = isConnectable(w, x, y, x + 1, y);
		boolean left = isConnectable(w, x, y, x - 1, y);
		boolean up = isConnectable(w, x, y, x, y - 1);
		boolean downleft = isConnectable(w, x, y, x - 1, y + 1);
		boolean downright = isConnectable(w, x, y, x + 1, y + 1);
		render.get().draw(x * 16, y * 16, 10 + spos, !down ? 2 : 0, 1, 2, "sheet0", 0);
		render.get().draw(x * 16 + 8, y * 16, 10 + spos0, !down ? 2 : 0, 1, 4, "sheet0", 0);
		if(!left) render.get().fillRect(x * 16, y * 16, 1, 16, 444);
		if(!right) render.get().fillRect(x * 16 + 15, y * 16, 1, 16, 444);
		if(!up) render.get().fillRect(x * 16, y * 16, 16, 1, 444);
		if(left && !downleft && down) render.get().fillRect(x * 16, y * 16 + 1, 1, 15, 444);
		if(right && !downright && down) render.get().fillRect(x * 16 + 15, y * 16 + 1, 1, 15, 444);
	}

	@Override
	public void randomTick(World w, int x, int y) {
		
	}

	@Override
	public boolean isPassable(World w, int x, int y, Entity e) {
		return false;
	}

	@Override
	public void onInteract(World w, int x, int y, Entity e, InteractionMode mode) {
		
	}
	
	@Override
	public boolean isConnectable(World w, int x, int y, int xx, int yy) {
		return w.getTile(xx, yy) instanceof TileRock;
	}

	@Override
	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type) {
		if(type != ToolType.PICKAXE){
			damage = 1;
			if(world.getMetadata(i, j, 1) > 35) return;
		}
		world.setMetadata(i, j, 1, (byte) (world.getMetadata(i, j, 1) + damage));
		world.damageParticle(i * 16, j * 16, damage);
		if(world.getMetadata(i, j, 1) >= 40){
			world.setMetadata(i, j, 1, (byte) 0);
			world.setTile(i, j, Tiles.grass);
			world.throwItem(i * 16, j * 16, new ItemSlot(Items.rock, 0, 2));
		}
	}
	
}
