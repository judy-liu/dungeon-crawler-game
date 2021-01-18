package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The player entity.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 */

public class Player extends Follow {

	private ArrayList<Entity> inventory;
    private Direction direction;
    private ArrayList<PlayerObserver> observers;
    
    private IntegerProperty inventorySize; // when this changes, it updates its listeners
    private IntegerProperty invincibilityProperty; 
    
    // all pets that are following the player
    private LinkedList<Pet> pets;
    
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, dungeon);
        this.inventory = new ArrayList<Entity>();
        this.observers = new ArrayList<PlayerObserver>();
        this.direction = null;
        //this.invincibility = 0;
        this.invincibilityProperty = new SimpleIntegerProperty(0);
        this.inventorySize = new SimpleIntegerProperty(0);
        this.pets = new LinkedList<Pet>();
    }

    /**
     * Get the Integer Property of inventory size. Is listened to in the controller.s
     * @return
     */
    public IntegerProperty getInventorySizeProperty() {
    	return this.inventorySize;
    }
    /**
     * Set invincibility to be on for 10 moves.
     */
    public void triggerInvincibility() {
    	this.invincibilityProperty.set(10); // added
    }
    
    /**
     * If invincibility is > 0, the player is invincible.
     * @return true if invincible, false if not
     */
    public boolean isInvincible() {
    	return this.invincibilityProperty.get() > 0;
    }
    
    /**
     * Get how many remaining steps the player is invincible for.
     * @return number of remaining invincible steps
     */
    public int getInvincibilityState() {
    	return this.invincibilityProperty.get();
    }
    
    /**
     * Get the IntegerProperty of how many invincible steps left.
     * @return invincibility property
     */
    public IntegerProperty getInvincibilityStateProperty() {
    	return this.invincibilityProperty;
    }

    /**
     * Get the direction the player last moved in.
     * @return last direction
     */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Set direction to up and then try to move.
	 */
	public void moveUp() {
        if (getY() > 0) {
            direction = new DirectionUp();
            move();
        }
    }
	
	/**
	 * Set direction to down and then try to move.
	 */
    public void moveDown() {
        if (getY() < getDungeon().getHeight() - 1) {
            direction = new DirectionDown();
            move();
        }
    }

	/**
	 * Set direction to left and then try to move.
	 */
    public void moveLeft() {
        if (getX() > 0) {
            direction = new DirectionLeft();
            move();
        }
    }

	/**
	 * Set direction to right and then try to move.
	 */
    public void moveRight() {
        if (getX() < getDungeon().getWidth() - 1) {
            direction = new DirectionRight();
        	move();
    	}
    }
    
    /**
     * Move the player to next block in set direction if the movement is valid.
     */
    private void move() {
    	if (!checkMovement()) return;
    	
    	setOldX(getX());
    	setOldY(getY());

    	// move the player
    	direction.move(this, getDungeon());
    	
    	// decrement/remove invincibility potion when invincible
    	if (this.invincibilityProperty.get() > 0) {
    		int dec = this.invincibilityProperty.get() - 1;
    		this.invincibilityProperty.set(dec);
    	}
    	else if (this.invincibilityProperty.get() == 0) {
    		this.removeItem("invincibilityPotion"); //remove potion from inventory
    	}    	

    	// collide entities on the new position with player
    	for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
    		if (e == null) continue;
    		e.collide(this);
    	}
    	
    	// update any entities on the block the player has moved to
    	getDungeon().updateGridEntities(getX(), getY());
    	
    	// update any entities on the previous position of player
    	getDungeon().updateGridEntities(getOldX(), getOldY());
    	
    	notifyObservers();
    	updatePetsLocation();
    }
    
    /**
     * Move the player to the given portal.
     * @param dest the destination portal
     */
    public void teleport(Portal dest) {
    	updatePetsLocation();
    	this.x().set(dest.getX());
    	this.y().set(dest.getY());
    	for (Entity e : getDungeon().getGridEntities(getX(), getY())) {
    		if (e == null) continue;
    		// dont collide with the portal again
    		if (e.getClass() == Portal.class) continue;
    		e.collide(this);
    	}
    	getDungeon().updateGridEntities(getX(), getY());
    	getDungeon().updateGridEntities(getNewX(), getNewY());
    }
    
    public void addPet(Pet pet) {
    	pets.addFirst(pet);
    }
    
    private void updatePetsLocation() {
		Iterator<Pet> iterator = pets.iterator();
		Follow prev = this;
		while (iterator.hasNext()) {
			Pet p = iterator.next();
			p.updateLocation(prev);
			prev = p;
		}
	}

	/**
     * Check whether moving the player in the given direction is valid and not blocked by other entities.
     * @return true if valid move, false if invalid
     */
    public boolean checkMovement() {
    	// get list of entities in the next square
    	ArrayList<Entity> nextBlock = getDungeon().getGridEntities(getNewX(), getNewY());
    	for (Entity e : nextBlock) {
    		if (e == null) continue;
    		if (!e.isCollidable(this)) return false;
    	}
    	return true;
    }	
    
	/**
	 * Get the inventory of the player.
	 * @return the player inventory
	 */
    public ArrayList<Entity> getInventory() {
    	return this.inventory;
    }
	
	/**
	 * Add an item to the player's inventory.
	 * @param item
	 */
	public void addItem(Entity item) {
		this.inventory.add(item);
		this.inventorySize.set(this.inventory.size()); // ++1
	}

	@Override
	public void collide(Enemy enemy) {
		enemy.collide(this);
	}
	
	/**
	 * Get a specific item in inventory. Searches inventory by item name.
	 * @param get name of the item to get
	 * @return the entity item that is being retrieved or null if it cannot be found
	 */
    public Entity getInventoryItem(String get) {
    	for (Entity i : inventory) {
    		if (i.getName().equals(get)) {
    			return i;
    		}
    	}
    	return null;
    }
    
    /**
     * Remove a specific item from inventory by item's name.
     * @param remove the name of the item to remove
     */
    public void removeItem(String remove) {    	
    	Iterator<Entity> curr = this.inventory.iterator();
    	while (curr.hasNext()) {
    		Entity item = (Entity) curr.next();
    		if (item.getName().equals(remove)) {
    			curr.remove();
    		}
    	}
    	this.inventorySize.set(this.inventory.size()); // --1
    }
    
    /**
     * Return name of the class: player
     * @return the string "player"
     */
    public String getName() {
    	return "player";
    }
    
    /**
     * Swing the player's sword to the left. Do nothing if there is no sword in inventory.
     */
    public void swingSwordLeft() {
    	Sword sword = (Sword) getInventoryItem("sword");
    	if (sword == null) return;
    	sword.swingLeft(this);
    }

    /**
     * Swing the player's sword to the right. Do nothing if there is no sword in inventory.
     */
    public void swingSwordRight() {
    	Sword sword = (Sword) getInventoryItem("sword");
    	if (sword == null) return;
    	
    	sword.swingRight(this);
    }
    
    /**
     * Swing the player's sword upwards. Do nothing if there is no sword in inventory.
     */
    public void swingSwordUp() {
    	Sword sword = (Sword) getInventoryItem("sword");
    	if (sword == null) return;
    	
    	sword.swingUp(this);
    }
    
    /**
     * Swing the player's sword downwards. Do nothing if there is no sword in inventory.
     */
    public void swingSwordDown() {
    	Sword sword = (Sword) getInventoryItem("sword");
    	if (sword == null) return;
    	
    	sword.swingDown(this);
    }
    
	/**
	 * Add an observer of the player to the player's list of observers.
	 * @param po the player observer to be added
	 */
	public void addObserver(PlayerObserver po) {
		this.observers.add(po);
	}
	
	/**
	 * Return list of player's observers.
	 * @return
	 */
	public ArrayList<PlayerObserver> getObservers(){
		return this.observers;
	}
	
	/**
	 * Update observers on current player state (invincible or not).
	 */
	public void notifyObservers() {
		for (PlayerObserver o : observers) {
			o.update();
		}
	}
	
	/**
	 * Get the new x-value of player after moving in certain direction
	 * @return new x-value as an int
	 */
	public int getNewX() {
		return getX() + direction.getAddX();
	}

	/**
	 * Get the new y-value of player after moving in certain direction
	 * @return new y-value as an int
	 */
	public int getNewY() {
		return getY() + direction.getAddY();
	}

	/**
	 * Place any pets into all adjacent free baskets.
	 */
	public void putBasket() {
		// cannot put into basket during night
		if (!getDungeon().isDay()) return;
		
		// get all adjacent baskets
		ArrayList<Basket> baskets = getDungeon().getAdjacentBaskets(getX(), getY());
		
		// if there are no baskets, do nothing
		if (baskets.isEmpty()) return;
		
		// if there are baskets try to put the last pet into them
		for (Basket b : baskets) {
			// if there are no more pets nothing can be added to basket
			if (pets.isEmpty()) break;
			
			// if the basket is empty put the first followed pet into it
			// then update location
			if (b.isEmpty()) {
				b.setPet(pets.removeLast());
			}
		}
	}
	
}

