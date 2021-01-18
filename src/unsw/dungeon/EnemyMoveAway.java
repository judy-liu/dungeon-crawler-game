package unsw.dungeon;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Enemy movement when player is invincible.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public class EnemyMoveAway implements EnemyMovement {

	@Override
	public Direction findRoute(Enemy enemy, Dungeon dungeon) {
		ArrayList<Direction> movable = findMovableDirections(enemy, dungeon);
		
		// shuffle the list so the enemy doesn't always travel in the same direction
		// if two choices have equal minimal distance
		Collections.shuffle(movable);
		
		int maxDist = -1;
		Direction direction = null;
		Player player = enemy.getPlayer();
		
		for (Direction d : movable) {
			int steps = stepsAway(enemy.getX() + d.getAddX(), player.getX(), 
									enemy.getY() + d.getAddY(), player.getY());

			// choose the route that puts the maximum distance between
			// player and enemy
			if (maxDist < steps) {
				maxDist = steps;
				direction = d;
			} 
		}
		
		return direction;
	}
}
