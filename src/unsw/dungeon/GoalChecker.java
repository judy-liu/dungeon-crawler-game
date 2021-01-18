package unsw.dungeon;

import java.util.ArrayList;

/**
 * Checks the completion of different types of complex goals.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public interface GoalChecker {

	/**
	 * Checks whether some or all of subgoals are complete depending type of checker.
	 * @param subgoals the list of GoalSubjects to check
	 * @return true if subgoals are complete, false if incomplete
	 */
	public boolean checkComplete(ArrayList<Goal> subgoals);
	

}
