package components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import util.Textures;

public class SpriteSheet {
	private List<Sprite> sprites = new ArrayList<>();
	
	/**
	 * Initialize sprite sheet
	 * 
	 * @param texture			Texture ID
	 * @param resolution		Sprite resolution
	 */
	public SpriteSheet(int texture, int resolutionX, int resolutionY, int xOffSet, int yOffSet) {
		Vector2f dimensions = Textures.getDimensions(texture);
		float width = (xOffSet + dimensions.x) / resolutionX;
		float height = (yOffSet + dimensions.y) / resolutionY;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width ; j++) {
				float top = (i * resolutionY + resolutionY + yOffSet) / dimensions.y;
				float bottom = (i * resolutionY + yOffSet) / dimensions.y;
				float left = (j * resolutionX + xOffSet) / dimensions.x;
				float right = (j * resolutionX + resolutionX + xOffSet) / dimensions.x;
				Vector2f[] texCoords = {
						new Vector2f(right, bottom),	
						new Vector2f(right, top),
						new Vector2f(left, top),
						new Vector2f(left, bottom),
				};
				sprites.add(new Sprite(texture, texCoords));
			}
		}
	}
	
	/**
	 * Get sprite from sprite sheet
	 * 
	 * @param index			Index of sprite
	 * @return				Sprite
	 */
	public Sprite getSprite(int index) {
		return sprites.get(index);
	}
}
