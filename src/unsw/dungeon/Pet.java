package unsw.dungeon;

import java.util.ArrayList;

/**
 * A pet to follow player entity around.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public class Pet extends Follow {

	private Player player;
	private GoalPet goal;
	
	/**
	 * Constructor for Pet
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon the pet is in
	 */
	public Pet(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.player = null;
		this.goal = null;
	}
	
	/**
	 * Set the pet to follow a player.
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
		player.addPet(this);
	}
	
	/**
	 * Set the pet to belong to a pet goal.
	 * @param goal the pet goal
	 */
	public void setGoal(GoalPet goal) {
		this.goal = goal;
	}

	/**
	 * When added to basket, set player to null and update any pet goals.
	 */
	public void addToBasket() {
		this.player = null;
		if (goal != null) {
			goal.updateSelf();
		}
	}
	
	@Override
	public boolean isSolid() {
		// is solid if it has no player
		return this.player == null;
	}

	@Override
	public void collide(Player player) {
		// if this pet is already following the player, do nothing
		if (this.player != null) return;
		setPlayer(player);		
	}

	@Override
	public void collide(Enemy enemy) {
		enemy.collide(this);
	}
	
	@Override
	public boolean isCollidable(Enemy enemy) {
		// if the pet hasn't been collected by the player
		// it cannot collide with an enemy
		if (player == null) return false;
		return true;
	}

	@Override
	public boolean isCollidable(Boulder boulder) {
		return false;
	}

	@Override
	public boolean isCollidable(Pet pet) {
		return false;
	}

	/**
	 * Move the pet into the old position of the entity that is before it.
	 * @param prev the entity the pet is following
	 */
	public void updateLocation(Follow prev) {
		int newX = prev.getOldX();
		int newY = prev.getOldY();

		// there is another object that implements follow on the next block
		// so it cannot move there
		if (!checkMove(newX, newY)) return;
		
		// save the current position as old position
		setOldX(getX());
		setOldY(getY());
		
		// moving the pet to the previous block of the player
		getDungeon().updateMoveLocation(this, newX, newY);
		this.x().set(newX);
		this.y().set(newY);
		for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
			e.collide(this);
		}
	}
	
	/**
	 * Checks whether the pet can move the the new position.
	 * @param newX new x-position
	 * @param newY new y-position
	 * @return true if can move, false if cannot
	 */
	private boolean checkMove(int newX, int newY) {
		if (player.getX() == newX && player.getY() == newY)
			return false;
		
		// if there is an entity that cannot collide with pet
		// then move is invalid
		ArrayList<Entity> entities = getDungeon().getGridEntities(newX, newY);
		for (Entity e : entities) {
			if (!e.isCollidable(this))
				return false;
		}
		return true;
	}


}
