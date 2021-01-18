package unsw.dungeon;

/**
 * Direction interface to move movable entities (Player, Enemy and Boulder).
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public interface Direction {

	/**
	 * Numerical value to add to entity's x-coordinate in order to move in given direction.
	 * @return 0 if direction is up or down, -1 for moving left, 1 for moving right
	 */
	public int getAddX();
	
	/**
	 * Numerical value to add to entity's y-coordinate in order to move in given direction.
	 * @return 0 if direction is left or right, -1 for moving up, 1 for moving down
	 */
	public int getAddY();
	
	/**
	 * Move the given entity in the given direction.
	 * @param e entity to be moved
	 * @param dungeon dungeon the entity is in
	 */
	public void move(Entity e, Dungeon dungeon);
	
}
