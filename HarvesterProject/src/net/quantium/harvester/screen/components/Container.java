package net.quantium.harvester.screen.components;

public class Container extends AbstractContainer<Component>{

	public void addFirst(Component comp) {
		this.comps.add(0, comp);
	}

}
