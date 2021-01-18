package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The door entity.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Door extends Entity {

	private int id;
	private BooleanProperty locked;
	
	public Door(int x, int y, int id, Dungeon dungeon) {
		super(x, y, dungeon);
		this.id = id;
		this.locked = new SimpleBooleanProperty(true);
	}
	
	/**
	 * Get the id of the door.
	 * @return int value of id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the boolean property for locked
	 * @return the locked attribute
	 */
	public BooleanProperty locked() {
		return locked;
	}
	
	/**
	 * Door is solid (blocks movement) if locked. Not solid if locked is not true.
	 */
	@Override
	public boolean isSolid() {
		return locked.get();
	}

	@Override
	public boolean isCollidable(Player player) {
		if (!locked.get()) return true;
		Entity item = player.getInventoryItem("key");
		if (item == null) {
			return false;
		}
		// collidable if player holds the correct key for this door
		Key key = (Key) item;
		if (key.getKeyID() == getId()) return true;
		return false;
	}

	@Override
	public boolean isCollidable(Boulder boulder) {
		return !locked.get();
	}

	@Override
	public boolean isCollidable(Enemy enemy) {
		return !locked.get();
	}


	/**
	 * if the player can collide with the door it means the door is unlocked or unlockable with the key
	 */
	@Override
	public void collide(Player player) {
		if (locked.get()) {
			player.removeItem("key");
			locked.set(false);
		}		
	}

}
