package renderer;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import components.SpriteRenderer;
import main.Window;
import util.AssetLoader;
import util.Helper;
import util.Shaders;
import util.Textures;

public class RenderBatch {
	//Vertex
	//=========
	//Pos					Color							Tex coords		Tex id
	//float, float,			float, float, float ,float,		float, float,	float
	
	private final int VERTEX_SIZE = 9;
		
	private float[] vertices;

	private final int[][] VERTEX_GROUPS = {
			{0, 2},
			{1, 4},
			{2, 2},
			{3, 1},
	};
	
	private List<Integer> textures = new ArrayList<>();
	private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};
	
	private SpriteRenderer[] sprites;
	private int numSprites;
	private boolean hasRoom = true;
	
	private int vaoID, vboID;
	private int maxBatchSize;
	private int shaderID;
	
	private int zIndex;
	
	/**
	 * Initializer
	 * @param maxBatchSize		Maximum batch size
	 * @param zIndex			Z coordinate of batch
	 */
	public RenderBatch(int maxBatchSize, int zIndex) {
		shaderID = AssetLoader.getShader("assets/shaders/default");
		this.maxBatchSize = maxBatchSize;
		this.sprites = new SpriteRenderer[maxBatchSize];
		
		// 4 vertices quads
		vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
		
		this.numSprites = 0;
		this.hasRoom = true;
		
		this.zIndex = zIndex;
	}
	
	/**
	 * Start method
	 */
	public void start() {
		// Generate VAO
		vaoID = Helper.generateVAO();
		vboID = Helper.storeDataInAttributeList(vaoID, VERTEX_SIZE, VERTEX_SIZE * maxBatchSize, VERTEX_GROUPS);
		
		int[] indices = generateIndices();
		Helper.generateIndicesBuffer(vaoID, indices);
	}
	
	/**
	 * Add SpriteRenderer to batch
	 * 
	 * @param sprite SpriteRenderer to add
	 */
	public void addSprite(SpriteRenderer sprite) {
		int index = numSprites;
		sprites[index] = sprite;
		numSprites++;
		
		int texID = sprite.getTexture();
		if(texID != 0) {
			if(!textures.contains(texID)) {
				textures.add(texID);
			}
		}
		
		loadVertexProperties(index);
		
		if(numSprites >= maxBatchSize) {
			hasRoom = false;
		}
	}
	
	/**
	 * Render method
	 */
	public void render() {
		boolean rebufferData = false;
		for(int i = 0; i < numSprites; i++) {
			SpriteRenderer spr = sprites[i];
			if(spr.isDirty()) {
				loadVertexProperties(i);
				spr.setClean();
				rebufferData = true;
			}
		}
		
		if(rebufferData) {
			//Stores vertices into VBO
			Helper.storeDataInAttributeList(vaoID, vboID, VERTEX_SIZE, VERTEX_GROUPS, vertices);
		}
		
		//Bind and load shader
		Shaders.useShader(shaderID);
		Shaders.loadMatrix(shaderID, "uProjection", Window.getScene().getCamera().getProjectionMatrix());
		Shaders.loadMatrix(shaderID, "uView", Window.getScene().getCamera().getViewMatrix());
		
		for(int i = 0; i < textures.size(); i++) {
			Textures.loadTextureToShader(shaderID, textures.get(i), i + 1);
		}
		
		Shaders.loadIntArray(shaderID, "uTextures", texSlots);
		// Bind the VAO
		glBindVertexArray(vaoID);

		// Enable the vertex attribute pointers
		glEnableVertexAttribArray(0); 
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES, numSprites * 6, GL_UNSIGNED_INT, 0);
		
		//Unbind
		Shaders.unbindShader();
		for(int i = 0; i < textures.size(); i++) {
			Textures.unbindTexture(shaderID, i + 1);
		}
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
	/**
	 * Load vertex data based on sprite gameObject data
	 * 
	 * @param index			Index on the vertices array
	 */
	private void loadVertexProperties(int index) {
		SpriteRenderer sprite = this.sprites[index];
		
		int offset = index * 4 * VERTEX_SIZE;
		
		Vector4f color = sprite.getColor();
		Vector2f[] texCoords = sprite.getTexCoords();
		int texID = 0;
		if(sprite.getTexture() != 0) {
			for(int i = 0; i < textures.size(); i++) {
				if(textures.get(i) == sprite.getTexture()) {
					texID = i + 1;
					break;
				}
			}
		}
			
		float xAdd = 1.0f;
		float yAdd = 1.0f;
		for(int i = 0; i < 4; i++) {
			if(i == 1) {
				yAdd = 0.0f;
			}else if(i == 2) {
				xAdd = 0.0f;
			}else if(i == 3) {
				yAdd = 1.0f;
			}
			
			vertices[offset] = sprite.gameObject.transform.position.x + (xAdd * sprite.gameObject.transform.scale.x);
			vertices[offset + 1] = sprite.gameObject.transform.position.y + (yAdd * sprite.gameObject.transform.scale.y);
			
			vertices[offset + 2] = color.x;
			vertices[offset + 3] = color.y;
			vertices[offset + 4] = color.z;
			vertices[offset + 5] = color.w;
			
			vertices[offset + 6] = texCoords[i].x;
			vertices[offset + 7] = texCoords[i].y;
			
			vertices[offset + 8] = texID;
			
			offset += VERTEX_SIZE;
		}
	}
	
	/**
	 * Generate Indices array for vertices
	 * 
	 * @return indices array
	 */
	private int[] generateIndices() {
		int[] elements = new int[6 * maxBatchSize];
		for(int i = 0; i < maxBatchSize; i++) {
			int offsetArrayIndex = 6 * i;
			int offset = 4 * i;
			
			elements[offsetArrayIndex] = 3 + offset;
			elements[offsetArrayIndex + 1] = 2 + offset;
			elements[offsetArrayIndex + 2] = 0 + offset;
			elements[offsetArrayIndex + 3] = 0 + offset;
			elements[offsetArrayIndex + 4] = 2 + offset;
			elements[offsetArrayIndex + 5] = 1 + offset;

		}
		return elements;
	}
	
	/**
	 * Checks if batch has room for more sprites
	 * 
	 * @return			If batch has room
	 */
	public boolean hasRoom() {
		return hasRoom;
	}
	
	/**
	 * Checks if batch has room for texture
	 * 
	 * @return			 If texture list has room
	 */
	public boolean hasTextureRoom() {
		return this.textures.size() < 8;
	}
	
	/**
	 * Checks if batch contains texture
	 * 
	 * @param texture		Texture to check
	 * @return				If contains texture
	 */
	public boolean hasTexture(int texture) {
		return this.textures.contains(texture);
	}
	
	public int getZIndex() {
		return this.zIndex;
	}
}
