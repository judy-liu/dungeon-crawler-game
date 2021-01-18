package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.GoalExit;
import unsw.dungeon.Player;

class GoalExitTest {

	private Dungeon dungeon;
	private Player player;
	private Exit exit;
	private GoalExit goal;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 1, 1);
		exit = new Exit(3, 3, dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(exit);
		goal = new GoalExit(dungeon, exit);
		dungeon.setGoal(goal);
	}
	
	@Test
	void testPlayerReachesGoal() {

		player.moveRight();
		player.moveRight();
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
		player.moveDown();
		player.moveDown();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);
		assertTrue(goal.checkComplete());
		assertTrue(dungeon.checkComplete());
	}
	
	@Test
	void testPlayerReachesThenLeavesGoal() {

		player.moveRight();
		player.moveRight();
		player.moveDown();
		player.moveDown();
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 3);
		assertTrue(goal.checkComplete());
		assertTrue(dungeon.checkComplete());
		
		player.moveLeft();
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);
		assertFalse(goal.checkComplete());
		assertFalse(dungeon.checkComplete());
		
	}
	

}
