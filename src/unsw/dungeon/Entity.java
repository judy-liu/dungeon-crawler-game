package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private Dungeon dungeon;
    private BooleanProperty inDungeon;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     * @param dungeon 
     */
    public Entity(int x, int y, Dungeon dungeon) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.dungeon = dungeon;
        this.inDungeon = new SimpleBooleanProperty(true);
    }

    /**
     * Get IntegerProperty x - entity horizontal coordinate in dungeon
     * @return x IntegerProperty of entity
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * Get IntegerProperty y - entity vertical coordinate in dungeon
     * @return y IntegerProperty of entity
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * Get the inDungeon boolean property to see if entity is still in dungeon
     * @return inDungeon property
     */
    public BooleanProperty inDungeon() {
    	return inDungeon;
    }
    
    /**
     * Get the y-coordinate as an integer
     * @return int y-coordinate
     */
    public int getY() {
        return y().get();
    }

    /**
     * Get the x-coordinate as an integer
     * @return int x-coordinate
     */
    public int getX() {
        return x().get();
    }
    
    /**
     * Get the dungeon the entity exists in
     * @return
     */
    public Dungeon getDungeon() {
    	return this.dungeon;
    }
    
    // following three methods check if any entity can be on the same square
    // or moved by the parameter entities
    // default true but is overridden in some classes
    
    /**
     * Check if current entity can be collided (exist in the same block or
     * be moved away) by player.
     * @param player player to be moved into block
     * @return true if collidable, false otherwise
     */
    public boolean isCollidable(Player player) {
    	return true;
    }
    
    /**
     * Check if current entity can be collided (exist in the same block or
     * be moved away) by boulder.
     * @param boulder boulder to be moved into block
     * @return true if collidable, false otherwise
     */
    public boolean isCollidable(Boulder boulder) {
    	return true;
    }
    
    /**
     * Check if current entity can be collided (exist in the same block or
     * be moved away) by enemy.
     * @param enemy enemy to be moved into block
     * @return true if collidable, false otherwise
     */
    public boolean isCollidable(Enemy enemy) {
    	return true;
    }

    /**
     * Check if current entity can be collided (exist in the same block or
     * be moved away) by enemy.
     * @param enemy enemy to be moved into block
     * @return true if collidable, false otherwise
     */
    public boolean isCollidable(Pet pet) {
    	return true;
    }
    
    // various empty collide methods that can be overridden in subclasses when necessary
    
    /**
     * Executes action the entity should take when collided by pet.
     * Default do nothing, but is overridden when necessary.
     * @param player player that collides with this entity
     */
    public void collide(Pet pet) {
    	return;
    }
    
    /**
     * Executes action the entity should take when collided by player.
     * Default do nothing, but is overridden when necessary.
     * @param player player that collides with this entity
     */
    public void collide(Player player) {
    	return;
    }
    
    /**
     * Executes action the entity should take when collided by boulder.
     * Default do nothing, but is overridden when necessary.
     * @param boulder boulder that collides with this entity
     */
    public void collide(Boulder boulder) {
    	return;
    }
    
    /**
     * Executes action the entity should take when collided by enemy.
     * Default do nothing, but is overridden when necessary.
     * @param enemy enemy that collides with this entity
     */
    public void collide(Enemy enemy) {
    	return;
    }
    
    /**
     * Executes action the entity should take when collided by sword.
     * Default do nothing, but is overridden when necessary.
     * @param sword sword that collides with this entity
     */
    public void collide(Sword sword) {
    	return;
    }
    
    /**
     * Checks whether the entity is solid (blocks enemy and boulder movement).
     * @return default false (non-solid) but is overridden in subclasses when necessary
     */
    public boolean isSolid() {
    	return false;
    }
    
    /**
     * Updates entities state when something is moved onto or off of the same grid block.
     * Default do nothing but is overridden when necessary.
     */
    public void update() {
    	return;
    }

    /**
     * Gets the class name of this entity.
     * @return default is entity, overridden in subclasses when necessary
     */
	public String getName() {
		return "entity";
	}
}
