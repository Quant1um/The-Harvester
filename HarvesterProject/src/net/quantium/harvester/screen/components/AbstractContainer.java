package net.quantium.harvester.screen.components;

import java.util.ArrayList;
import java.util.List;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.input.TextModifiers;
import net.quantium.harvester.render.Renderer;

public class AbstractContainer<T extends Component> extends Component{
	protected List<T> comps = new ArrayList<T>();
	protected T focused = null;
	
	@Override
	public void render(Renderer render){
		for(int i = 0; i < comps.size(); i++)
			comps.get(i).render(render);
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first){
		T comp = getComponentOn(x, y);
		if(comp != null){
			setFocused(comp);
		}else
			focused = null;
		
		if(hasFocused())
			focused.onMouseClick(x, y, button, first);
	}
	
	@Override
	public void onKeyPress(Key key, boolean first){
		if(hasFocused())
			focused.onKeyPress(key, first);
	}
	
	@Override
	public void onMouseWheel(int ticks){
		if(hasFocused())
			focused.onMouseWheel(ticks);
	}
	
	@Override
	public void onTextInput(char key, TextModifiers mod) {
		if(hasFocused())
			focused.onTextInput(key, mod);
	}
	
	@Override
	public void update(){
		for(int i = 0; i < comps.size(); i++)
			comps.get(i).update();
	}
	
	public T getComponentOn(int x, int y){
		for(int i = 0; i < comps.size(); i++){
			T c = comps.get(i);
			if(x >= c.getX() && y >= c.getY() && x <= c.getX() + c.getWidth() && y <= c.getY() + c.getHeight())
				return c;
		}
		return null;
	}
	
	public T getFocused(){
		return focused;
	}
	
	public void setFocused(T comp){
		if(this.focused == comp) return;
		if(hasFocused()) focused.setFocused(false);
		if(comp != null) comp.setFocused(true);
		this.focused = comp;
	}
	
	public boolean hasFocused(){
		return focused != null;
	}
	
	public void add(T comp){
		comps.add(comp);
	}
	
	public void add(int id, T comp){
		comps.add(id, comp);
	}
	
	public void addFirst(T comp){
		add(0, comp);
	}
	
	public void remove(T comp){
		if(comp == focused)
			focused = null;
		comps.remove(comp);
	}
	
	public void clear(){
		comps.clear();
	}
}
