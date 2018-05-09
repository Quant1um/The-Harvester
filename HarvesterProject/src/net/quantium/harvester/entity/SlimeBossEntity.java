package net.quantium.harvester.entity;

import net.quantium.harvester.Main;
import net.quantium.harvester.entity.ai.AISlime;
import net.quantium.harvester.entity.hitbox.Hitbox;
import net.quantium.harvester.render.Renderer;

public class SlimeBossEntity extends SlimeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean readyToEject = false;
	
	public SlimeBossEntity() {
		super(SlimeType.DARKIE);
		health = maxHealth = 100;
	}
	
	@Override
	public void init() {
		ai = new AISlime(this);
		hitbox = new Hitbox(20, 10, -12, -3);
	}
	
	@Override
	public void render(Renderer render){
		render.get().draw(x - 16, y - yOffset[frame] * 3 - 16, frame * 4 + 6, 33 + (readyToEject ? 4 : 0), 4, 4, "sheet0", 0);
		if(invincibleTime >= 20)
			render.get().drawTinted(x - 16, y - yOffset[frame] * 3 - 16, frame * 4 + 6, 33 + (readyToEject ? 4 : 0), 4, 4, DAMAGE_TINT_COLOR, "sheet0", 0);
	}
	
	@Override
	public void update() {
		super.update();
		if(Main.instance().getCounter() % 4 != 0) jump--;
		
		if(readyToEject) eject();	
	}

	private void eject() {
		readyToEject = false;
		for(double i = 0; i < 2 * Math.PI; i += (Math.PI / 3) + (Main.RANDOM.nextInt(100) / 100f)){
			world.addEntity(new SpikeEntity(x, y, (float)Math.cos(i) * 7f, (float)Math.sin(i) * 7f));
		}
	}
	
	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity){
			((LivingEntity) ent).hit(4);
		}
	}
}
