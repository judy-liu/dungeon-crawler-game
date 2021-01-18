package unsw.dungeon;

import java.util.ArrayList;

/**
 * Class for complex goals. It is a GoalSubject as it is a goal itself and can be observed.
 * It is also a GoalObserver as it observes subgoals.
 * Implements a strategy pattern for goal checking.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class GoalComplex implements Goal {
	
	private ArrayList<Goal> subgoals;
	private GoalChecker checker;
		
	public GoalComplex(GoalChecker checker) {
		this.subgoals = new ArrayList<Goal>();
		this.checker = checker;
	}
	
	/**
	 * Add a subgoal to this goal.
	 * @param subgoal subgoal to add
	 */
	public void addSubgoal(Goal subgoal) {
		subgoals.add(subgoal);
	}
	
	/**
	 * Using the checker for AND or OR goals, check whether the complex
	 * goal has been completed
	 */
	@Override
	public boolean checkComplete() {
		return checker.checkComplete(subgoals);
	}
	
	/**
	 * GoalSubject interface method used for leaves,
	 * which has same functionality as updateGoal() for complex goals
	 */
	@Override
	public void updateSelf() {
		return;
	}

	@Override
	public String toString() {
		String str = "[ " + subgoals.get(0).toString();
		for (int i = 1; i < subgoals.size(); i++) {
			Goal subgoal = subgoals.get(i);
			str += " " + checker.toString() + " " + subgoal.toString();
		}
		str += " ]";
		return str;
	}
	

}
