package net.quantium.harvester.entity;

public abstract class AggressiveEntity extends AliveEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int damage;
	
	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity){
			((AliveEntity) ent).hit(damage);
		}
	}
}
