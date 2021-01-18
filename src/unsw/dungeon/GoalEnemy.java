package unsw.dungeon;

import java.util.ArrayList;

/**
 * An enemy goal. Is a leaf goal as it has no subgoals.
 * Observed by a GoalObserver which can be either Dungeon or a GoalComplex.
 * Observes all enemies in a dungeon to check completion.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalEnemy implements Goal {

	private Dungeon dungeon;
	private ArrayList<Enemy> enemies;
	private int defeated;
	
	/**
	 * Create an enemy goal that knows the dungeon and its observer.
	 * Adds all current enemy entities from dungeon to the list of enemies.
	 * @param dungeon the dungeon
	 * @param observer the composite component this goal is a part of
	 */
	public GoalEnemy(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.enemies = new ArrayList<Enemy>();
		addEnemiesToGoal();
		this.defeated = 0;
	}
	
	/**
	 * Add all enemies currently in dungeon to the goal.
	 */
	public void addEnemiesToGoal() {
		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int j = 0; j < dungeon.getHeight(); j++) {
				ArrayList<Entity> s = dungeon.getGridEntities(i, j);
				s.removeIf(e -> e.getClass() != Enemy.class);
				for (Entity entity : s) {
					Enemy enemy = (Enemy) entity;
					enemies.add(enemy);
					enemy.setGoal(this);
				}
			}
		}
	}

	
	/**
	 * Goal is complete when number of number of enemies defeated == enemy goal amount.
	 */
	@Override
	public boolean checkComplete() {
		return defeated == enemies.size();
	}
	
	/**
	 * Updates the num of defeated. Checks is the goal is complete, if so, notifies observers.
	 * Call this method whenever an enemy is defeated by the player.
	 */
	@Override
	public void updateSelf() {
		defeated++;
		dungeon.updateGoal();
	}

	
	@Override
	public String toString() {
		return "Defeat all enemies";
	}
	
}
