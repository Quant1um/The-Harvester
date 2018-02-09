package net.quantium.harvester.screen.components;

public interface IValueHolder<T> {
	
	T getValue();
	void setValue(T value);
	void onValueChanged(T value);
}
