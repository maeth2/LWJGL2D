package main;

import org.joml.Vector2f;

public class Tile extends GameObject{
	public Tile(String name, int w, int h, int x, int y, int z) {
		super(
			name, 
			new Transform(new Vector2f(x, y), new Vector2f(w, h)),
			z
		);
	}
}
