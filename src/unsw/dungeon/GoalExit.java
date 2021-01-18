package unsw.dungeon;

/**
 * An exit goal. Is a leaf goal as it has no subgoals.
 * Observed by a GoalObserver which can be either Dungeon or a GoalComplex.
 * Observes the dungeon exit to check completion.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalExit implements Goal {

	private Dungeon dungeon;
	private Exit exit;
	private boolean complete;
	
	/**
	 * Create goal of dungeon
	 * @param dungeon the dungeon
	 * @param exit the exit observed by this goal
	 * @param observer the composite component this goal is a part of
	 */
	public GoalExit(Dungeon dungeon, Exit exit) {
		this.dungeon = dungeon;
		this.exit = exit;
		exit.setGoal(this);
		this.complete = false;
	}
	
	@Override
	public boolean checkComplete() {
		return complete;
	}
	
	@Override
	public void updateSelf() {
		Player player = dungeon.getPlayer();
		if (player.getX() == exit.getX() && player.getY() == exit.getY()) {
			System.out.println("##### true now");
			complete = true;
		} else {
			System.out.println("##### false now");
			complete = false;
		}
		dungeon.updateGoal();
	}


	@Override
	public String toString() {
		return "Reach the exit";
	}
	
	
}
