package unsw.dungeon;

import java.util.ArrayList;

/**
 * Interface for enemy movement. Many methods are common to the two states and are therefore default.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public interface EnemyMovement {

	/**
	 * Move the given enemy in the direction given by the best route.
	 * @param enemy the enemy entity to move
	 * @param dungeon the dungeon
	 */
	public default void move(Enemy enemy, Dungeon dungeon) {
		Direction moveDir = findRoute(enemy, dungeon);
		if (moveDir == null) return;
		moveDir.move(enemy, enemy.getPlayer().getDungeon());
	}
	
	/**
	 * Return the direction that moves the enemy closest towards or farthest away
	 * from the player. Must be overridden by implementing classes.
	 * @param enemy the enemy entity to move
	 * @param dungeon the dungeon
	 * @return Direction of best movement
	 */
	public Direction findRoute(Enemy enemy, Dungeon dungeon);
	
	/**
	 * Gets all directions the enemy can move in (i.e. the enemy is not
	 * blocked by a solid entity).
	 * @param enemy the enemy entity to move
	 * @param dungeon the dungeon
	 * @return list of movable directions
	 */
	public default ArrayList<Direction> findMovableDirections(Enemy enemy, Dungeon dungeon) {
		ArrayList<Direction> movable = new ArrayList<Direction>();
		
		if (enemy.getX() > 0 && !dungeon.containsSolid(enemy.getX() - 1, enemy.getY()))
			movable.add(new DirectionLeft());
		
		if (enemy.getX() + 1 < dungeon.getWidth() && 
			!dungeon.containsSolid(enemy.getX() + 1, enemy.getY()))
			movable.add(new DirectionRight());
		
		if (enemy.getY() > 0 && !dungeon.containsSolid(enemy.getX(), enemy.getY() - 1))
			movable.add(new DirectionUp());
		
		if (enemy.getY() + 1 < dungeon.getHeight() && 
			!dungeon.containsSolid(enemy.getX(), enemy.getY() + 1))
			movable.add(new DirectionDown());
		
		return movable;
	}
	
	/**
	 * Calculate the total steps away between two coordinates.
	 * @param x1 x value of first coordinate
	 * @param x2 x value of second coordinate
	 * @param y1 y value of first coordinate
	 * @param y2 y value of second coordinate
	 * @return total number of blocks between two coordinates
	 */
	public default int stepsAway(int x1, int x2, int y1, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
}
