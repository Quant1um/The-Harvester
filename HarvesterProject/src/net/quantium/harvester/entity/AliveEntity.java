package net.quantium.harvester.entity;

public abstract class AliveEntity extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int health;
	public int maxHealth;
	public int radiation;
	
	protected int invicibleTime;
	
	public void hit(int dmg){
		if(this.invicibleTime > 2) return;
		this.health -= dmg;
		this.world.damageParticle(x, y, dmg);
		this.invicibleTime = 30;
		if(this.health <= 0){
			this.died();
			this.remove();
		}
	}
	
	public abstract void died();
	
	public void update(){
		if(invicibleTime > 0) invicibleTime--;
	}
	
}
