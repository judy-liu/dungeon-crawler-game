package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class GoalSwitchTest {

	private Dungeon dungeon;
	private Player player;
	private Switch s1;
	private Switch s2;
	private Switch s3;
	private Switch s4;
	private GoalSwitch goal;
	private Boulder b1;
	private Boulder b2;
	private Boulder b3;
	private Boulder b4;
	
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 0, 3);
		s1 = new Switch(dungeon, 0, 1);
		s2 = new Switch(dungeon, 1, 1);
		s3 = new Switch(dungeon, 2, 1);
		s4 = new Switch(dungeon, 3, 1);
		b1 = new Boulder(dungeon, 0, 2);
		b2 = new Boulder(dungeon, 1, 2);
		b3 = new Boulder(dungeon, 2, 2);
		b4 = new Boulder(dungeon, 3, 2);
		goal = new GoalSwitch(dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(s1);
		dungeon.addEntity(s2);
		dungeon.addEntity(s3);
		dungeon.addEntity(s4);
		dungeon.addEntity(b1);
		dungeon.addEntity(b2);
		dungeon.addEntity(b3);
		dungeon.addEntity(b4);
		dungeon.setGoal(goal);
	}
	
	// check that goal switch test is adding switches correctly to its list
	@Test
	void testAddSwitchesToGoal() {
		System.out.println("~~~~~~~~~~~~~~~~ testAddSwitchesToGoal() ~~~~~~~~~~~~~~~~"); 
		
		goal.addSwitchesToGoal();
		
		assertEquals(goal.numberObserved(), 4);
		assertFalse(dungeon.checkComplete());

				
	}
	
	// check that goal is satisfied when player moves boulders onto switches.
	@Test
	void testCompleteTruePlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testCompleteTruePlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addSwitchesToGoal();
		
		assertFalse(goal.checkComplete());
		
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		
		assertTrue(goal.checkComplete());
		assertTrue(dungeon.checkComplete());

		
	}
	
	// check that goal is not satisfied when player moves but doesnt trigger enough switches
	@Test
	void testCompleteFalsePlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testCompleteFalsePlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addSwitchesToGoal();
		assertFalse(goal.checkComplete());
		
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());

		
	}
	
	// check that goal is not satisfied when player hasn't triggered anything
	@Test
	void testEmptyPlayer() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testEmptyPlayer() ~~~~~~~~~~~~~~~~"); 

		goal.addSwitchesToGoal();		
		assertFalse(goal.checkComplete());
		
		player.moveRight();
		
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());

		
	}
	
	// check that goal is not satisfied when player untriggers a switch
	@Test
	void testUntriggerSwitch() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testUntriggerSwitch() ~~~~~~~~~~~~~~~~"); 

		goal.addSwitchesToGoal();
		
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveDown();
		player.moveRight();
		player.moveUp();
		player.moveUp(); // push boulder off of switch
		
		assertEquals(goal.checkComplete(), false);
		assertEquals(dungeon.checkComplete(), false);

		
	}

}
