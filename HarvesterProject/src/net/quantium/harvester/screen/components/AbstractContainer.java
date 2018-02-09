package net.quantium.harvester.screen.components;

import java.util.ArrayList;
import java.util.List;

import net.quantium.harvester.input.InputService.Key;
import net.quantium.harvester.input.MouseState;
import net.quantium.harvester.render.Renderer;

public class AbstractContainer<T extends Component> extends Component{
	protected List<T> comps = new ArrayList<T>();
	protected int focused = -1;
	
	@Override
	public void render(Renderer render, boolean tfocused){
		for(int i = 0; i < comps.size(); i++)
			comps.get(i).render(render, focused == i);
	}
	
	@Override
	public void onMouseClick(int x, int y, MouseState button, boolean first){
		int c = getIndexOn(x, y);
		if(c >= 0){
			if(focused != c){
				focused = c;
				comps.get(c).onSelect();
			}
		}else
			focused = -1;
		if(focused >= 0 && focused < comps.size())
			comps.get(focused).onMouseClick(x, y, button, first);
	}
	
	@Override
	public void onKeyPress(Key key, boolean first){
		if(focused >= 0 && focused < comps.size())
			comps.get(focused).onKeyPress(key, first);
	}
	
	@Override
	public void onMouseWheel(int ticks){
		if(focused >= 0 && focused < comps.size())
			comps.get(focused).onMouseWheel(ticks);
	}
	
	@Override
	public void onKeyWrite(char key, boolean backspace, boolean submit) {
		if(focused >= 0 && focused < comps.size())
			comps.get(focused).onKeyWrite(key, backspace, submit);
	}
	
	@Override
	public void update(){
		for(int i = 0; i < comps.size(); i++)
			comps.get(i).update();
	}
	
	public int getIndexOn(int x, int y){
		for(int i = 0; i < comps.size(); i++){
			Component c = comps.get(i);
			if(x >= c.getX() && y >= c.getY() && x <= c.getX() + c.getWidth() && y <= c.getY() + c.getHeight())
				return i;
		}
		return -1;
	}
	
	public Component getFocused(){
		if(focused >= 0 && focused < comps.size())
			return comps.get(focused);
		return null;
	}
	
	public void add(T c){
		comps.add(c);
	}
	
	public void add(int id, T c){
		comps.add(id, c);
	}
	
	public void addFirst(T c){
		add(0, c);
	}
	
	public void remove(T c){
		focused = -1;
		comps.remove(c);
	}

	@Override
	public void onSelect() {
		
	}
	
	public void render(Renderer render){
		render(render, false);
	}
}
