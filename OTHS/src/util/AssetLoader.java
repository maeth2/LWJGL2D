package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import components.SpriteSheet;

public class AssetLoader {
	public static Map<String, Integer> textures = new HashMap<>();
	public static Map<String, Integer> shaders = new HashMap<>();
	public static Map<String, SpriteSheet> spriteSheets = new HashMap<>();
	
	/**
	 * Loads texture from assets
	 * 
	 * @param resourceName			Image file path
	 * @return						Texture ID
	 */
	public static int getTexture(String resourceName) {
		File file = new File(resourceName);
		String path = file.getAbsolutePath();
		if(textures.containsKey(path)) {
			return textures.get(path);
		}else {
			int textureID = Textures.loadTexture(resourceName);
			if(textureID > -1) {
				textures.put(path, textureID);
			}
			return textureID;
		}
	}
	
	/**
	 * Loads shader from assets
	 * 
	 * @param resourceName			Shader file path
	 * @return						Shader ID
	 */
	public static int getShader(String resourceName) {
		File file = new File(resourceName);
		String path = file.getAbsolutePath();
		if(shaders.containsKey(path)) {
			return shaders.get(path);
		}else {
			int shaderID = Shaders.buildShader(resourceName);
			if(shaderID > -1) {
				shaders.put(path, shaderID);
			}
			return shaderID;
		}
	}
	
	/**
	 * Loads sprite sheet from assets
	 * 
	 * @param resourceName			Sprite sheet file path
	 * @param resolution			Resolution per sprite
	 * @return						Sprite sheet
	 */
	public static SpriteSheet getSpriteSheet(String resourceName, int resolutionX, int resolutionY) {
		File file = new File(resourceName);
		String path = file.getAbsolutePath();
		if(spriteSheets.containsKey(path)) {
			return spriteSheets.get(path);
		}else {
			int texture = getTexture(resourceName);
			if(texture > -1) {
				SpriteSheet spriteSheet = new SpriteSheet(texture, resolutionX, resolutionY, 0, 0);
				spriteSheets.put(path, spriteSheet);
				return spriteSheet;
			}
			return getSpriteSheet("assets/textures/unknown.png", 32, 32, 0, 0);
		}
	}
	
	/**
	 * Loads sprite sheet from assets
	 * 
	 * @param resourceName			Sprite sheet file path
	 * @param resolution			Resolution per sprite
	 * @param xOffSet				X offset of spritesheet
	 * @param yoffSet				Y offset of spritesheet
	 * @return						Sprite sheet
	 */
	public static SpriteSheet getSpriteSheet(String resourceName, int resolutionX, int resolutionY, int xOffSet, int yOffSet) {
		File file = new File(resourceName);
		String path = file.getAbsolutePath();
		if(spriteSheets.containsKey(path)) {
			return spriteSheets.get(path);
		}else {
			int texture = getTexture(resourceName);
			if(texture > -1) {
				SpriteSheet spriteSheet = new SpriteSheet(texture, resolutionX, resolutionY, xOffSet, yOffSet);
				spriteSheets.put(path, spriteSheet);
				return spriteSheet;
			}
			return getSpriteSheet("assets/textures/unknown.png", 32, 32, 0, 0);
		}
	}
}
