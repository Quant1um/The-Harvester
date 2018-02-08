package net.quantium.harvester.entity;

public abstract class MobEntity extends LivingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int damage;
	
	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity){
			((LivingEntity) ent).hit(damage);
		}
	}
}
