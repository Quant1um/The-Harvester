package net.quantium.harvester.entity.particle;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.Renderer;

public class DotParticle extends ParticleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final IBehaviour behaviour;
	private final int color;
	
	public DotParticle(int color, IBehaviour behaviour){
		this.color = color;
		this.behaviour = behaviour;
	}
	
	@Override
	public void update() {
		super.update();
		this.behaviour.tick(this);
	}

	@Override
	public void init() {
		this.lifetime = (int) (Main.TICKS_PER_SECOND * this.behaviour.getLifetime(this));
		this.behaviour.init(this);
	}

	@Override
	public void render(Renderer render) {
		render.get().put(x, y, color);
	}

	public static interface IBehaviour{
		void tick(DotParticle particle);
		
		default float getLifetime(DotParticle particle){
			return 5f;
		}
		
		default void init(DotParticle particle){}
	}
	
	public static class BehaviourFactory{
		
		public static IBehaviour lifetime(final IBehaviour source, final float lifetime){
			return new IBehaviour(){

				@Override
				public void tick(DotParticle particle) {
					source.tick(particle);
				}

				@Override
				public float getLifetime(DotParticle particle) {
					return lifetime;
				}

				@Override
				public void init(DotParticle particle) {
					source.init(particle);
				}
			};
		}
	}
}
