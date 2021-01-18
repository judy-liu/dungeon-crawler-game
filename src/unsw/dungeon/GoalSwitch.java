package unsw.dungeon;

import java.util.ArrayList;

/**
 * A switch goal. Is a leaf goal as it has no subgoals.
 * Observed by a GoalObserver which can be either Dungeon or a GoalComplex.
 * Observes all switches in a dungeon to check completion.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalSwitch implements Goal {

	private Dungeon dungeon;
	private ArrayList<Switch> switches;
	private int switched;
	
	/**
	 * Create a switch/boulder goal that knows the dungeon and its observer.
	 * Adds all current switch entities from dungeon to the list of switches.
	 * @param dungeon the dungeon
	 * @param observer the composite component this goal is a part of
	 */
	public GoalSwitch(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.switches = new ArrayList<Switch>();
		addSwitchesToGoal();
		this.switched = 0;
	}
	
	/**
	 * Add all switches currently in dungeon to the goal.
	 */
	public void addSwitchesToGoal() {
		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int j = 0; j < dungeon.getHeight(); j++) {
				ArrayList<Entity> s = dungeon.getGridEntities(i, j);
				s.removeIf(e -> e.getClass() != Switch.class);
				for (Entity entity : s) {
					Switch swit = (Switch) entity;
					switches.add(swit);
					swit.setGoal(this);
				}
			}
		}
	}
	
	/**
	 * Goal is complete when number of number of enemies defeated == enemy goal amount.
	 */
	@Override
	public boolean checkComplete() {
		return switched == switches.size();
	}
	
	/**
	 * Updates the num of switched. Checks is the goal is complete, if so, notifies observers.
	 * switch ++ when switch is triggered, switch -- when switch is untriggered.
	 * @param triggered
	 */
	@Override
	public void updateSelf() {
		int count =  0;
		// check which switches are off and which ones are on
		for (Switch swit : switches) {
			if (swit.isTriggered()) {
				count++;
			}
		}
		switched = count;
		dungeon.updateGoal();
	}
		
	/**
	 * Get size of switches array. Used for unit testing.
	 * @return
	 */
	public int numberObserved() {
		return switches.size();
	}

	@Override
	public String toString() {
		return "Trigger all switches";
	}
	
}
