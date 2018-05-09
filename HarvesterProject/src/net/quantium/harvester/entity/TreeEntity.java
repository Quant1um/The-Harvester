package net.quantium.harvester.entity;

import java.util.Random;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.item.instances.ToolItem;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.render.Color;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.world.World;

public class TreeEntity extends LivingEntity {

	public static final int[] treeColors;
	static{
		treeColors = new int[256];
		for(int i = 0; i < 256; i++){
			treeColors[i] = (int) Math.min(Math.max(((1 - (i / 256f)) * 3), 0), 9) * 100 + (int) Math.min(Math.max(((i / 256f) * 8), 0), 9) * 10;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	public int temp;
	public int rotation;
	
	public TreeEntity(Random rand, byte temp, int type){
		if(type > 2) type = 2;
		this.type = rand.nextInt(100) == 10 ? rand.nextInt(3) : type;
		rotation = rand.nextInt(1);
		this.temp = treeColors[temp + 128];
		health = maxHealth = 20;
	}
	
	@Override
	public void init() {
		hitbox = new Hitbox(type == 0 ? 7 : type == 1 ? 4 : 10, 5, type == 0 ? -1 : type == 1 ? -3 : -5, -5);
	}

	private static final int[] spriteSheetMetaX0 = {0, 4, 11};
	private static final int[] spriteSheetMetaY0 = {0, 3, 0};
	private static final int[] spriteSheetMetaW0 = {3, 3, 5};
	private static final int[] spriteSheetMetaH0 = {5, 2, 7};
	private static final int[] spriteSheetMetaX1 = {4, 8, 6};
	private static final int[] spriteSheetMetaY1 = {0, 0, 5};
	private static final int[] spriteSheetMetaW1 = {4, 3, 5};
	private static final int[] spriteSheetMetaH1 = {3, 5, 6};
	
	@Override
	public void render(Renderer render) {
		ColorBundle bnd = ColorBundle.get(-1, Color.darker((short) temp, 1), temp, -1, -1, Color.lighter((short)temp, 1));
		ColorBundle bnd0 = ColorBundle.get(-1, 321, 432, -1, -1, 543);
		int meta = type;
		int x0 = spriteSheetMetaX0[meta];
		int x1 = spriteSheetMetaX1[meta];
		int y0 = spriteSheetMetaY0[meta];
		int y1 = spriteSheetMetaY1[meta];
		int w0 = spriteSheetMetaW0[meta];
		int w1 = spriteSheetMetaW1[meta];
		int h0 = spriteSheetMetaH0[meta];
		int h1 = spriteSheetMetaH1[meta];
		
		render.get().drawColored(x - w0 * 4, y - h0 * 8, x0, y0, w0, h0, bnd0, "ambient", 0);
		render.get().drawColored(x - w0 * 4 + (type == 0 ? -1 : 0), y - h0 * 8 + (type == 1 ? -20 : 0), x1, y1, w1, h1, bnd, "ambient", 0);
	}

	@Override
	public void bump(Entity ent) {}

	@Override
	public boolean isPassable(Entity ent) {
		return false;
	}

	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot item) {
		if(item != null && item.getItem() instanceof ToolItem && ((ToolItem)item.getItem()).getToolType() == ToolType.AXE){
			this.hit(((ToolItem)item.getItem()).getPower());
		}else{
			this.hit(1);
		}
		if(Main.RANDOM.nextInt(5) == 0) world.throwItem(x, y, new ItemSlot(Items.stick, 0, Main.RANDOM.nextInt(2) + 1));
		if(Main.RANDOM.nextInt(30) == 0) world.throwItem(x, y, new ItemSlot(Items.nut, 0, Main.RANDOM.nextInt(1) + 1));
		return true;
	}

	@Override
	public void onDied() {
		super.onDied();
		world.throwItem(x, y, new ItemSlot(Items.wood, 0, Main.RANDOM.nextInt(5) + 1));
	}
}
