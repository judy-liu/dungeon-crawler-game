package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class GoalComplexTestAnd {
	
	private Dungeon dungeon;
	private Player player;
	private GoalComplex dungeonGoal;

	@BeforeEach
	void setUp() {
		dungeon = new Dungeon(9, 6);
		player = new Player(dungeon, 2, 2);
		dungeonGoal = new GoalComplex(new GoalCheckerAnd());
		
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
		
		// move player to exit
		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		
		// treasure goal not complete because treasure1 not collected
		assertFalse(treasureGoal.checkComplete());
		
		// exit goal complete
		assertTrue(exitGoal.checkComplete());
		
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
		// switch goal is complete
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveUp();
		player.moveLeft();
		player.moveUp();
		player.moveRight();
		assertTrue(switchGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
		// collect treasure3
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		assertTrue(treasureGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
	}
	
	// complete treasure and enemy goals
	@Test
	void goalCompleteTreasureEnemy() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("DSADSADASDSADS");
		Treasure treasure1 = new Treasure(1, 1, dungeon);
		Treasure treasure2 = new Treasure(2, 1, dungeon);
		Treasure treasure3 = new Treasure(3, 1, dungeon);
		dungeon.addEntity(treasure1);
		dungeon.addEntity(treasure2);
		dungeon.addEntity(treasure3);
		GoalTreasure treasureGoal = new GoalTreasure(dungeon);
		dungeonGoal.addSubgoal(treasureGoal);
		
		Enemy e1 = new Enemy(dungeon, 4, 2);
		e1.setPlayer(player);
		InvincibilityPotion potion = new InvincibilityPotion(3, 2, dungeon);
		dungeon.addEntity(e1);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		Wall w1 = new Wall(5, 1, dungeon);
		Wall w2 = new Wall(5, 2, dungeon);
		Wall w3 = new Wall(4, 3, dungeon);
		Wall w4 = new Wall(4, 1, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		dungeon.addEntity(w4);
		
		player.moveRight(); // get potion
		player.moveRight();
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
		// now collect all treasure
		 
		player.moveLeft();
		player.moveUp();
		player.moveLeft();
		player.moveLeft();
		player.moveLeft();
		
		assertTrue(treasureGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
	}
	
	@Test
	void goalCompleteEnemyExit() {
		
		Exit exit = new Exit(8, 5, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		dungeonGoal.addSubgoal(exitGoal);
		
		Enemy e1 = new Enemy(dungeon, 4, 2);
		e1.setPlayer(player);
		InvincibilityPotion potion = new InvincibilityPotion(3, 2, dungeon);
		dungeon.addEntity(e1);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		Wall w1 = new Wall(5, 1, dungeon);
		Wall w2 = new Wall(5, 2, dungeon);
		Wall w3 = new Wall(4, 3, dungeon);
		Wall w4 = new Wall(4, 1, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		dungeon.addEntity(w4);
		
		player.moveRight(); // get potion
		player.moveRight();
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		assertFalse(exitGoal.checkComplete());
		
		player.moveLeft();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertTrue(exitGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
	}
	
	@Test
	void goalCompleteEnemySwitchExit() {
		Exit exit = new Exit(8, 5, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		dungeonGoal.addSubgoal(exitGoal);
		
		Enemy e1 = new Enemy(dungeon, 4, 2);
		e1.setPlayer(player);
		InvincibilityPotion potion = new InvincibilityPotion(3, 2, dungeon);
		dungeon.addEntity(e1);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoal.addSubgoal(enemyGoal);
		
		Switch switch1 = new Switch(dungeon, 3, 0);
		dungeon.addEntity(switch1);
		Boulder boulder = new Boulder(dungeon, 3, 1);
		dungeon.addEntity(boulder);
		GoalSwitch switchGoal = new GoalSwitch(dungeon);
		dungeonGoal.addSubgoal(switchGoal);
		
		Wall w1 = new Wall(5, 1, dungeon);
		Wall w2 = new Wall(5, 2, dungeon);
		Wall w3 = new Wall(4, 3, dungeon);
		Wall w4 = new Wall(4, 1, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		dungeon.addEntity(w4);
		
		player.moveRight(); // get potion
		player.moveRight(); // kill enemy
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		assertFalse(exitGoal.checkComplete());
		
		player.moveLeft(); 
		player.moveUp(); // push boulder onto switch
		
		assertTrue(switchGoal.checkComplete());
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertTrue(exitGoal.checkComplete());
		assertTrue(dungeon.checkComplete());
	}
	
	
	@Test
	void complexAndGoalWithinComplexGoal() {
		// ( this and this ) and this
		// ( switch and enemy ) and exit
		
		Exit exit = new Exit(8, 5, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		
		GoalComplex dungeonAndGoalLeaf = new GoalComplex(new GoalCheckerAnd());
		
		Enemy e1 = new Enemy(dungeon, 4, 2);
		e1.setPlayer(player);
		InvincibilityPotion potion = new InvincibilityPotion(3, 2, dungeon);
		dungeon.addEntity(e1);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonAndGoalLeaf.addSubgoal(enemyGoal);
		
		Switch switch1 = new Switch(dungeon, 3, 0);
		dungeon.addEntity(switch1);
		Boulder boulder = new Boulder(dungeon, 3, 1);
		dungeon.addEntity(boulder);
		GoalSwitch switchGoal = new GoalSwitch(dungeon);
		dungeonAndGoalLeaf.addSubgoal(switchGoal);
		
		dungeonGoal.addSubgoal(dungeonAndGoalLeaf);
		dungeonGoal.addSubgoal(exitGoal);
		
		assertFalse(dungeonAndGoalLeaf.checkComplete());
		
		Wall w1 = new Wall(5, 1, dungeon);
		Wall w2 = new Wall(5, 2, dungeon);
		Wall w3 = new Wall(4, 3, dungeon);
		Wall w4 = new Wall(4, 1, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		dungeon.addEntity(w4);
		
		player.moveRight(); // get potion
		player.moveRight(); // kill enemy
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		assertFalse(exitGoal.checkComplete());
		
		player.moveLeft(); 
		player.moveUp(); // push boulder onto switch
		
		assertTrue(switchGoal.checkComplete());
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertTrue(exitGoal.checkComplete());
		assertTrue(dungeonAndGoalLeaf.checkComplete());
		assertTrue(dungeon.checkComplete());	
		
	}
	

	@Test
	void complexOrGoalWithinComplexGoal() {
		Exit exit = new Exit(8, 5, dungeon);
		dungeon.addEntity(exit);
		GoalExit exitGoal = new GoalExit(dungeon, exit);
		
		GoalComplex dungeonGoalOrLeaf = new GoalComplex(new GoalCheckerOr());
		// this OR goal will have enemy or switch
		
		Enemy e1 = new Enemy(dungeon, 4, 2);
		e1.setPlayer(player);
		InvincibilityPotion potion = new InvincibilityPotion(3, 2, dungeon);
		dungeon.addEntity(e1);
		dungeon.addEntity(potion);
		GoalEnemy enemyGoal = new GoalEnemy(dungeon);
		dungeonGoalOrLeaf.addSubgoal(enemyGoal);
		
		Switch switch1 = new Switch(dungeon, 3, 0);
		dungeon.addEntity(switch1);
		Boulder boulder = new Boulder(dungeon, 3, 1);
		dungeon.addEntity(boulder);
		GoalSwitch switchGoal = new GoalSwitch(dungeon);
		dungeonGoalOrLeaf.addSubgoal(switchGoal);
		
		dungeonGoal.addSubgoal(dungeonGoalOrLeaf);
		dungeonGoal.addSubgoal(exitGoal);
		
		assertFalse(dungeonGoalOrLeaf.checkComplete());
		
		Wall w1 = new Wall(5, 1, dungeon);
		Wall w2 = new Wall(5, 2, dungeon);
		Wall w3 = new Wall(4, 3, dungeon);
		Wall w4 = new Wall(4, 1, dungeon);
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);
		dungeon.addEntity(w3);
		dungeon.addEntity(w4);
		
		player.moveRight(); // get potion
		player.moveRight(); // kill enemy
		
		assertTrue(enemyGoal.checkComplete());
		assertFalse(dungeon.checkComplete());
		assertFalse(exitGoal.checkComplete());
		
//		player.moveLeft(); 
//		player.moveUp(); // push boulder onto switch
		
		assertFalse(switchGoal.checkComplete());
		
		player.moveLeft();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertTrue(exitGoal.checkComplete());
		assertTrue(dungeonGoalOrLeaf.checkComplete());
		assertTrue(dungeon.checkComplete());
	}

	
}
