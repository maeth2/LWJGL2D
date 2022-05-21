package renderer;

import java.util.ArrayList;
import java.util.List;

import components.SpriteRenderer;
import main.GameObject;

public class Renderer {
	private final int MAX_BATCH_SIZE = 1000;
	private List<RenderBatch> batches;
	
	/**
	 * Initializer
	 */
	public Renderer() {
		this.batches = new ArrayList<>();
	}
	
	/**
	 * Add a Game Object to the rendering list
	 * 
	 * @param gameObject 		GameObject to add
	 */
	public void add(GameObject gameObject) {
		SpriteRenderer spr = gameObject.getComponent(SpriteRenderer.class);
		if(spr != null) {
			add(spr);
		}
	}
	
	/**
	 * Add a SpriteRenderer component to a rendering batch
	 * 
	 * @param sprite		SpriteRenderer to add
	 */
	private void add(SpriteRenderer sprite) {
		boolean added = false;
		for(RenderBatch batch : batches) {
			if(batch.hasRoom() && batch.getZIndex() == sprite.gameObject.getZIndex()) {
				int tex = sprite.getTexture();
				if(tex == 0 || batch.hasTexture(tex) || batch.hasTextureRoom()) {
					batch.addSprite(sprite);
					added = true;
					break;
				}
			}
		}
		
		if(!added) {
			RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.getZIndex());
			newBatch.start();
			addBatch(newBatch);
			newBatch.addSprite(sprite);
		}
	}
	
	/**
	 * Add a render batch to pool of batches
	 * 
	 * @param batch			Render Batch to add
	 */
	private void addBatch(RenderBatch batch) {
		int low = 0;
	    int high = batches.size();

	    while (low < high && high != 0) {
	        int mid = low + (high - low) / 2;
	        if (batch.getZIndex() == batches.get(mid).getZIndex()){
	            low = mid + 1;
	            break;
	        } else if (batch.getZIndex() > batches.get(mid).getZIndex()){
	            low = mid + 1;
	        }else{
	            high = mid - 1;
	        }
	    }

	    batches.add(low, batch);
	}
	
	/**
	 * Render method
	 */
	public void render() {
		for(RenderBatch b : batches) {
			b.render();
		}
	}
}
