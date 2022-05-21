package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StateMachine extends Component{
	private class StateTrigger{
		public String state;
		public String trigger;
		
		public StateTrigger(String state, String trigger) {
			this.state = state;
			this.trigger = trigger;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o.getClass() != StateTrigger.class) return false;
			StateTrigger t2 = (StateTrigger) o;
			return t2.trigger.equals(this.trigger) && t2.state.equals(this.state);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(trigger, state);
		}
	}
	
	public HashMap<StateTrigger, String> stateTransfers = new HashMap<>();
	private List<AnimationState> states = new ArrayList<>();
	private AnimationState currentState = null;
	private String defaultStateTitle = "";
	
	/**
	 * Add a state transfer connection
	 * 
	 * @param from				Animation to transition from
	 * @param to				Animation to transition to
	 * @param onTrigger			Trigger to start transition
	 */
	public void addStateTrigger(String from, String to, String onTrigger) {
		this.stateTransfers.put(new StateTrigger(from, onTrigger), to);
	}
	
	/**
	 * Add a animation state
	 * 
	 * @param state		Animation state to add
	 */
	public void add(AnimationState state) {
		this.states.add(state);
	}
	
	/**
	 * Trigger animation change
	 * 
	 * @param trigger			Trigger String
	 */
	public void trigger(String trigger) {
		if(currentState.isDoneLoop()) {
			for(StateTrigger s : stateTransfers.keySet()) {
				if(s.state.equals(currentState.name) && s.trigger.equals(trigger)) {
					if(stateTransfers.get(s) != null) {
						int newIndex = -1;
						int index = 0;
						for(AnimationState s2 : states) {
							if(s2.name.equals(stateTransfers.get(s))) {
								newIndex = index;
							}
							index++;
						}
						
						if(newIndex > -1) {
							currentState = states.get(newIndex);
							currentState.reset();
						}
					}
					return;
				}
			}
		}
	}
	
	/**
	 * Sets the current state
	 * @param currentState
	 */
	public void setCurrentState(AnimationState currentState) {
		this.currentState = currentState;
	}
	
	/**
	 * Sets the default state
	 * @param state
	 */
	public void setDefaultState(String state) {
		this.defaultStateTitle = state;
	}

	@Override
	public void start() {
		for(AnimationState state : states) {
			if(state.name.equals(defaultStateTitle)) {
				currentState = state;
				break;
			}
		}
	}
	
	@Override
	public void update(float dt) {
		if(currentState != null) {
			currentState.update(dt);
			SpriteRenderer sprite = gameObject.getComponent(SpriteRenderer.class);
			if(sprite != null) {
				sprite.setSprite(currentState.getCurrentSprite());
			}
		}
	}
}
