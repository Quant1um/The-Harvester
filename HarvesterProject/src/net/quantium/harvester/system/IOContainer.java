package net.quantium.harvester.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import net.quantium.harvester.Main;

public class IOContainer {
	
	private final String path;
	
	public static final String DATA_FOLDER = System.getProperty("user.home") + File.separator + Main.NAME;
	
	public static boolean isSaving;
	
	static{
		if(!new File(DATA_FOLDER).exists())
			new File(DATA_FOLDER).mkdir();
		if(!new File(DATA_FOLDER + File.separator + "saves").exists())
			new File(DATA_FOLDER + File.separator + "saves").mkdir();
	}
	private Map<String, Object> map;
	
	public IOContainer(String path){
		if(!path.startsWith(File.separator))
			path = File.separator + path;
		this.path = path;
	}

	
	@SuppressWarnings("unchecked")
	public void load(){
		if(!new File(DATA_FOLDER + path).exists())
			map = new HashMap<String, Object>();
		else{
			try {
				FileInputStream fis = new FileInputStream(DATA_FOLDER + path);
				ObjectInputStream ois = new ObjectInputStream(fis);
				map = (HashMap<String, Object>) ois.readObject();	
				ois.close();
				fis.close();
	        } catch (ClassNotFoundException | IOException e) {
	        	map = new HashMap<String, Object>();
			}
		}
	}
	
	public void save(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				isSaving = true;
				try {
					if(!new File(DATA_FOLDER + path).exists()){
						new File(DATA_FOLDER + path).createNewFile();
					}
					FileOutputStream f = new FileOutputStream(DATA_FOLDER + path);
				    ObjectOutputStream o = new ObjectOutputStream(f);
				    o.writeObject(map);
				    o.close();
				    f.close();
				    System.gc();//dirty trick
			    } catch (IOException e) {
			    	e.printStackTrace();
				}
				isSaving = false;
			}
			
		}).start();
	}
	
	public Map<String, Object> get(){
		if(map == null) load();
		return map;
	}
	
}
