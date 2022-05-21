package components;

import java.util.ArrayList;
import java.util.List;

import util.AssetLoader;

public class AnimationState {
	private class Frame {
		private Sprite sprite;
		private float frameTime;		
		public Frame(Sprite sprite, float frameTime) {
			this.sprite = sprite;
			this.frameTime = frameTime;
		}
		
	}
	
	public String name;
	private List<Frame> frames = new ArrayList<Frame>();
	private Sprite defaultSprite = new Sprite(AssetLoader.getTexture("assets/textures/unknown.png"));
	private float timeLeft;
	private int currentFrame;
	private boolean isLooping;
	private boolean doneLoop;
	
	public AnimationState(String name, boolean isLooping) {
		this.name = name;
		this.isLooping = isLooping;
	}
	
	public void addFrame(Sprite sprite, float frameTime) {
		frames.add(new Frame(sprite, frameTime));
	}
	
	public void setLoop(boolean loop) {
		this.isLooping = loop;
	}
	
	public boolean isDoneLoop() {
		return this.doneLoop;
	}
	
	public void reset() {
		this.doneLoop = false;
		this.currentFrame = 0;
	}
	
	public void update(float dt) {
		if(currentFrame < frames.size()) {
			timeLeft -= dt;
			if(timeLeft <= 0) {
				if(currentFrame == frames.size() - 1) doneLoop = true;
				if(currentFrame != frames.size() - 1 || isLooping) {
					currentFrame = (currentFrame + 1) % frames.size();
					timeLeft = frames.get(currentFrame).frameTime;
				}
			}
		}
	}
	
	public Sprite getCurrentSprite() {
		if(currentFrame < frames.size()) {
			return frames.get(currentFrame).sprite;
		}
		return defaultSprite;
	}
}
