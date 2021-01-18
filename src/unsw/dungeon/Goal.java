package unsw.dungeon;

/**
 * A leaf goal which is being observed by either a Dungeon or a GoalComplex.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public interface Goal {

	/**
	 * Check whether given goal has been completed.
	 * @return true if goal is complete, false otherwise
	 */
	public boolean checkComplete();
	
	/**
	 * GoalSubject updates its own completion and then checks whether goal is complete.
	 * If complete, it will notify its observers of it completion.
	 */
	public void updateSelf();
	
	
}
