package unsw.dungeon;

/**
 * Update coordinates for entity moving down.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class DirectionDown implements Direction {

	@Override
	public void move(Entity e, Dungeon dungeon) {
		dungeon.updateMoveLocation(e, e.getX(), e.getY() + 1);
		e.y().set(e.getY() + 1);
	}

	@Override
	public int getAddX() {
		return 0;
	}

	@Override
	public int getAddY() {
		return 1;
	}
	
}
