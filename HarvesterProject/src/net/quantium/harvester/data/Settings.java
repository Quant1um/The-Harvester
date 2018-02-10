package net.quantium.harvester.data;

import net.quantium.harvester.text.Localization;
import net.quantium.harvester.utilities.IOContainer;

public class Settings {
	public static final int[][] resolutions = {{350, 225},
			   								   {400, 250},
			   								   {400, 300},
			   								   {450, 300},
			   								   {500, 350},
			   								   {500, 400}};
	
	public IOContainer container = new IOContainer("settings.dat");
	public boolean useShadows;
	public int resolution;
	public int localization;
	
	public int getWidth(){
		return resolutions[resolution][0];
	}
	
	public int getHeight(){
		return resolutions[resolution][1];
	}
	
	public void restore(){
		useShadows = (boolean) container.get().getOrDefault("shadows", false);
		resolution = (int) container.get().getOrDefault("res", 0);
		Localization.setCurrentLocaleID(localization = (int) container.get().getOrDefault("lang", 0));
	}
	
	public void save(){
		container.get().put("shadows", useShadows);
		container.get().put("res", resolution);
		container.get().put("lang", localization);
		container.save();
	}
}
