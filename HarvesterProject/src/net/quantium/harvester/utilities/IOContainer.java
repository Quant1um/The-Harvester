package net.quantium.harvester.utilities;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import net.quantium.harvester.Main;

public class IOContainer{
	public static final String DATA_FOLDER = System.getProperty("user.home") + File.separator + Main.NAME;
	
	private static volatile boolean isSaving;
	private final String path;
	
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
				saveSynchronized();
			}
		}).start();
	}
	
	public void saveSynchronized(){
		isSaving = true;
		
		FileOutputStream tempFileOut = null;
		ObjectOutputStream objectOut = null;
		
		try {
			File file = new File(DATA_FOLDER + path);
			if(!file.exists()){
				file.createNewFile();
			}
			
			tempFileOut = new FileOutputStream(file);
			objectOut = new ObjectOutputStream(tempFileOut);
			objectOut.writeObject(map); //todo possible crash concurrent (on game exit)
	    } catch (IOException e) {
	    	e.printStackTrace();
		} finally {
			close(tempFileOut);
			close(objectOut);
			
		}
		
		isSaving = false;
	}
	
	private static void close(Closeable closeable) {
		if(closeable == null)
			return;
		
		try{
			closeable.close();
			System.gc(); //dirty trick
		}catch(Throwable ignored){}		
	}

	public Map<String, Object> get(){
		if(map == null) load();
		return map;
	}	
	
	public static final boolean isSaving(){
		return isSaving;
	}
}
