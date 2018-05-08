package net.quantium.harvester.tile;


import net.quantium.harvester.Main;
import net.quantium.harvester.entity.Entity;
import net.quantium.harvester.entity.PlayerEntity;
import net.quantium.harvester.entity.SlimeBossEntity;
import net.quantium.harvester.entity.Entity.InteractionMode;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class TileWater extends Tile{


	
	public static final int[] pattern = {0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1};
	
	@Override
	public void render(Renderer render, World w, int x, int y) {
		render.get().draw(x * 16, y * 16, 9, 4, 2, 2, "sheet0", 0);
		if((((x / 2) & 7) ^ (y & 5)) == 0){
			int animframe = pattern[(Main.getInstance().getCounter() / 10 + x * 2 + y * 7) % pattern.length];
			render.get().drawColored(x * 16, y * 16, animframe * 2, 13, 2, 2, ColorBundle.get(-1, -1, -1, -1, -1, 778), "ambient", 0);
		}
	}

	@Override
	public void randomTick(World w, int x, int y) {
		
	}

	@Override
	public boolean isPassable(World w, int x, int y, Entity e) {
		return e instanceof PlayerEntity || e instanceof SlimeBossEntity;
	}

	@Override
	public void onInteract(World w, int x, int y, Entity e, InteractionMode mode) {
		
	}

	@Override
	public boolean isConnectable(World w, int x, int y, int xx, int yy) {
		return w.getTile(xx, yy) instanceof TileWater;
	}

	@Override
	public void hit(World world, int i, int j, PlayerEntity playerEntity, int damage, ToolType type) {
		
	}
}
