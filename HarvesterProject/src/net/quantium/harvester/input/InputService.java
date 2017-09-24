package net.quantium.harvester.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

import net.quantium.harvester.Main;
import net.quantium.harvester.render.Layer;

public class InputService implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener{
	public Main main;
	
	public static final int BUTTON_UNDEFINED = 0;
	public static final int BUTTON_LEFT = 1;
	public static final int BUTTON_MIDDLE = 2;
	public static final int BUTTON_RIGHT = 3;
	public static final int BUTTON_RELEASED = 4;
	
	private boolean click = false;
	private int x, y;
	private int button;
	
	public Map<Integer, Key> keys = new HashMap<Integer, Key>();
	
	public InputService(Main main){
		this.main = main;
		main.addKeyListener(this);
		main.addMouseListener(this);
		main.addMouseWheelListener(this);
		main.addMouseMotionListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if(Main.getInstance().getScreenService().isScreenActive())
			Main.getInstance().getScreenService().current().onMouseWheel(arg0.getWheelRotation());
		if(main.session != null && !Main.getInstance().getScreenService().isScreenActive())
			main.session.getSpectator().onMouseWheel(arg0.getWheelRotation());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		x = (arg0.getX() / Main.SCALE);
		y = (arg0.getY() / Main.SCALE);
		click = true;
		button = arg0.getButton();
		if(Main.getInstance().getScreenService().isScreenActive())
			Main.getInstance().getScreenService().current().onMouseClick(x, y, button, true);
		if(main.session != null && !Main.getInstance().getScreenService().isScreenActive())
			main.session.getSpectator().onMouseClick(x, y, button);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		x = (arg0.getX() / Main.SCALE);
		y = (arg0.getY() / Main.SCALE);
		click = false;
		if(Main.getInstance().getScreenService().isScreenActive())
			Main.getInstance().getScreenService().current().onMouseClick(x, y, BUTTON_RELEASED, true);
		if(main.session != null && !Main.getInstance().getScreenService().isScreenActive())
			main.session.getSpectator().onMouseClick(x, y, BUTTON_RELEASED);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(keys.containsKey(arg0.getKeyCode())){
			get(arg0.getKeyCode()).toggle(true, arg0.getKeyChar(), arg0.getModifiers());
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_F5 && arg0.isControlDown())
			Main.getInstance().nextDebugMode();
		
		if(Main.getInstance().getScreenService().isScreenActive()){
			Main.getInstance().getScreenService().current().onKeyWrite(arg0.getKeyChar(), arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE, arg0.getKeyCode() == KeyEvent.VK_ENTER);
			if(keys.containsKey(arg0.getKeyCode())){
				if(Main.getInstance().getScreenService().isScreenActive())
					Main.getInstance().getScreenService().current().onKeyPress(get(arg0.getKeyCode()), true);
				
			}
				
		}else if(main.session != null && keys.containsKey(arg0.getKeyCode()) && !Main.getInstance().getScreenService().isScreenActive())
			main.session.getSpectator().onKeyPress(get(arg0.getKeyCode()), true);
	}
	

	public void keyHeld(Key key){
		if(Main.getInstance().getScreenService().isScreenActive())
			Main.getInstance().getScreenService().current().onKeyPress(key, false);
		
	}
	
	public void mouseHeld(){
		if(Main.getInstance().getScreenService().isScreenActive())
			Main.getInstance().getScreenService().current().onMouseClick(x, y, button, false);
		if(main.session != null && !Main.getInstance().getScreenService().isScreenActive())
			main.session.getSpectator().onMouseClick(x, y, button);
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(keys.containsKey(arg0.getKeyCode())){
			get(arg0.getKeyCode()).toggle(false, arg0.getKeyChar(), arg0.getModifiers());
			if(Main.getInstance().getScreenService().isScreenActive())
				Main.getInstance().getScreenService().current().onKeyPress(get(arg0.getKeyCode()), false);	
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		x = (arg0.getX() / Main.SCALE);
		y = (arg0.getY() / Main.SCALE);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		x = (arg0.getX() / Main.SCALE);
		y = (arg0.getY() / Main.SCALE);
	}
	
	public int getMouseX(){
		return x;
	}
	
	public int getMouseY(){
		return y;
	}
	
	public boolean isMouseOverRectangle(int x, int y, int w, int h){
		return this.x >= x && this.x <= x + w && this.y >= y && this.y <= y + h;
	}
	
	public boolean isMouseOverButton(int x, int y, int w){
		return isMouseOverRectangle(x, y, w * Layer.BLOCK_SIZE, 2 * Layer.BLOCK_SIZE);
	}

	public void update() {
		if(click) mouseHeld();
		for(Key key : keys.values()){
			key.update();
			if(key.clicked) keyHeld(key);
			if(key.down) 
				if(main.session != null && !Main.getInstance().getScreenService().isScreenActive())
					main.session.getSpectator().onKeyPress(key, false);
		}
	}
	
	public Key get(int code){
		return keys.get(code);
	}
	
	public boolean isClicked(){
		return click;
	}
		
	public final Key w = new Key (KeyEvent.VK_W);
	public final Key s = new Key (KeyEvent.VK_S);
	public final Key a = new Key (KeyEvent.VK_A);
	public final Key d = new Key (KeyEvent.VK_D);
		
	public final Key inventory = new Key (KeyEvent.VK_E);
	public final Key escape = new Key (KeyEvent.VK_ESCAPE);
	public final Key space = new Key (KeyEvent.VK_SPACE);
		
	public final Key left = new Key (KeyEvent.VK_LEFT);
	public final Key right = new Key (KeyEvent.VK_RIGHT);

	public class Key{
		public int presses, absorbs;
		public boolean down, clicked;

		public final int code;
		
		public char ch;
		public int modifiers;
		
		public Key(int code) {
			this.code = code;
			keys.put(code, this);
		}

		public void toggle(boolean pressed, char ch, int modifiers) {
			this.modifiers = modifiers;
			this.ch = ch;
			
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void update() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
		
		@Override
		public String toString(){
			return String.format("Key[code=%d, mod=%d, char=%s]", code, modifiers, ch);
		}
	}
	
	@Override
	public String toString(){
		return "InputService{Click=" + click + ", Button=" + button + ", X=" + x + ", Y=" + y + "}";
	}
}
