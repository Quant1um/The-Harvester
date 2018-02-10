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

import net.quantium.harvester.data.Session;
import net.quantium.harvester.data.Settings;
import net.quantium.harvester.entity.BuildableInfo;
import net.quantium.harvester.input.IInputListener;
import net.quantium.harvester.input.InputService;
import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.item.Items;
import net.quantium.harvester.render.ColorBundle;
import net.quantium.harvester.render.Renderer;
import net.quantium.harvester.screen.CrashScreen;
import net.quantium.harvester.screen.MainScreen;
import net.quantium.harvester.screen.ScreenService;
import net.quantium.harvester.text.FontSize;
import net.quantium.harvester.text.TextAlign;
import net.quantium.harvester.tile.Tiles;
import net.quantium.harvester.timehook.TimeHook;
import net.quantium.harvester.timehook.TimeHookManager;
import net.quantium.harvester.utilities.IOContainer;

//old and legacy and unreadable code
//its has a lot of not-encapsulated variables
//                 hardcode
//                 uncommented ununderstandable code
//                 invalid abstractions
//                 useless stuff
public class Main extends Canvas implements IInputListener{
	
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
	
	private int frames = 0, 
				updates = 0;
	private int framesIncompleted = 0, 
				updatesIncompleted = 0;

	private DebugMode debugMode = DebugMode.NONE;

	private boolean active = true;
	
	private Renderer renderer;
	private ScreenService screenService;
	private InputService inputService;
	
	private JFrame frame;
	
	private Session session;
	private Settings settings = new Settings();
	
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
				if(_instance.session != null) _instance.session.save(true);
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
		TimeHookManager.register(new TimeHook(1d, true){
			@Override
			public void elapsed() {	
				frames = framesIncompleted;
				updates = updatesIncompleted;
				framesIncompleted = updatesIncompleted = 0;
			}
		});
		
		renderer = new Renderer(getRenderWidth(), getRenderHeight());
		inputService = new InputService(this);
		screenService = new ScreenService();
		
		screenService.setScreen(new MainScreen());
	}
	
	private void run(){
		long time = System.nanoTime();
		double unp = 0;	

		while(active){
			try{
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
				}
				
				Thread.sleep(1);
			}catch(Throwable e){
				forceCrashScreen(e);
			}
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
	
	public Session getSession(){
		return session;
	}
	
	public boolean hasSession(){
		return session != null;
	}
	
	public void setSession(Session session){
		this.session = session;
	}
	
	public void resetSession(){
		session = null;
	}
	
	public Settings getSettings(){
		return settings;
	}
	
	public DebugMode getDebugMode(){
		return debugMode;
	}
	
	public void nextDebugMode(){
		debugMode = DebugMode.values()[(debugMode.ordinal() + 1) % DebugMode.values().length];
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

		if(!screenService.isScreenActive() || screenService.current().mustRenderGame){
			if(session != null){
				session.render(renderer);
				if(session.getWorld().player != null){
					for(int i = 0; i < session.getWorld().player.getMaxHealth(); i++){
						boolean has = session.getWorld().player.health > i;
						renderer.get().drawColored(3 + i * 11, getRenderHeight() - 16, 0, 10, 2, 2, ColorBundle.get(-1, -1, -1, has ? 833 : 333, has ? 722 : 222, -1), "gui", 0);
					}
				}
			}
		}
		
		if(screenService.isScreenActive())
			screenService.current().render(renderer);
		
		int y = 5;
		if(debugMode != DebugMode.NONE){
			renderer.get().drawText(5, y, FontSize.NORMAL, "debug enabled: " + debugMode.name(), 999, TextAlign.LEFT);
			renderer.get().drawText(5, y += 10, FontSize.NORMAL, "fps: " + frames, 999, TextAlign.LEFT);
			renderer.get().drawText(5, y += 10, FontSize.NORMAL, "ups: " + updates, 999, TextAlign.LEFT);
			if(session != null && session.getWorld() != null && session.getWorld().player != null){
				renderer.get().drawText(5, y += 10, FontSize.NORMAL, "x: " + session.getWorld().player.x, 999, TextAlign.LEFT);
				renderer.get().drawText(5, y += 10, FontSize.NORMAL, "y: " + session.getWorld().player.y, 999, TextAlign.LEFT);
				renderer.get().drawText(5, y += 10, FontSize.NORMAL, "time: " + session.getWorld().time, 999, TextAlign.LEFT);
			}
			
			
			switch(debugMode){
				case GUI_INPUT: {
					if(screenService.isScreenActive())
						renderer.get().drawText(5, y += 10, FontSize.NORMAL, "screen: " + screenService.current().getClass().getSimpleName(), 999, TextAlign.LEFT);
					renderer.get().drawText(5, y += 10, FontSize.NORMAL, "mouse x: " + inputService.getMouseX(), 999, TextAlign.LEFT);
					renderer.get().drawText(5, y += 10, FontSize.NORMAL, "mouse y: " + inputService.getMouseY(), 999, TextAlign.LEFT);
					for(Key key : inputService.keys()){
						if(key.isDown())
							renderer.get().drawText(5, y += 10, FontSize.NORMAL, key.toString(), 888, TextAlign.LEFT);
					}
				} break;
				default: break;
			}
		}
		
		if(IOContainer.isSaving){
			renderer.get().drawColored(getRenderWidth() - 20 + 1, 4 + 1, ((counter / 30) % 5) * 2, 14, 2, 2, ColorBundle.get(-1, -1, -1, -1, 000, 000), "gui", 0);
			renderer.get().drawColored(getRenderWidth() - 20, 4, ((counter / 30) % 5) * 2, 14, 2, 2, ColorBundle.get(-1, -1, -1, -1, 669, 888), "gui", 0);
		}
		
		if(screenService.current() == null || screenService.current().showCursor)
			renderer.get().drawColored(inputService.getMouseX(), inputService.getMouseY(), 0, 6, 2, 2, ColorBundle.get(-1, -1, -1, -1, -1, 7999), "gui", 0);
		
		renderer.update();
		Graphics g = bufstr.getDrawGraphics();
		renderer.renderImage(g, getWidth(), getHeight());
		g.dispose();
		bufstr.show();
	}

	public static final String CRASHLOG_FILE = "crash.log";
	void writeCrashlog(Throwable e) {
		e.printStackTrace();
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
	
	private void forceCrashScreen(Throwable e) {
		writeCrashlog(e);		
		if(screenService.current() instanceof CrashScreen)
			System.exit(-1);
		screenService.setScreen(new CrashScreen(e));
	}

	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first) {
		if(session != null) 
			session.getSpectator().onMouseClick(x, y, button, first);
		if(screenService.isScreenActive())
			screenService.current().onMouseClick(x, y, button, first);
	}

	@Override
	public void onKeyPress(Key key, boolean first) {
		if(session != null) 
			session.getSpectator().onKeyPress(key, first);
		
		if(key.isSuppressed()) return;
		if(screenService.isScreenActive())
			screenService.current().onKeyPress(key, first);
	
		if(key.isSuppressed()) return;
		if(first && key == inputService.debug)
			nextDebugMode();
	}

	@Override
	public void onMouseWheel(int ticks) {
		if(session != null) 
			session.getSpectator().onMouseWheel(ticks);
		if(screenService.isScreenActive())
			screenService.current().onMouseWheel(ticks);
	}
	
	public enum DebugMode{
		NONE, HITBOX, AI_HEATMAP, METADATA, GUI_INPUT
	}
}
