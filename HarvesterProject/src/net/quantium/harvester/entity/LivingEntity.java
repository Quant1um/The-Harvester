package net.quantium.harvester.entity;

public abstract class LivingEntity extends Entity{
	
	public static final int DAMAGE_TINT_COLOR = 5900;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int health;
	protected int maxHealth;
		
	protected int invincibleTime;
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if(this.health <= 0)
			this.onDied();
	}

	public int getInvincibleTime() {
		return invincibleTime;
	}

	public void hit(int dmg){
		if(this.invincibleTime > 2) return;
		this.health -= dmg;
		this.world.damageParticle(x, y, dmg);
		this.invincibleTime = 30;
		if(this.health <= 0)
			this.onDied();
	}
	
	public void heal(int hp){
		this.health += hp;
		if(this.health > maxHealth)
			this.health = maxHealth;
		this.world.healParticle(x, y, hp);
		this.invincibleTime = 15;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public void onDied() {
		this.remove();
	}
	
	@Override
	public void update(){
		if(invincibleTime > 0) invincibleTime--;
	}
}
