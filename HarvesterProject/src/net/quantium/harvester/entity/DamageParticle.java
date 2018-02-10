package net.quantium.harvester.entity;

import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public class DamageParticle extends ParticleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int damage;
	
	private float yy;
	
	public DamageParticle(int damage){
		this.damage = damage;
	}
	
	@Override
	public void particleUpdate() {
		yy += 0.45f;
		if(yy >= 24) remove();	
	}

	@Override
	public void init() {
		lifetime = 200;
	}

	@Override
	public void render(Renderer render) {
		render.get().drawText(x, (int) (y - yy), FontSize.NORMAL, String.valueOf(damage), (int)(9 - (yy / 3)) * 1000 + 810, TextAlign.LEFT, false);
	}

	@Override
	public void bump(Entity ent) {}
}
