package net.quantium.harvester.entity;

public abstract class ParticleEntity extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int time;
	protected int lifetime = 50;
	
	public abstract void particleUpdate();
	
	@Override
	public void update(){
		time++;
		if(time >= lifetime) this.remove();
		particleUpdate();
	}
	
	public int getTime(){
		return time;
	}
	
}
