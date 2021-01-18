package unsw.dungeon.frontend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import unsw.dungeon.*;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private ArrayList<Portal> portals;
    private ArrayList<Enemy> enemies;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        portals = new ArrayList<Portal>();
        enemies = new ArrayList<Enemy>();
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        
        for (Enemy enemy : enemies) {
        	enemy.setPlayer(dungeon.getPlayer());
        }
        
        // add destination portal
		for (Portal p : this.portals) {
			for (Portal q: this.portals) {
				if (p == q) continue;
				if (p.getId() == q.getId()) {
					p.setDest(q);
				}
			}
		}
        
        JSONObject jsonGoal = json.getJSONObject("goal-condition");
        Goal goal = buildGoal(jsonGoal, dungeon);
        dungeon.setGoal(goal);
        
        return dungeon;
    }

    private void makeSubgoals(GoalComplex complex, JSONObject jsonGoal, Dungeon dungeon) {
    	JSONArray jsonSubgoals = jsonGoal.getJSONArray("subgoals");
    	for (int i = 0; i < jsonSubgoals.length(); i++) {
    		JSONObject sub = jsonSubgoals.getJSONObject(i);
    		Goal subgoal = buildGoal(sub, dungeon);
    		complex.addSubgoal(subgoal);
    	}
    }
    
    private Goal buildGoal(JSONObject jsonGoal, Dungeon dungeon) {
    	Goal goal = null;
    	switch (jsonGoal.getString("goal")) {
        case "AND":
        	goal = new GoalComplex(new GoalCheckerAnd());
        	makeSubgoals((GoalComplex) goal, jsonGoal, dungeon);
        	break;
        case "OR":
        	goal = new GoalComplex(new GoalCheckerOr());
        	makeSubgoals((GoalComplex) goal, jsonGoal, dungeon);
        	break;
        case "exit":
        	Exit exit = null;
        	for (int i = 0; i < dungeon.getWidth(); i++) {
        		for (int j = 0; j < dungeon.getHeight(); j++) {
        			ArrayList<Entity> s = dungeon.getGridEntities(i, j);
    				s.removeIf(e -> e.getClass() != Exit.class);
    				if (s.isEmpty()) continue;
    				exit = (Exit) s.get(0);
    				break;
        		}
        	}
        	goal = new GoalExit(dungeon, exit);
        	break;
        case "boulders":
        	goal = new GoalSwitch(dungeon);
        	break;
        case "treasure":
        	goal = new GoalTreasure(dungeon);
        	break;
        case "enemies":
        	goal = new GoalEnemy(dungeon);
        	break;
        case "pets":
        	goal = new GoalPet(dungeon);
        	break;
        default:
        	break;
        }
    	return goal;
	}

	private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id = 0;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            onLoad(wall);
            entity = wall;
            break;
        // TODO Handle other possible entities
        case "treasure": // added jl
        	Treasure treasure = new Treasure(x, y, dungeon);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon, x, y);
        	onLoad(enemy);
        	entity = enemy;
        	enemies.add(enemy);
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(dungeon, x, y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(x, y, id, dungeon);
        	onLoad(key);
        	entity = key;
        	break;
        case "sword":
        	Sword sword = new Sword(x, y, dungeon);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "invincibility":
        	InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y, dungeon);
        	onLoad(invincibilityPotion);
        	entity = invincibilityPotion;
        	break;
        case "switch":
        	Switch swit = new Switch(dungeon, x, y);
        	onLoad(swit);
        	entity = swit;
        	break;
        case "exit":
        	Exit exit = new Exit(x, y, dungeon);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(x, y, id, dungeon);
        	onLoad(door);
        	entity = door;
        	break;
        case "portal":
        	id = json.getInt("id");
        	Portal portal = new Portal(dungeon, x, y, id);
        	onLoad(portal);
        	entity = portal;
        	this.portals.add(portal); // add portal to list
        	break;
        case "pet":
        	Pet pet = new Pet(x, y, dungeon);
        	onLoad(pet);
        	entity = pet;
        	break;
        case "basket":
        	Basket basket = new Basket(x, y, dungeon);
        	onLoad(basket);
        	entity = basket;
        	break;
        default:
        	break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

	public abstract void onLoad(Boulder boulder);

	public abstract void onLoad(Switch swit);

    // TODO Create additional abstract methods for the other entities
    
    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Sword sword);

	public abstract void onLoad(InvincibilityPotion invincibilityPotion);

	public abstract void onLoad(Door door);

	public abstract void onLoad(Enemy enemy);

	public abstract void onLoad(Portal portal);
	
	public abstract void onLoad(Pet pet);
	
	public abstract void onLoad(Basket basket);

	public abstract void onLoad(Exit exit);
	


}
