package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class GoalComplexTestOr {

	private Dungeon dungeon;
	private Player player;
	private GoalComplex dungeonGoal;

	@BeforeEach
	void setUp() {
		dungeon = new Dungeon(9, 6);
		player = new Player(dungeon, 2, 2);
		dungeonGoal = new GoalComplex(new GoalCheckerOr());
		
		dungeon.setPlayer(player);
		dungeon.setGoal(dungeonGoal);
		
		dungeon.addEntity(player);
	}

	@Test
	void goalIncomplete() {
		Exit exit = new Exit(8, 5, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		dungeonGoal.addSubgoal(exitGoal);
		
		Treasure treasure1 = new Treasure(1, 1, dungeon);
		Treasure treasure2 = new Treasure(4, 2, dungeon);
		Treasure treasure3 = new Treasure(6, 3, dungeon);
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(treasure3);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		dungeonGoal.addSubgoal(treasureGoal);
		
		// move player to treasure2
		player.moveRight();
		player.moveRight();
		
		// move player to treasure3
		player.moveRight();
		player.moveRight();
		player.moveDown();
		
		// treasure goal not complete because treasure1 not collected
		assertFalse(treasureGoal.checkComplete());
		
		// exit goal incomplete because the player isn't on the exit
		assertFalse(exitGoal.checkComplete());
		
		// overall dungeon goal not complete
		assertFalse(dungeonGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
	}
	
	// many possible combinations, below are just some examples
	// if the below work then most combinations of goals work
	// as individual goals have been tested
	
	@Test
	void goalCompleteTreasureSwitch() {
		Treasure treasure1 = new Treasure(1, 1, dungeon);
		Treasure treasure2 = new Treasure(4, 2, dungeon);
		Treasure treasure3 = new Treasure(6, 3, dungeon);
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(treasure3);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		dungeonGoal.addSubgoal(treasureGoal);
		
		Switch switch1 = new Switch(dungeon, 5, 1);
		Switch switch2 = new Switch(dungeon, 1, 4);
		dungeon.addEntity(switch1);
		dungeon.addEntity(switch2);
		GoalSwitch switchGoal = new GoalSwitch(dungeon);
		dungeonGoal.addSubgoal(switchGoal);
		
		Boulder boulder1 = new Boulder(dungeon, 4, 2);	// boulder1 lies on top of treasure2
		Boulder boulder2 = new Boulder(dungeon, 1, 2);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		
		// collect treasure1
		player.moveUp();
		player.moveLeft();
		assertFalse(dungeon.checkComplete());
		
		// push boulder2 onto switch2
		player.moveDown();
		player.moveDown();
		assertFalse(dungeon.checkComplete());
		
		// push boulder1 onto switch1, collecting treasure2 on the way
		// switch goal is complete so OR goal is complete
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveUp();
		player.moveLeft();
		player.moveUp();
		player.moveRight();
		assertTrue(switchGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
		// push boulder2 off switch2, making switch goal incomplete
		// so OR goal becomes incomplete again
		player.moveRight();
		assertFalse(switchGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
		// collect treasure3, completing treasure goal
		// so OR goal is complete again
		player.moveDown();
		player.moveDown();
		player.moveRight();
		assertTrue(treasureGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
	}
	
	@Test
	void goalMoreThanTwoSubgoals() {
		
		Treasure treasure1 = new Treasure(4, 1, dungeon);
		Treasure treasure2 = new Treasure(7, 1, dungeon);
		Treasure treasure3 = new Treasure(5, 4, dungeon);
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(treasure3);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		dungeonGoal.addSubgoal(treasureGoal);
		
		Enemy enemy1 = new Enemy(dungeon, 5, 2);
		Enemy enemy2 = new Enemy(dungeon, 1, 4);
		
		enemy1.setPlayer(player);
		enemy2.setPlayer(player);
		
		Sword sword = new Sword(3, 2, dungeon);
		InvincibilityPotion potion = new InvincibilityPotion(1, 2, dungeon);
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(sword);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		// things to block enemy
		Wall w1 = new Wall(0, 4, dungeon);
		Wall w2 = new Wall(1, 5, dungeon);
		Wall w3 = new Wall(2, 4, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		
		Door door = new Door(1, 3, 0, dungeon);
		Key key = new Key(1, 1, 0, dungeon);
		dungeon.addEntity(door);
		dungeon.addEntity(key);
		
		Boulder boulder1 = new Boulder(dungeon, 7, 3);
		Switch switch1 = new Switch(dungeon, 8, 3);
		dungeon.addEntity(boulder1);
		dungeon.addEntity(switch1);
		
		GoalSwitch switchGoal = new GoalSwitch(dungeon);
		dungeonGoal.addSubgoal(switchGoal);
		
		// get sword and kill first enemy
		player.moveRight();
		enemy1.move();
		enemy2.move();
		player.swingSwordRight();
		
		// enemy goal not yet complete
		assertFalse(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
		// get key and invincibility potion to open door
		// and kill other enemy
		player.moveUp();
		enemy2.move();
		player.moveLeft();
		enemy2.move();
		player.moveLeft();
		enemy2.move();
		player.moveDown();
		enemy2.move();
		assertTrue(player.isInvincible());
		player.moveDown(); // collides with enemy and enemy dies
		enemy2.move();
		
		// enemy no longer on grid
		ArrayList<Entity> grid13 = dungeon.getGridEntities(1, 3);
		assertFalse(grid13.contains(enemy2));
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(switchGoal.checkComplete());
		assertFalse(treasureGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
	}

	@Test
	void complexAndGoalWithinComplexGoal() {		
		GoalComplex andGoal = new GoalComplex(new GoalCheckerAnd());
		
		Treasure treasure1 = new Treasure(3, 2, dungeon);
		dungeon.addEntity(treasure1);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		andGoal.addSubgoal(treasureGoal);
		
		Exit exit = new Exit(4, 2, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		andGoal.addSubgoal(exitGoal);
		
		dungeonGoal.addSubgoal(andGoal);
		
		Enemy enemy = new Enemy(dungeon, 8, 5);
		enemy.setPlayer(player);
		dungeon.addEntity(enemy);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		// move right to collect treasure, completing treasure goal
		player.moveRight();
		assertTrue(treasureGoal.checkComplete());
		assertFalse(andGoal.checkComplete());
		assertFalse(dungeonGoal.checkComplete());
		
		// move right again to exit, completing AND goal thus completing dungeon OR goal
		player.moveRight();
		assertTrue(exitGoal.checkComplete());
		assertTrue(andGoal.checkComplete());
		assertTrue(dungeonGoal.checkComplete());

	}
	
	
	@Test
	void complexOrGoalWithinComplexGoal() {
		GoalComplex orGoal = new GoalComplex(new GoalCheckerOr());
		
		Treasure treasure1 = new Treasure(3, 2, dungeon);
		dungeon.addEntity(treasure1);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		orGoal.addSubgoal(treasureGoal);
		
		Exit exit = new Exit(4, 2, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		orGoal.addSubgoal(exitGoal);
		
		dungeonGoal.addSubgoal(orGoal);
		
		Enemy enemy = new Enemy(dungeon, 8, 5);
		enemy.setPlayer(player);
		dungeon.addEntity(enemy);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		// move right to collect treasure, completing treasure goal and OR goal
		player.moveRight();
		assertTrue(treasureGoal.checkComplete());
		assertFalse(exitGoal.checkComplete());
		assertTrue(orGoal.checkComplete());
		assertTrue(dungeonGoal.checkComplete());

	}

}
