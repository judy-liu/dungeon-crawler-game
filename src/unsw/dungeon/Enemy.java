package unsw.dungeon;

/**
 * The enemy entity.
 * Is an observer of Player.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Enemy extends Entity implements PlayerObserver {

	private EnemyMovement moveTowards;
	private EnemyMovement moveAway;
	private EnemyMovement movement;
	private Player player;
	private GoalEnemy goal;
	
	public Enemy(Dungeon dungeon, int x, int y) { // added dungeon parameter bc it wont work
		super(x, y, dungeon);
		this.moveTowards = new EnemyMoveTowards();
		this.moveAway = new EnemyMoveAway();
		this.movement = moveTowards;
	}
	
	/**
	 * Set a goal for the enemy to belong to.
	 * @param goal
	 */
	public void setGoal(GoalEnemy goal) {
		this.goal = goal;
	}

	/**
	 * Get the player the enemy is tracking.
	 * @return the player in the dungeon
	 */
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		player.addObserver(this);
	}
	
	/**
	 * Update the movement of the enemy based on if the player is invincible or not.
	 */
	@Override
	public void update() {
		if (player.isInvincible()) {
			movement = moveAway;
		} else {
			movement = moveTowards;
		}
	}

	/**
	 * Enemy is moved based on the current movement direction (towards or away from player).
	 * Enemy collides with all entities on the block it has moved to.
	 */
	public void move() {
		if (inDungeon().get() == false) return;
		movement.move(this, getDungeon());
		for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
			if (e == null) continue;
			e.collide(this);
		}
	}
	
	/**
	 * Move enemy to the given destination portal.
	 * @param dest the destination portal
	 */
	public void teleport(Portal dest) {
		getDungeon().updateMoveLocation(this, dest.getX(), dest.getY());
    	this.x().set(dest.getX());
    	this.y().set(dest.getY());
    	for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
    		if (e == null) continue;
    		// dont collide with the portal again
    		if (e.getClass() == Portal.class) continue;
    		e.collide(this);
    	}
    	getDungeon().updateGridEntities(getX(), getY());
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	
	@Override
	public boolean isCollidable(Boulder boulder) {
		return false;
	}

	/**
	 * When player collides with enemy, player dies. If player is invincible, enemy will die when collided with.
	 */
	@Override
	public void collide(Player player) {
		if (player.isInvincible()) {
			if (goal != null) 
				goal.updateSelf(); // defeated++
			getDungeon().removeEntity(this);
		} else {
			getDungeon().playerDeath();			
		}
	}

	/**
	 * Enemy dies when collided with sword. Sword remaining number of uses is reduced.
	 */
	@Override
	public void collide(Sword sword) {
		getDungeon().removeEntity(this);
		if (goal != null) 
			goal.updateSelf();
		sword.decreaseHits();
	}

	@Override
	public void collide(Pet pet) {
		getDungeon().removeEntity(this);
		if (goal != null) 
			goal.updateSelf();
	}
	

}
