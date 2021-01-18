package unsw.dungeon;

/**
 * Update coordinates for entity moving left.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class DirectionLeft implements Direction {

	@Override
	public void move(Entity e, Dungeon dungeon) {
		dungeon.updateMoveLocation(e, e.getX() - 1, e.getY());
		e.x().set(e.getX() - 1);
	}

	@Override
	public int getAddX() {
		return -1;
	}

	@Override
	public int getAddY() {
		return 0;
	}
	
}
