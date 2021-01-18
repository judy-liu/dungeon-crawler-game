package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The sword entity.
 * Can be swung in four different directions.
 * Usage goes down when it collides with an enemy entity.
 * Maximum number of uses is 5, and is removed from player inventory when used up.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Sword extends Entity {

	//private int hits; // every time player uses sword, hits -= 1
	private IntegerProperty hitsLeft;
	
	/**
	 * Constructor for Sword
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon the sword is in
	 */
	public Sword(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.hitsLeft = new SimpleIntegerProperty(5);
	}

	@Override
	public String getName() {
		return "sword";
	}

	@Override
	public String toString() {
		return "(item) sword";
	}
	
	/**
	 * Get the number of hits remaining for the sword.
	 * @return number of hits left
	 */
	public IntegerProperty getHits() {
		return this.hitsLeft;
	}
	
	/**
	 * Decreases the value of hits by 1 every time function is called (when player swings sword)s
	 */
	public void decreaseHits() {
		this.hitsLeft.set(this.hitsLeft.getValue() - 1);
		
		// if hits decreases to zero, remove from player inventory
		if (this.hitsLeft.getValue() == 0) {
			Player player = getDungeon().getPlayer();
			player.removeItem(getName());
		}
	}

	@Override
	public void collide(Player player) {
		Sword sword = (Sword) player.getInventoryItem("sword");
		if (sword != null) { 
			return; 
		}
		player.addItem(this); // add this item to player's inventory
		getDungeon().removeEntity(this);
	}
	
	/**
	 * Swing the sword left. Collide with entities in left block.
	 * @param player player that swings the sword
	 */
	public void swingLeft(Player player) {	
		// can't swing further left because no dungeon
		if (player.getX() == 0) return;
		
		// get entities to the left of player
		ArrayList<Entity> entities = getDungeon().getGridEntities(player.getX() - 1, player.getY());
		collideSelf(entities);
	}
	
	/**
	 * Swing the sword right. Collide with entities in right block.
	 * @param player player that swings the sword
	 */
	public void swingRight(Player player) {
		// can't swing further right because no dungeon
		if (player.getX() == getDungeon().getWidth() - 1) return;
		
		// get entities to the right of player
		ArrayList<Entity> entities = getDungeon().getGridEntities(player.getX() + 1, player.getY());
		collideSelf(entities);
	}
	
	/**
	 * Swing the sword up. Collide with entities in above block.
	 * @param player player that swings the sword
	 */
	public void swingUp(Player player) {
		// can't swing further up because no dungeon
		if (player.getY() == 0) return;
		
		// get entities above player
		ArrayList<Entity> entities = getDungeon().getGridEntities(player.getX(), player.getY() - 1);
		collideSelf(entities);
	}
	
	/**
	 * Swing the sword down. Collide with entities in below block.
	 * @param player player that swings the sword
	 */
	public void swingDown(Player player) {
		// can't swing further left because no dungeon
		if (player.getY() == getDungeon().getHeight() - 1) return;
		
		// get entities below player
		ArrayList<Entity> entities = getDungeon().getGridEntities(player.getX(), player.getY() + 1);
		collideSelf(entities);
	}
	
	/**
	 * Make all the enemies in the direction of the swing collide with sword.
	 * @param entities
	 */
	private void collideSelf(ArrayList<Entity> entities) {
		for (Entity e : entities) {
			if (e == null) continue;
			if (getHits().getValue() == 0) return;
			e.collide(this);
		}
	}

}
