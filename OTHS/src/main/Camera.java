 package main;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import components.Controller;

public class Camera extends GameObject{
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private GameObject target;
	private float width, height;
	
	public Camera(Vector2f position, float width, float height) {
		super("Camera");
		this.transform.position = position;
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
		this.width = width;
		this.height = height;
		this.addComponent(new Controller());
		adjustProjection();
	}
	
	@Override
	public void update(float dt) {
		if(target != null) {
			if(target.getComponent(Controller.class) != null) {
				target.getComponent(Controller.class).updateController(dt);
			}
			this.setPosition(target.transform.position);
		}else {
			this.getComponent(Controller.class).updateController(dt);
		}
	}
	
	public void adjustProjection() {
		projectionMatrix.identity();
		projectionMatrix.ortho(0.0f, width, 0.0f, height, 0.0f, 100.0f);
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
		Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
		viewMatrix.identity();
		viewMatrix.lookAt(	
				new Vector3f(this.transform.position.x, this.transform.position.y, 20),
				cameraFront.add(this.transform.position.x, this.transform.position.y, 0),
				cameraUp
		);

		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void setTarget(GameObject target) {
		this.target = target;
	}
	
	public void setPosition(Vector2f position) {
		this.transform.position.x = position.x - Window.WIDTH / 4;
		this.transform.position.y = position.y - Window.HEIGHT / 4;
	}
}
