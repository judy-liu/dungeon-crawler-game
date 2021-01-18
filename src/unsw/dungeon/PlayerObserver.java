package unsw.dungeon;

/**
 * Interface for PlayerObservers.
 * PlayerObservers can be an Enemy or Dungeon whose state is updated when player moves.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public interface PlayerObserver {
	/**
	 * Update the player observer and execute any relevant methods.
	 */
	public void update();
}
