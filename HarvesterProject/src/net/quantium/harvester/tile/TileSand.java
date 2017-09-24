package net.quantium.harvester.tile;

import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.ToolItem.ToolType;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class TileSand extends Tile{

	@Override
	public void render(Renderer render, World w, int x, int y) {
		
		render.get().fillRect(x * 16, y * 16, 16, 16, 468);
		
		boolean u = !canConnect(w, x, y, x, y - 1);
		boolean d = !canConnect(w, x, y, x, y + 1);
		boolean l = !canConnect(w, x, y, x - 1, y);
		boolean r = !canConnect(w, x, y, x + 1, y);
		
		if (!u && !l) {
			render.get().draw(x * 16 + 0, y * 16 + 0, 4, 4, 1, 1, "sheet0", 0);
		} else
			render.get().draw(x * 16 + 0, y * 16 + 0, (l ? 6 : 7), (u ? 4 : 5), 1, 1, "sheet0", 0);

		if (!u && !r) {
			render.get().draw(x * 16 + 8, y * 16 + 0, 4, 5, 1, 1, "sheet0", 0);
		} else
			render.get().draw(x * 16 + 8, y * 16 + 0, (r ? 8 : 7), (u ? 4 : 5), 1, 1, "sheet0", 0);

		if (!d && !l) {
			render.get().draw(x * 16 + 0, y * 16 + 8, 4, 6, 1, 1, "sheet0", 0);
		} else
			render.get().draw(x * 16 + 0, y * 16 + 8, (l ? 6 : 7), (d ? 6 : 5), 1, 1, "sheet0", 0);
		if (!d && !r) {
			render.get().draw(x * 16 + 8, y * 16 + 8, 4, 7, 1, 1, "sheet0", 0);
		} else
			render.get().draw(x * 16 + 8, y * 16 + 8, (r ? 8 : 7), (d ? 6 : 5), 1, 1, "sheet0", 0);
	}

	@Override
	public void randomTick(World w, int x, int y) {
		
	}

	@Override
	public boolean isPassable(World w, int x, int y, Entity e) {
		return true;
	}

	@Override
	public void onInteract(World w, int x, int y, Entity e, InteractionMode mode) {
		
	}

	@Override
	public boolean isConnectable(World w, int x, int y, int xx, int yy) {
		Tile t = Tile.Registry.get(w.getTile(xx, yy));
		if(t instanceof TileSand || t instanceof TileGrass){
			return true;
		}
		if(t instanceof TileRock){
			return true;
		}
		return false;
	}

	@Override
	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type) {
		if(type != ToolType.SHOVEL) return;
		world.setMetadata(j, i, 1, (byte) (world.getMetadata(i, j, 1) + damage));
		if(world.getMetadata(i, j, 1) + damage >= 200){
			world.setTile(i, j, Tiles.water);
		}
	}
	


}
