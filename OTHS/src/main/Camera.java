 package main;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import listeners.KeyListener;

public class Camera {
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Vector2f position;
	private Transform target;
	private float width, height;
	
	private float speed = 1000.0f;

	public Camera(Vector2f position, float width, float height) {
		this.position = position;
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
		this.width = width;
		this.height = height;
		adjustProjection();
	}

	public void update(float dt) {
		if(target == null) {
			if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_A)) {
				changeX(speed * dt);
			}else if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_D)) {
				changeX(-speed * dt);
			}
			
			if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_S)) {
				changeY(speed * dt);
			}else if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_W)) {
				changeY(-speed * dt);
			}
		}else{
			if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_A)) {
				target.position.x -= speed * dt;
			}else if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_D)) {
				target.position.x += speed * dt;
			}
			
			if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_S)) {
				target.position.y -= speed * dt;
			}else if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_W)) {
				target.position.y += speed * dt;
			}
			this.setPosition(target.position);
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
				new Vector3f(position.x, position.y, 20),
				cameraFront.add(position.x, position.y, 0),
				cameraUp
		);

		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void setTarget(Transform target) {
		this.target = target;
	}
	
	public void setPosition(Vector2f position) {
		this.position.x = position.x - 500;
		this.position.y = position.y - 200;
	}
	
	public void changeX(float dx) {
		this.position.x += dx;
	}
	
	public void setX(float x) {
		this.position.x += x;
	}
	
	public void changeY(float dy) {
		this.position.y += dy;
	}
	
	public void setY(float y) {
		this.position.y += y;
	}
}
