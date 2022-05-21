package components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import main.Transform;

public class SpriteRenderer extends Component{
	
	private Vector4f color;
	private Sprite sprite;
	
	private Transform lastTransform;
	
	private boolean isDirty = true;
	
	/**
	 * Constructs component
	 * 
	 * @param color			Vector4f color
	 */
	public SpriteRenderer(Vector4f color) {
		init(color, new Sprite(0));
	}
	
	/**
	 * Constructs component
	 * 
	 * @param sprite		Sprite object
	 */
	public SpriteRenderer(Sprite sprite) {
		init(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), sprite);
	}
	
	/**
	 * Constructs component
	 * 
	 * @param color			Vector4f color
	 * @param sprite		Sprite object
	 */
	public SpriteRenderer(Vector4f color, Sprite sprite) {
		init(color, sprite);
	}
	
	/**
	 * Initializes component
	 * 
	 * @param color			
	 * @param sprite
	 */
	private void init(Vector4f color, Sprite sprite) {
		this.color = color;
		this.sprite = sprite;
	}
	
	@Override
	public void start() {
		this.lastTransform = gameObject.transform.copy();
	}
	
	@Override
	public void update(float dt) {
		if(!this.lastTransform.equals(this.gameObject.transform)) {
			this.gameObject.transform.copy(this.lastTransform);
			isDirty = true;
		}
	}
	
	public Vector4f getColor() {
		return this.color;
	}
	
	public Vector2f[] getTexCoords() {
		return sprite.getTexCoords();
	}
	
	public int getTexture() {
		return sprite.getTexture();
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		isDirty = true;
	}

	public void setColor(Vector4f color) {
		if(!this.color.equals(color)) {
			this.color.set(color);
			isDirty = true;
		}
	}
	
	public boolean isDirty() {
		return isDirty;
	}
	
	public void setClean() {
		this.isDirty = false;
	}
}
