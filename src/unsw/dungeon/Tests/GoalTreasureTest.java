package unsw.dungeon.Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class GoalTreasureTest {	
	
	private Dungeon dungeon;
	private Player player;
	private Treasure t1;
	private Treasure t2;
	private Treasure t3;
	private Treasure t4;
	private GoalTreasure goal;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 0, 2);
		t1 = new Treasure(0, 1, dungeon);
		t2 = new Treasure(1, 1, dungeon);
		t3 = new Treasure(2, 1, dungeon);
		t4 = new Treasure(3, 1, dungeon);
		goal = new GoalTreasure(dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(t1);
		dungeon.addEntity(t2);
		dungeon.addEntity(t3);
		dungeon.addEntity(t4);
		dungeon.setGoal(goal);
	}
	
	// check that goal treasure test is adding treasures correctly to its list
	@Test
	void testAddTreasureToGoal() {
		System.out.println("~~~~~~~~~~~~~~~~ testAddTreasureToGoal() ~~~~~~~~~~~~~~~~"); 
		
		goal.addTreasureToGoal();
		
		assertEquals(goal.numberObserved(), 4);
		assertFalse(dungeon.checkComplete());
				
	}
	
	// check that goal is not satisfied when no treasure has been collected to inventory
	@Test
	void testCompleteFalse() {
		System.out.println("~~~~~~~~~~~~~~~~ testCompleteFalse() ~~~~~~~~~~~~~~~~"); 
		
		goal.addTreasureToGoal();
		
		goal.checkComplete();
		
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());
			
	}
	
	// check that goal is satisfied when player moves and collects treasure to their inventory
	@Test
	void testCompleteTruePlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testCompleteTruePlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addTreasureToGoal(); // add all instances of treasure to goal
		
		player.moveUp();
		player.moveRight();
		player.moveRight();
		player.moveRight();
		
		assertTrue(goal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
	}
	
	// check that goal is not satisfied when player moves but doesnt not collect enough treasure
	@Test
	void testCompleteFalsePlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testCompleteFalsesPlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addTreasureToGoal(); // add all instances of treasure to goal
		
		player.moveUp();
		player.moveRight();
		player.moveRight();
		player.moveDown();
		
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());

		
	}
	
	// check that goal is not satisfied when player hasn't collected anything
	@Test
	void testEmptyPlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testEmptyPlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addTreasureToGoal(); // add all instances of treasure to goal
		
		assertFalse(goal.checkComplete());
		
		player.moveDown();
		
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());

		
	}
}
