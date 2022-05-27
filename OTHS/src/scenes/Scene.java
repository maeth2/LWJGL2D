package scenes;

import java.util.ArrayList;
import java.util.List;

import main.Camera;
import main.GameObject;
import main.Tile;
import renderer.Renderer;

public abstract class Scene {	
	
	protected Camera camera;
	protected Renderer renderer = new Renderer();
	private boolean isRunning = false;
	protected List<GameObject> gameObjects = new ArrayList<GameObject>();
	protected List<Tile> tiles = new ArrayList<Tile>();
	
	public Scene() {}
	
	/**
	 * Initializes the scene
	 */
	public void init() {}
	
	/**
	 * Updates the scene
	 * 
	 * @param dt		Time passed per frame
	 */
	public abstract void update(float dt);
	
	/**
	 * Adds a game object to the scene
	 * 
	 * @param g			Game object to add
	 */
	public void addGameObjectToScene(GameObject g) {
		gameObjects.add(g);
		if(isRunning) {
			g.start();
			renderer.add(g);
		}
	}
	
	/**
	 * Adds a tile to the scene
	 * 
	 * @param t			Tile to add
	 */
	public void addTileToScene(Tile t) {
		tiles.add(t);
		if(isRunning) {
			t.start();
			renderer.add(t);
		}
	}
	
	/**
	 * Start function
	 */
	public void start() {
		for(GameObject g : gameObjects) {
			g.start();
			renderer.add(g);
		}
		isRunning = true;
	}
	
	/**
	 * Get scene camera
	 * @return			Scene camera
	 */
	public Camera getCamera() {
		return this.camera;
	}
}
