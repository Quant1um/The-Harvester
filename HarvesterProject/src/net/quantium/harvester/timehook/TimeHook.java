package net.quantium.harvester.timehook;

public abstract class TimeHook {
	
	public double time;
	
	public abstract void elapsed();
	public abstract double seconds();
}
