package unsw.dungeon;

/**
 * The key entity.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */


public class Key extends Entity {

	private int keyID;
	
	/**
	 * Constructor for Key
	 * @param x x-position
	 * @param y y-position
	 * @param id key ID (must match with door ID)
	 * @param dungeon dungeon the key is in
	 */
	public Key(int x, int y, int id, Dungeon dungeon) {
		super(x, y, dungeon);
		this.keyID = id; // each key has an individual id that matches with a door
	}

	/**
	 * Get keyID attribute.
	 * @return the keyID
	 */
	public int getKeyID() {
		return keyID;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass() ) {
        	return false;
        }
        Key other = (Key) obj; 
        if (this.keyID == other.keyID) { 
            return true; 
        } 
        
        return false;
	}

	
	@Override
	public String toString() {
		return "(item) key: " + this.keyID;
	}

	@Override
	public String getName() {
		return "key";
	}

	/**
	 * If player has a key in their inventory, they pick up the new key and the old
	 * key in inventory returns to its original position
	 */
	@Override
	public void collide(Player player) {
		Key k = (Key) player.getInventoryItem("key");
		// remove old key from inventory and add it back to the dungeon
		if (k != null) {
			getDungeon().addEntity(k);
			player.removeItem("key");
		}

		player.addItem(this); // add this item to player's inventory
//		this.inDungeon().set(false);
		getDungeon().removeEntity(this);
		
		
		
	}

}
