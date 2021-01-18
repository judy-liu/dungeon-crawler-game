package unsw.dungeon;

import java.util.ArrayList;

/**
 * Checks OR complex goals.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalCheckerOr implements GoalChecker {

	@Override
	public boolean checkComplete(ArrayList<Goal> subgoals) {
		for (Goal goal : subgoals) {
			if (goal.checkComplete()) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "OR";
	}

}
