package net.quantium.harvester.entity;

public abstract class LivingEntity extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int health;
	protected int maxHealth;
		
	protected int invincibleTime;
	
	public void hit(int dmg){
		if(this.invincibleTime > 2) return;
		this.health -= dmg;
		this.world.damageParticle(x, y, dmg);
		this.invincibleTime = 30;
		if(this.health <= 0){
			this.onDied();
			this.remove();
		}
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public void onDied() {}
	
	public void update(){
		if(invincibleTime > 0) invincibleTime--;
	}
	
}
