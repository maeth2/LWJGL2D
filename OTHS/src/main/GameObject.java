package main;

import java.util.ArrayList;
import java.util.List;

import components.Component;

public class GameObject {
	private String name;
	private List<Component> components = new ArrayList<Component>();
	private int zIndex = 0;
	public Transform transform;
	
	public GameObject(String name) {
		init(name, new Transform(), 0);
	}
	
	public GameObject(String name, Transform transform, int zIndex) {
		init(name, transform, zIndex);
	}
	
	public void init(String name, Transform transform, int zIndex) {
		this.name = name;
		this.transform = transform;
		this.zIndex = zIndex;
	}
	
	public <T extends Component> T getComponent(Class<T> componentClass) {
		for(Component c : components) {
			if(componentClass.isAssignableFrom(c.getClass())) {
				return componentClass.cast(c);
			}
		}
		return null;
	}
	
	public <T extends Component> void removeComponent(Class<T> componentClass) {
		for(int i = 0; i < components.size(); i++) {
			Component c = components.get(i);
			if(componentClass.isAssignableFrom(c.getClass())) {
				components.remove(i);
				return;
			}
		}
	}
	
	public void addComponent(Component c) {
		components.add(c);
		c.gameObject = this;
	}
	
	public void update(float dt) {
		for(Component c : components) {
			c.update(dt);
		}
	}
	
	public void start() {
		for(Component c : components) {
			c.start();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getZIndex() {
		return this.zIndex;
	}
}
