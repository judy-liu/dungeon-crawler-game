package unsw.dungeon;

/**
 * The treasure entity.
 * Can be collected by player and be associated with a treasure goal.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Treasure extends Entity {

	private int amount;
	private GoalTreasure goal;
	
	/**
	 * Constructor for Treasure
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon the treasure is in
	 */
	public Treasure(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.goal = null;
	}

	/**
	 * Set a goal for the treasure to belong to.
	 * @param goal
	 */
	public void setGoal(GoalTreasure goal) {
		this.goal = goal;
	}
	
	@Override
	public String toString() {
		int amount = this.amount;
		String result = "(item) treasure: " + amount;
		return result;
	}

	@Override
	public String getName() {
		return "treasure";
	} 
	
	/**
	 * When treasure collides with player, it is picked up and added to the inventory. Also if there is a treasure goal
	 * update the goal.
	 */
	@Override
	public void collide(Player player) {
		player.addItem(this); // add this item to player's inventory
		getDungeon().removeEntity(this);
		
		// if there is a treasure goal, call the update method
		if (this.goal != null) {
			goal.updateSelf();
		}
	}
	
}
