package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class PlayerMovementTest {

	Dungeon dungeon;
	Player player;
	Wall w1;
	Wall w2;
	Wall w3;
	Wall w4;
	
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 2, 1);
		w1 = new Wall(1, 2, dungeon);
		w2 = new Wall(3, 0, dungeon);
		w3 = new Wall(3, 2, dungeon);
		w4 = new Wall(2, 3, dungeon);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);	
		dungeon.addEntity(w1);
		dungeon.addEntity(w2);	
		dungeon.addEntity(w3);	
		dungeon.addEntity(w4);	
	}
	
	@Test
	void testMoveUp() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveUp();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 0);
	}
	
	@Test
	void testMoveDown() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveDown();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
	}
	
	@Test
	void testMoveLeft() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveLeft();
		
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 1);
	}
	
	@Test
	void testMoveRight() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveRight();
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
	}
	
	// test that player cannot move through walls
	
	@Test
	void testMoveFailByWall() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveRight();
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
		player.moveDown(); // blocked by wall
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
		player.moveUp(); // blocked by wall
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
		player.moveLeft();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveDown();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
		
		player.moveRight(); // blocked by wall
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 2);
	}
	
	// test that player cannot move off of the dungeon's border
	@Test
	void testMoveFailByBorder() {
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		player.moveRight();
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
		
		player.moveRight(); // can't go beyond scope of dungeon
		
		assertEquals(player.getX(), 3);
		assertEquals(player.getY(), 1);
	}

}
