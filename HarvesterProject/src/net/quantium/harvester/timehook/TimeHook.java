package net.quantium.harvester.timehook;

public abstract class TimeHook {
	
	public double time;
	public double duration;
	public boolean repeating;
	
	public TimeHook(double duration, boolean repeating){
		this.duration = duration;
		this.repeating = repeating;
	}
	
	public abstract void elapsed();
}
