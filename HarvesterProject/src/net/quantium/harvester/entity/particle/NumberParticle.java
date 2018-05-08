package net.quantium.harvester.entity.particle;

import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;

public class NumberParticle extends ParticleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int number, color;
	private float yy;
	
	public NumberParticle(int number, int color){
		this.number = number;
		this.color = color;
	}
	
	@Override
	public void update() {
		super.update();
		yy += 0.45f;
		if(yy >= 24) remove();	
	}

	@Override
	public void init() {
		lifetime = 200;
	}

	@Override
	public void render(Renderer render) {
		render.get().drawText(x, (int) (y - yy), FontSize.NORMAL, String.valueOf(number), (int)(9 - (yy / 3)) * 1000 + this.color, TextAlign.LEFT, false);
	}
}
