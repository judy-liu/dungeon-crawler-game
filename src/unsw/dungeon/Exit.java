package unsw.dungeon;

/**
 * The exit entity.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Exit extends Entity {
	
	private GoalExit goal;
	
	/**
	 * Create an exit in given dungeon
	 * @param x x-coordinate of exit
	 * @param y y-coordinate of exit
	 * @param dungeon the dungeon
	 */
	public Exit( int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.goal = null;
	}
	
	/**
	 * Set the exit goal if there is one.
	 * @param goal an exit goal for this dungeon
	 */
	public void setGoal(GoalExit goal) {
		this.goal = goal;
	}

	/**
	 * If there is an exit goal, the goal must be updated when collided with player.
	 */
	@Override
	public void collide(Player player) {
		if (goal != null) goal.updateSelf();
	}

	/**
	 * Exits are solid so boulder cannot be pushed onto it.
	 */
	public boolean isSolid() {
		return true;
	}

	/**
	 * If a player leaves the square, the goal is updated to be incomplete.
	 */
	@Override
	public void update() {
		if (goal != null) goal.updateSelf();
	}

}
