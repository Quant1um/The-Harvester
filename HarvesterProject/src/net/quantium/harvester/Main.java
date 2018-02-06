package net.quantium.harvester;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import javax.swing.JFrame;

import net.quantium.harvester.entity.BuildableInfo;
import net.quantium.harvester.input.InputService;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.CrashScreen;
import net.quantium.harvester.screen.MainScreen;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.system.IOContainer;
import net.quantium.harvester.system.Session;
import net.quantium.harvester.system.Settings;
import net.quantium.harvester.system.text.FontSize;
import net.quantium.harvester.tile.Tiles;
import net.quantium.harvester.timehook.TimeHook;
import net.quantium.harvester.timehook.TimeHookManager;

//legacy
public class Main extends Canvas{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final double NANOSECONDS_PER_SECOND = 1000000000.0, TICKS_PER_SECOND = 50.0;
	public static final double NANOSECONDS_PER_TICK = NANOSECONDS_PER_SECOND / TICKS_PER_SECOND;
	public static final int SCALE = 2;
	public static final int VERSION = 1;
	
	public int counter = 0;
	
	public static final String NAME = "THE HARVESTER";
	
	public static final Random GLOBAL_RANDOM = new Random();
	
	int frames = 0, 
		updates = 0;
	int framesIncompleted = 0, 
		updatesIncompleted = 0;

	private int debugMode = 0;
	private static final String[] DEBUG_NAMES = new String[]{null, "hitbox", "ai_valuable", "metadata"};
	
	private boolean active = true;
	
	private Renderer renderer;
	private ScreenService screenService;
	private InputService inputService;
	
	private JFrame frame;
	
	public Session session;
	public Settings settings = new Settings();
	
	private static Main _instance;
	public static Main getInstance(){
		return _instance;
	}
	
	public static void main(String[] args){
		System.setProperty("sun.java2d.opengl", "True");
		
		Tiles.register();
		Items.register();
		BuildableInfo.register();
		Session.updateNames();
		
		_instance = new Main();
		_instance.settings.restore();
		_instance.setPreferredSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.setMinimumSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.setMaximumSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.frame = new JFrame(NAME + " v0.0.2 ALPHA");
		_instance.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_instance.frame.setLayout(new BorderLayout());
		_instance.frame.getContentPane().add(_instance, BorderLayout.CENTER);
		_instance.frame.getContentPane().setPreferredSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.frame.getContentPane().setMinimumSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.frame.getContentPane().setMaximumSize(new Dimension(_instance.getRenderWidth() * SCALE, _instance.getRenderHeight() * SCALE));
		_instance.frame.setResizable(false);
		_instance.frame.pack();
		_instance.frame.setVisible(true);
		_instance.frame.getContentPane().requestFocus();
		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank");
		_instance.frame.getContentPane().setCursor(blankCursor);
		
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(_instance));
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

			@Override
			public void run() {
				if(_instance.session != null) _instance.session.save();
				_instance.active = false;
			}
			
		}));
		
		_instance.init();
		_instance.run();
	}
	
	public void forceExit(){
		active = false;
		System.exit(0);
	}
	
	private void init(){
		TimeHookManager.register(new TimeHook(){

			@Override
			public void elapsed() {	
				frames = framesIncompleted;
				updates = updatesIncompleted;
				framesIncompleted = updatesIncompleted = 0;
			}

			@Override
			public double seconds() {
				return 1;
			}
			
		});
		
		renderer = new Renderer(getRenderWidth(), getRenderHeight());
		inputService = new InputService(this);
		screenService = new ScreenService(this);
		
		screenService.setScreen(new MainScreen());
	}
	
	private void run(){
		long time = System.nanoTime();
		double unp = 0;	
		try{
			while(active){
				long cur = System.nanoTime();
				long delta = cur - time;
				time = cur;
				if(delta > 0){
					unp += delta / NANOSECONDS_PER_TICK;
					while(unp >= 1){
						update();
						unp--;
						updatesIncompleted++;
					}
					render();
					framesIncompleted++;
					
					TimeHookManager.update(delta / NANOSECONDS_PER_SECOND);
					throw new Exception("abc");
				}
			}
		}catch(Throwable e){
			forceCrashScreen(e);
		}
	}
	
	public int getRenderWidth(){
		return settings.getWidth();
	}

	public int getRenderHeight(){
		return settings.getHeight();
	}
	
	public boolean useShadows(){
		return settings.useShadows;
	}
	
	public ScreenService getScreenService(){
		return screenService;
	}
	
	public InputService getInputService(){
		return inputService;
	}
	
	public int getDebugMode(){
		return debugMode;
	}
	
	public void nextDebugMode(){
		debugMode = (debugMode + 1) % DEBUG_NAMES.length;
	}
	
	public int getCounter(){
		return counter;
	}
	
	private void update(){	
		counter++;
		if(counter >= Integer.MAX_VALUE) counter = 0;
		inputService.update();
		if(screenService.isScreenActive()){
			if(screenService.current().mustUpdateGame){
				if(session != null) session.update();		
			}
			if(screenService.isScreenActive()) screenService.current().update();
		}else{
			if(session != null) session.update();		
		}
	}
	
	private void render(){
		BufferStrategy bufstr = getBufferStrategy();
		if(bufstr == null){
			createBufferStrategy(3);
			return;
		}
		renderer.get().fill(000);

		if(!screenService.isScreenActive() || screenService.current().mustRenderGame){
			if(session != null){
				session.render(renderer);
				if(session.getWorld().player != null){
					for(int i = 0; i < session.getWorld().player.maxHealth; i++){
						boolean have = session.getWorld().player.health > i;
						renderer.get().drawColored(3 + i * 11, getRenderHeight() - 16, 0, 10, 2, 2, ColorBundle.get(-1, -1, -1, have ? 833 : 333, have ? 722 : 222, -1), "gui", 0);
					}
				}
			}
		}
		
		if(screenService.isScreenActive())
			screenService.current().render(renderer);
		
		if(debugMode > 0){
			renderer.get().drawText(5, 5, FontSize.NORMAL, "debug enabled: " + DEBUG_NAMES[debugMode], 999);
			renderer.get().drawText(5, 15, FontSize.NORMAL, "fps: " + frames, 999);
			renderer.get().drawText(5, 25, FontSize.NORMAL, "ups: " + updates, 999);
			if(session != null && session.getWorld() != null && session.getWorld().player != null){
				renderer.get().drawText(5, 35, FontSize.NORMAL, "x: " + session.getWorld().player.x, 999);
				renderer.get().drawText(5, 45, FontSize.NORMAL, "y: " + session.getWorld().player.y, 999);
				renderer.get().drawText(5, 55, FontSize.NORMAL, "time: " + session.getWorld().time, 999);
			}
		}
		
		if(IOContainer.isSaving){
			renderer.get().drawColored(getRenderWidth() - 20 + 1, 4 + 1, ((counter / 30) % 5) * 2, 14, 2, 2, ColorBundle.get(-1, -1, -1, -1, 000, 000), "gui", 0);
			renderer.get().drawColored(getRenderWidth() - 20, 4, ((counter / 30) % 5) * 2, 14, 2, 2, ColorBundle.get(-1, -1, -1, -1, 669, 888), "gui", 0);
		}
		
		if(screenService.current() == null || screenService.current().showCursor) 
			renderer.get().renderCursor(inputService.getMouseX(), inputService.getMouseY());
		renderer.update();
		Graphics g = bufstr.getDrawGraphics();
		renderer.renderImage(g, getWidth(), getHeight());
		g.dispose();
		bufstr.show();
	}

	public static final String CRASHLOG_FILE = "crash.log";
	void writeCrashlog(Throwable e) {
		BufferedWriter writer = null;
        try{
            File crashInfo = new File(IOContainer.DATA_FOLDER + File.separator + CRASHLOG_FILE);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(crashInfo)));
            writer.write("# Exception: " + e.getClass().getSimpleName() + "\n");
            for(StackTraceElement element : e.getStackTrace())
                writer.write(element.toString() + "\n");
            writer.write("\n# Session: \n" + session);
            if(session != null){
            	writer.write("\n# Player: \n" + session.getPlayer());
            	writer.write("\n# World: \n" + session.getWorld());
            }
            writer.write("\n# Renderer: \n" + renderer);
            writer.write("\n# Screen Service: \n" + screenService);
            writer.write("\n# Input Service: \n" + inputService);
           
        }catch(Exception e0) {}
        finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {}
            }
        }
	}
	
	private static final long CRASH_SCREEN_DURATION_NANOS = (long)(NANOSECONDS_PER_SECOND * 5);
	private void forceCrashScreen(Throwable e) {
		writeCrashlog(e);
		screenService.setScreen(new CrashScreen(e));
		
		long nano = System.nanoTime();
		while(System.nanoTime() - nano < CRASH_SCREEN_DURATION_NANOS)
			render();

		System.exit(-1);
	}
}
