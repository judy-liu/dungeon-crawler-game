package unsw.dungeon;

import java.util.ArrayList;

/**
 * The portal entity.
 * Can teleport player, enemies and boulders provided the destination portal is not blocked.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Portal extends Entity {
	
	private int id;
	private Portal dest;

	/**
	 * Constructor for Portal
	 * @param x x-position
	 * @param y y-position
	 * @param id portal ID (must match with destination portal)
	 * @param dungeon dungeon the portal is in
	 */
	public Portal(Dungeon dungeon, int x, int y, int id) {
		super(x, y, dungeon);
		this.id = id;
		this.dest = null;
	}
	
	/**
	 * Get the id of this portal.
	 * @return id of portal
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the destination portal object.
	 * @return the destination portal
	 */
	public Portal getDest() {
		return dest;
	}

	/**
	 * Set the destination portal
	 * @param dest
	 */
	public void setDest(Portal dest) {
		if (dest.getId() != getId()) return;
		if (dest.getX() == getX() && dest.getY() == getY()) return;
		this.dest = dest;
	}
	
	@Override
	public void collide(Player player) {
		// if the destination portal has solids on it
		// check if it can be collided (moved away) by player
		// if it cannot be collided, return false
		if (!checkDestFree()) {
			ArrayList<Entity> destEntities = getDungeon().getGridEntities(dest.getX(), dest.getY());
			for (Entity e : destEntities) {
				if (!e.isCollidable(player))
					return;
			}
		}
		// if it reaches here it means the player can teleport
		getDungeon().removeEntity(player);
		player.teleport(getDest());
		getDungeon().addEntity(player);
	}

	@Override
	public void collide(Boulder boulder) {
		if (checkDestFree())
			boulder.teleport(dest);
	}

	@Override
	public void collide(Enemy enemy) {
		if (checkDestFree())
			enemy.teleport(dest);
	}
	
	/**
	 * Check that the destination portal is free so that entities can be teleported.
	 * @return true if destination can be teleported to, false otherwise
	 */
	private boolean checkDestFree() {
		return !getDungeon().containsSolid(dest.getX(), dest.getY());
	}
	
	
}
