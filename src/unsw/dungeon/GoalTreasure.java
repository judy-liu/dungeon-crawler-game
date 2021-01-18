package unsw.dungeon;

import java.util.ArrayList;

/**
 * A treasure goal. Is a leaf goal as it has no subgoals.
 * Observed by a GoalObserver which can be either Dungeon or a GoalComplex.
 * Observes all treasure in a dungeon to check completion.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalTreasure implements Goal {

	private Dungeon dungeon;
	private ArrayList<Treasure> treasure;
	private int collected;
	
	/**
	 * Create an treasure goal that knows the dungeon and its observer.
	 * Adds all current treasure entities from dungeon to the list of treasure.
	 * @param dungeon the dungeon
	 * @param observer the composite component this goal is a part of
	 */
	public GoalTreasure(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.treasure = new ArrayList<Treasure>();
		addTreasureToGoal();
		this.collected = 0;
	}
	
	/**
	 * Add all instances of treasure in a dungeon to the treasure goal amount.
	 * Also sets the goal of all instances of treasure in a dungeon to the GoalTreasure instance.
	 * 
	 */
	public void addTreasureToGoal() {
		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int j = 0; j < dungeon.getHeight(); j++) {
				ArrayList<Entity> t = dungeon.getGridEntities(i, j);
				t.removeIf(e -> e.getClass() != Treasure.class);
				for (Entity entity : t) {
					Treasure tres = (Treasure) entity;
					treasure.add(tres);
					tres.setGoal(this); // add this goal to the treasure
				}
			}
		}
	}

	/**
	 * Goal is complete when number of number of treasure collected == treasure goal amount.
	 */
	@Override
	public boolean checkComplete() {
		return collected == treasure.size();
	}
	
	/**
	 * Updates the num of collected in Goal Treasure instance. Checks is the goal is complete, if so, notifies observers.
	 * Call this method whenever a treasure is collected by the player.
	 */
	@Override
	public void updateSelf() {
		collected++;
		dungeon.updateGoal();
	}

	
	/**
	 * Get amount of treasure collected. Used for unit testing.
	 * @return collected
	 */
	public int getCollected() {
		return this.collected;
	}
	
	/**
	 * Get size of treasure array. Used for unit testing.
	 * @return
	 */
	public int numberObserved() {
		return treasure.size();
	}
	
	@Override
	public String toString() {
		return "Collect all treasure";
	}
	
}
