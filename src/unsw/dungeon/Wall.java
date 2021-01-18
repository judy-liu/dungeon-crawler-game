package unsw.dungeon;

/**
 * The wall entity.
 * Blocks all entity movement.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Wall extends Entity {

	/**
	 * Constructor for Wall
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon the door is in
	 */
    public Wall(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    /**
     * Walls are solid and block all movement.
     */
    @Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean isCollidable(Player player) {
		return false;
	}

	@Override
	public boolean isCollidable(Boulder boulder) {
		return false;
	}

	@Override
	public boolean isCollidable(Enemy enemy) {
		return false;
	}

	@Override
	public boolean isCollidable(Pet pet) {
		return false;
	}

}
