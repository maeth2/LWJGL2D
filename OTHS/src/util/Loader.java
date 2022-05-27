package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import components.SpriteRenderer;
import main.Tile;
import scenes.Scene;

public class Loader {
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	
	/**
	 * Loads level txt file into game
	 * 
	 * @param path			Directory to the file
	 * @param scene			Scene to load into
	 */
	public static void loadLevel(String path, Scene scene){
		File file = new File(path);
		try {
			int width;
			int height;
			String name;
	        Scanner sc;
			sc = new Scanner(file);
			name = sc.nextLine();
	        System.out.println(name);
	        width = sc.nextInt();
	        height = sc.nextInt();
	        for(int r = height - 1; r >= 0; r--) {
	        	for(int c = 0; c < width; c++) {
	        		int i = sc.nextInt();
	        		if(i == 0) continue;
	        		Tile t = new Tile("tile", WIDTH, HEIGHT, c * WIDTH, r * HEIGHT, 0);
	        		t.addComponent(
	        				new SpriteRenderer(
	        						AssetLoader.getSpriteSheet("assets/textures/floors.png", 16, 16).getSprite(i - 1)
	        				)
	        		);
	        		scene.addGameObjectToScene(t);
	        	}
	        }
	        for(int r = height - 1; r >= 0; r--) {
	        	for(int c = 0; c < width; c++) {
	        		int i = sc.nextInt();
	        		if(i == 0) continue;
	        		Tile t = new Tile("tile", WIDTH, HEIGHT, c * WIDTH, r * HEIGHT, 1);
	        		t.addComponent(
	        				new SpriteRenderer(
	        						AssetLoader.getSpriteSheet("assets/textures/walls.png", 16, 16).getSprite(i - 1)
	        				)
	        		);
	        		scene.addGameObjectToScene(t);
	        	}
	        }
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
