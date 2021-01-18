package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Basket for putting pets in.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 *
 */

public class Basket extends Entity {

	private Pet pet;
	private BooleanProperty hasPet;
	
	/**
	 * Create a new Basket at position (x,y) in the dungeon
	 * @param x x-position
	 * @param y y-position
	 * @param dungeon dungeon it is in
	 */
	public Basket(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.pet = null;
		this.hasPet = new SimpleBooleanProperty(false);
	}

	/**
	 * Set the pet for this basket.
	 * @param pet pet to add to basket
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
		this.hasPet.set(true);
		getDungeon().removeEntity(pet);
		this.pet.addToBasket();
	}


	@Override
	public boolean isCollidable(Player player) {
		return false;
	}


	@Override
	public boolean isCollidable(Boulder boulder) {
		return false;
	}


	@Override
	public boolean isCollidable(Enemy enemy) {
		return false;
	}


	@Override
	public boolean isCollidable(Pet pet) {
		return false;
	}

	/**
	 * Get the BooleanProperty of hasPet
	 * @return the hasPet attribute
	 */
	public BooleanProperty hasPet() {
		return this.hasPet;
	}

	/**
	 * Check whether basket is empty.
	 * @return true if empty, false if basket has pet
	 */
	public boolean isEmpty() {
		return !this.hasPet.get();
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
}
