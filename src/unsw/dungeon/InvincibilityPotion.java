package unsw.dungeon;

/**
 * The invincibility potion entity.
 * Updates player to invincible when picked up.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public class InvincibilityPotion extends Entity {
	/**
	 * New invincibility potion in the dungeon
	 * @param x x-position of invincibility potion
	 * @param y y-position of invincibility potion
	 * @param dungeon dungeon the potion is in
	 */
	public InvincibilityPotion(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
	}

	@Override
	public String toString() {
		return "(item) invincibilityPotion";
	}
	

	@Override
	public String getName() {
		return "invincibilityPotion";
	}

	/**
	 * If player has an invincibility potion already, they cannot pickup another one.
	 * If the player has no potion, player invincibility is triggered and potion disappears from dungeon.
	 */
	@Override
	public void collide(Player player) {
		if (player.isInvincible()) {
			return;
		}
		player.triggerInvincibility();
		player.addItem(this); // add this item to player's inventory
		getDungeon().removeEntity(this);
		
	}

}
