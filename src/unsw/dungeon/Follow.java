package unsw.dungeon;

/**
 * An entity that can follow and be followed.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public abstract class Follow extends Entity {

	private int oldX;
	private int oldY;
	
	/**
	 * Create a new followable entity with old position set to -1
	 * @param x starting x-value
	 * @param y starting y-value
	 * @param dungeon
	 */
	public Follow(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.oldX = -1;
		this.oldY = -1;
	}

	/**
	 * Obtain the previous x-position of the object.
	 * @return integer value of x-position
	 */
	public int getOldX() {
		return oldX;
	}
	
	/**
	 * Obtain the previous y-position of the object.
	 * @return integer value of y-position
	 */
	public int getOldY() {
		return oldY;
	}

	/**
	 * Setter for oldX value
	 * @param oldX
	 */
	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	/**
	 * Setter for oldY value
	 * @param oldY
	 */
	public void setOldY(int oldY) {
		this.oldY = oldY;
	}
	
}
