package unsw.dungeon;

/**
 * The switch entity.
 * Can be triggered and untriggered by moving boulders on and off them.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Switch extends Entity {

	private boolean triggered;
	private GoalSwitch goal;
	
	/**
	 * Constructor for Switch
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon the switch is in
	 */
	public Switch(Dungeon dungeon, int x, int y) {
		super(x, y, dungeon);
	}

	/**
	 * Check if boulder is triggered.
	 * @return
	 */
	public boolean isTriggered() {
		return triggered;
	}

	/**
	 * Set a goal for the switch to belong to.
	 * @param goal the switch goal
	 */
	public void setGoal(GoalSwitch goal) {
		this.goal = goal;
	}
	
	/**
	 * Checks if the switch is triggered or not and updates goal accordingly.
	 */
	@Override
	public void update() {
		boolean trig = false;
		for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
			if (e != null && e.getClass() == Boulder.class) {
				trig = true;
				break;
			}
		}
		this.triggered = trig;
		if (goal != null) goal.updateSelf();
	}

	@Override
	public void collide(Boulder boulder) {
		this.triggered = true;
		if (goal != null) goal.updateSelf();
	}
	
}
