package net.quantium.harvester.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.quantium.harvester.Main;

public class InputService implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener{
	private Main main;
	
	private int x, y;
	private MouseState mouseState = MouseState.UNDEFINED;
	
	private Map<Integer, Key> keys = new HashMap<Integer, Key>();
	
	public InputService(Main main){
		this.main = main;
		main.addKeyListener(this);
		main.addMouseListener(this);
		main.addMouseWheelListener(this);
		main.addMouseMotionListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		main.onMouseWheel(arg0.getWheelRotation());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent event) {
		x = (event.getX() / Main.SCALE);
		y = (event.getY() / Main.SCALE);
		mouseState = MouseState.fromMouseEvent(event);
		main.onMouseClick(x, y, mouseState, true);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		x = (event.getX() / Main.SCALE);
		y = (event.getY() / Main.SCALE);
		mouseState = MouseState.RELEASED;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(keys.containsKey(event.getKeyCode())){
			Key key = get(event.getKeyCode());
			if(!key.down){
				key.toggle(true, event.getKeyChar(), event.getModifiers());
				main.onKeyPress(key, true);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		if(keys.containsKey(event.getKeyCode())){
			Key key = get(event.getKeyCode());
			if(key.down)
				key.toggle(false, event.getKeyChar(), event.getModifiers());
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

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
	
	public void update() {
		if(isClicked())
			main.onMouseClick(x, y, mouseState, false);
		for(Key key : keys.values()){
			key.update();
			if(key.down)
				main.onKeyPress(key, false);
		}
	}
	
	public Key get(int code){
		return keys.get(code);
	}
	
	public boolean isClicked(){
		return mouseState != MouseState.UNDEFINED && mouseState != MouseState.RELEASED;
	}
		
	public Collection<Key> keys(){
		return keys.values();
	}
	
	public final Key w = new Key(KeyEvent.VK_W);
	public final Key s = new Key(KeyEvent.VK_S);
	public final Key a = new Key(KeyEvent.VK_A);
	public final Key d = new Key(KeyEvent.VK_D);
		
	public final Key inventory = new Key(KeyEvent.VK_E);
	public final Key escape = new Key(KeyEvent.VK_ESCAPE);
	public final Key space = new Key(KeyEvent.VK_SPACE);
		
	public final Key debug = new Key(KeyEvent.VK_F5);

	public class Key{
		public int presses, absorbs;
		private boolean down, clicked, suppressed;

		public final int code;
		
		private char character;
		private int modifiers;
		
		public Key(int code) {
			this.code = code;
			keys.put(code, this);
		}

		public void toggle(boolean pressed, char ch, int modifiers) {
			this.modifiers = modifiers;
			this.character = ch;
			
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public char getCharacter(){
			return character;
		}
		
		public int getModifiers(){
			return modifiers;
		}
		
		public boolean isDown(){
			if(suppressed) return false;
			return down;
		}
		
		public boolean isClicked(){
			if(suppressed) return false;
			return clicked;
		}
		
		public boolean isSuppressed(){
			return suppressed;
		}
		
		public void update() {
			suppressed = false;
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
		
		public void suppress(){
			suppressed = true;
		}
		
		@Override
		public String toString(){
			return String.format("Key{Code=%d, Mod=%d, Char=%s}", code, modifiers, character);
		}
	}
	
	@Override
	public String toString(){
		return "InputService{Mouse=" + mouseState + ", X=" + x + ", Y=" + y + "}";
	}
}
