package unsw.dungeon;

import java.util.ArrayList;

/**
 * The boulder entity.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Boulder extends Entity {
	
	public Boulder(Dungeon dungeon, int x, int y) {
		super(x, y, dungeon);
	}
	
	/**
	 * Move the boulder one square in given direction.
	 * @param direction direction to move the boulder, the last player movement direction
	 */
	public void move(Direction direction) {
		direction.move(this, getDungeon());
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	
	@Override
	public boolean isCollidable(Pet pet) {
		return false;
	}

	/**
	 * Checks whether the boulder can move to the given x and y coordinates.
	 * @param player the player pushing the boulder
	 * @param x x-coordinate the boulder wants to move to
	 * @param y y-coordinate the boulder wants to move to
	 * @return true if boulder can move there, false otherwise
	 */
	public boolean checkMovement(Player player, int x, int y) {
		if (getDungeon().containsSolid(x, y))
			return false;
		return true;
	}
	
	/**
	 * Move boulder to the given destination portal.
	 * @param dest the destination portal
	 */
    public void teleport(Portal dest) {
    	// doesn't need to update grid entities as apart
    	// from colliding with portal, boulder colliding doesn't do anything    	
    	getDungeon().updateMoveLocation(this, dest.getX(), dest.getY());
    	this.x().set(dest.getX());
    	this.y().set(dest.getY());
    }
	
    /**
     * Move the boulder in the same direction as the player.
     * Update any entities in the grid of the new position of the boulder.
     */
	@Override
	public void collide(Player player) {
		move(player.getDirection());
		getDungeon().updateGridEntities(getX(), getY());		
	}
	
	@Override
	public boolean isCollidable(Player player) {
		Direction direction = player.getDirection();
		int nextX = getX() + direction.getAddX();
		int nextY = getY() + direction.getAddY();
		if (nextX < 0 || nextX >= getDungeon().getWidth()) return false;
		if (nextY < 0 || nextY >= getDungeon().getHeight()) return false;
		ArrayList<Entity> nextBlock = getDungeon().getGridEntities(nextX, nextY);
		for (Entity e : nextBlock) {
			if (!e.isCollidable(this)) return false;
		}
		return true;
	}

	@Override
	public boolean isCollidable(Boulder boulder) {
		return false;
	}

	@Override
	public boolean isCollidable(Enemy enemy) {
		return false;
	}

	/**
	 * Boulder updates by colliding with all entities on the grid of its new position.
	 */
	@Override
	public void update() {
		ArrayList<Entity> entities = getDungeon().getGridEntities(getX(), getY());
		for (Entity e : entities) {
			e.collide(this);
		}
	}

}
