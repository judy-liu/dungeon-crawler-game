package unsw.dungeon;

import java.util.ArrayList;

/**
 * Checks AND complex goals.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalCheckerAnd implements GoalChecker {

	@Override
	public boolean checkComplete(ArrayList<Goal> subgoals) {
		for (Goal goal : subgoals) {
			if (!goal.checkComplete()) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AND";
	}

}
