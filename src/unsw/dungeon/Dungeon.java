package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Judy Liu z5209176
 * @author Ailin Zhang z5207331
 *
 */
public class Dungeon {

    private int width, height;
    private ArrayList<Entity>[][] entities;
    private Player player;
    private Goal goal;
    private BooleanProperty complete;
    private BooleanProperty alive;
    private BooleanProperty day;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList[width][height];
        for (int i = 0; i < width; i++) {
        	for (int j = 0; j < height; j++) {
        		entities[i][j] = new ArrayList<Entity>();
        	}
        }
        this.player = null;
        this.goal = null;
        this.alive = new SimpleBooleanProperty(true);
        this.complete = new SimpleBooleanProperty(false);
        this.day = new SimpleBooleanProperty(true);
    }
    
    /**
     * Get how many squares wide the dungeon is.
     * @return dungeon width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get how many squares high the dungeon is.
     * @return dungeon height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get dungeon player.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set dungeon player.
     * @param player the dungeon player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Return whether the dungeon player is still alive.
     * @return true if player is alive, false if dead
     */
    public BooleanProperty getAlive() {
    	return alive;
    }
    
	/**
	 * Return the goal object of the dungeon.
	 * @return the dungeon goal which can be a single goal, AND goal or OR goal
	 */
	public Goal getGoal() {
		return this.goal;
	}
    
	/**
	 * Set the dungeon goal.
	 * @param goal simple or complex goal for the whole dungeon
	 */
    public void setGoal(Goal goal) {
    	this.goal = goal;
    }

    /**
     * Get the boolean property for whether dungeon goal is complete
     * @return boolean property for dungeon goal completeness
     */
    public BooleanProperty getComplete() {
		return complete;
	}

    /**
     * Get the boolean property of whether the dungeon is day or night
     * @return boolean property for dungeon day or night
     */
    public BooleanProperty getDay() {
    	return day;
    }
    
    /**
     * Returns whether the dungeon state is day or night
     * @return true dungeon is in day, false if at night
     */
    public boolean isDay() {
    	return day.get();
    }
    
    /**
     * Set value for the day to opposite of the current value
     */
    public void switchDay() {
    	boolean current = day.get();
    	day.set(!current);
    }
    
	/**
     * Add the entity to the 2d array of entities.
     * @param entity the entity to be added
     */
    public void addEntity(Entity entity) {
    	if (entity == null) return;
    	entities[entity.getX()][entity.getY()].add(entity);
    	entity.inDungeon().set(true);
    }
    
    /**
     * Remove an entity from a tile's list of entities
     * @param entity entity to remove
     */
    public void removeEntity(Entity entity) {
    	if (entity == null) return;
    	entities[entity.getX()][entity.getY()].remove(entity);
    	entity.inDungeon().set(false);
    }
    
    /**
     * Get all entities on the square of given coordinates.
     * @param x x-coordinate of square
     * @param y y-coordinate of square
     * @return a copy of the list of entities in the given coordinates
     * if the coordinates are not valid return an empty list
     */
    public ArrayList<Entity> getGridEntities(int x, int y) {
    	if (x < 0 || x >= getWidth()) return new ArrayList<Entity>();
    	if (y < 0 || y >= getHeight()) return new ArrayList<Entity>();
    	return new ArrayList<Entity>(entities[x][y]);     
    }

    /**
     * For picking up an item. Can directly edit the list of items that is returned by this function.
     * @param x x-coordinate of square
     * @param y y-coordinate of square
     * @return the list of entities kept in the dungeons 2d array of entities
     */
    public ArrayList<Entity> getFlexibleGridEntities(int x, int y) {
    	// if they exist, return a good array
    	if (x >= 0 && y >= 0 && x < this.getWidth() && y < this.height) {
    		return entities[x][y]; 
    	}
    	return null;    
    }
    
    /**
     * Move the entity from original list in the 2d entity array 
     * to the list kept in its new coordinates.
     * @param entity entity to move
     * @param newX the new x-coordinate of the entity
     * @param newY the new y-coordinate of the entity
     */
	public void updateMoveLocation(Entity entity, int newX, int newY) {
		entities[entity.getX()][entity.getY()].remove(entity);
		entities[newX][newY].add(0, entity);
	}
	
	/**
	 * Update the states of all entities on a given grid square. Used for when entities collide with
	 * or move off of given square.
	 * @param x x-coordinate of square to update
	 * @param y y-coordinate of square to update
	 */
    public void updateGridEntities(int x, int y) {
		ArrayList<Entity> entities = getGridEntities(x, y);
		for (Entity e : entities) {
			if (e == null) continue;
			e.update();
		}
	}

    /**
     * Checks whether the square of given coordinates have a solid entity on it.
     * @param x x-coordinate of square
     * @param y y-coordinate of square
     * @return true if there is a solid, false if there is no solid
     */
	public boolean containsSolid(int x, int y) {
		ArrayList<Entity> gridEntities = getGridEntities(x,y);
		for (Entity e : gridEntities) {
			if (e == null) continue;
			if (e.isSolid()) return true;
		}
    	return false;
    }

	/**
	 * Handles player death. Sets alive status to false.
	 */
	public void playerDeath() {
		this.alive.set(false);
	}

	/**
	 * Set the completeness of the goal to the value of goals completion
	 */
	public void updateGoal() {
		this.complete.set(checkComplete());
	}
	
	/**
	 * Check whether the dungeons overall goal is completed.
	 * @return true if goal completed, false if incomplete
	 */
	public boolean checkComplete() {
		return goal.checkComplete(); // used for testing
	}


	public ArrayList<Basket> getAdjacentBaskets(int x, int y) {
		ArrayList<Basket> baskets = new ArrayList<Basket>();
		ArrayList<Entity> adjacent = getGridEntities(x - 1, y);
		adjacent.addAll(getGridEntities(x + 1, y));
		adjacent.addAll(getGridEntities(x, y - 1));
		adjacent.addAll(getGridEntities(x, y + 1));
		adjacent.removeIf(e -> e.getClass() != Basket.class);
		for (Entity e : adjacent) {
			baskets.add((Basket) e);
		}
		return baskets;
	}

}
