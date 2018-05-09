package net.quantium.harvester.timehook;

public class TimeHookManager {
	private static final TimeHook[] hooks = new TimeHook[16]; //16 hooks can be registered
	
	public static int register(TimeHook h){
		for(int i = 0; i < hooks.length; i++)
			if(hooks[i] == null){
				hooks[i] = h;
				return i;
			}
		return -1;
	}
	
	public static void update(double delta){
		for(int i = 0; i < hooks.length; i++){
			TimeHook hook = get(i);
			if(hook != null){
				hook.time += delta;
				if(hook.time >= hook.duration){
					hook.time -= hook.duration;
					hook.elapsed();
					if(!hook.repeating)
						hook = null;
				}	
			}
		}
	}
	
	public static TimeHook get(int id){
		return hooks[id];
	}
}
