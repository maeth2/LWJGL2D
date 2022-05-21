package components;

import org.joml.Vector2f;

public class Sprite {
	private int texture;
	
	private Vector2f[] texCoords = {
			new Vector2f(1, 1),
			new Vector2f(0, 1),
			new Vector2f(0, 0),
			new Vector2f(1, 0),	
	};
	
	public Sprite(int texture) {
		init(texture, texCoords);
	}
	
	public Sprite(int texture, Vector2f[] texCoords) {
		init(texture, texCoords);
	}
	
	private void init(int texture, Vector2f[] texCoords) {
		this.texture = texture;		
		this.texCoords = texCoords;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f[] getTexCoords() {
		return texCoords;
	}
}
