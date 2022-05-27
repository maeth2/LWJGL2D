package scenes;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import components.AnimationState;
import components.SpriteRenderer;
import components.StateMachine;
import listeners.KeyListener;
import main.Camera;
import main.GameObject;
import main.Transform;
import util.AssetLoader;
import util.Loader;

public class TestScene extends Scene{
	
	int costume = 0;
	float spriteFrameTime = 0.2f;
	float spriteFrameTimeLeft = 0.0f;
	
	private GameObject p;
    
	public TestScene() {
		init();
	}
	
	@Override
	public void init() {
		/*
		 * Create scene Camera
		 */
		camera = new Camera(new Vector2f(), 32 * 40, 32 * 21);
		
		Loader.loadLevel("assets/levels/level.txt", this);
		
		p = new GameObject(
				"OBJ", 
				new Transform(new Vector2f(0, 0), new Vector2f(128, 128)),
				100
		);
		p.addComponent(new SpriteRenderer(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0)));
		p.addComponent(new StateMachine());
		StateMachine states = p.getComponent(StateMachine.class);
		AnimationState idle = new AnimationState("IDLE", true);
		idle.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0), 0.5f);
		idle.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(1), 0.5f);
		states.add(idle);
		AnimationState attacking = new AnimationState("RUNNING", true);
		attacking.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0), 0.5f);
		attacking.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(2), 0.5f);
		states.add(attacking);
		states.addStateTrigger("IDLE", "RUNNING", "MOVE");
		states.addStateTrigger("RUNNING", "IDLE", "STOP");
		states.setDefaultState("IDLE");
		addGameObjectToScene(p);
		
//		for(int i = 0; i < 10000; i++) {
//			GameObject go2 = new GameObject(
//					"OBJ2", 
//					new Transform(new Vector2f((float)Math.random() * 1000, (float)Math.random() * 1000), new Vector2f(50, 50)),
//					(int)Math.random() * 5
//			);
//			go2.addComponent(new SpriteRenderer(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0)));
//			go2.addComponent(new StateMachine());
//			states = go2.getComponent(StateMachine.class);
//			idle = new AnimationState("IDLE", true);
//			idle.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0), 0.5f);
//			idle.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(1), 0.5f);
//			states.add(idle);
//			attacking = new AnimationState("RUNNING", true);
//			attacking.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(0), 0.5f);
//			attacking.addFrame(AssetLoader.getSpriteSheet("assets/textures/test.png", 32, 32).getSprite(2), 0.5f);
//			states.add(attacking);
//			states.addStateTrigger("IDLE", "RUNNING", "MOVE");
//			states.addStateTrigger("RUNNING", "IDLE", "STOP");
//			states.setDefaultState("IDLE");
//			addGameObjectToScene(go2);
//		}
		
		camera.setTarget(p.transform);
	}

	@Override
	public void update(float dt) {
		camera.update(dt);
		if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_E)) {
			p.getComponent(StateMachine.class).trigger("MOVE");
		}else {
			p.getComponent(StateMachine.class).trigger("STOP");
		}
		for(GameObject g : gameObjects) {
			g.update(dt);
		}
		
		this.renderer.render();
	}
}
