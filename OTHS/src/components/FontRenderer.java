package components;

public class FontRenderer extends Component {
	
	private boolean firstTime = true;

	@Override
	public void start() {
		System.out.println(SpriteRenderer.class + " ATTACHED TO " + gameObject.getName());
		if(gameObject.getComponent(SpriteRenderer.class) != null) {
			System.out.println("FOUND " + SpriteRenderer.class + " IN " + gameObject.getName());
			System.out.println("CREATING " + this.getClass() + "...");
		}
	}
	
	@Override
	public void update(float dt) {
		if(firstTime) {
			System.out.println("UPDATING " + this.getClass() + "!");
			firstTime = false;
		}
	}

}
