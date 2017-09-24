package net.quantium.harvester.timehook;

public class TimeHookManager {
	public static TimeHook[] hooks = new TimeHook[16]; //16 hooks can be registered
	
	public static int register(TimeHook h){
		for(int i = 0; i < hooks.length; i++)
			if(hooks[i] == null){
				hooks[i] = h;
				return i;
			}
		return -1;
	}
	
	public static void update(double delta){
		for(int i = 0; i < hooks.length; i++)
			if(hooks[i] != null){
				hooks[i].time += delta;
				if(hooks[i].time >= hooks[i].seconds()){
					hooks[i].time = 0;
					hooks[i].elapsed();
				}	
			}
	}
	
	public static TimeHook get(int id){
		return hooks[id];
	}
}
