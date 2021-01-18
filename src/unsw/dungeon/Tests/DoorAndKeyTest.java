package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class DoorAndKeyTest {

	private Dungeon dungeon;
	private Player player;
	private Door door1;
	private Door door2;
	private Key key1;
	private Key key2;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 5);
		player = new Player(dungeon, 1, 0);
		door1 = new Door(2, 2, 1, dungeon);
		door2 = new Door(1, 4, 2, dungeon);
		key1 = new Key(1, 1, 1, dungeon);
		key2 = new Key(3, 1, 2, dungeon);
		
		Wall wall1 = new Wall(0, 2, dungeon);
		Wall wall2 = new Wall(1, 2, dungeon);
		Wall wall3 = new Wall(3, 2, dungeon);
		Wall wall4 = new Wall(0, 4, dungeon);
		Wall wall5 = new Wall(2, 4, dungeon);
		Wall wall6 = new Wall(3, 4, dungeon);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(door1);
		dungeon.addEntity(door2);
		dungeon.addEntity(key1);
		dungeon.addEntity(key2);
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(wall3);
		dungeon.addEntity(wall4);
		dungeon.addEntity(wall5);
		dungeon.addEntity(wall6);
	}
	
	@Test
	void moveThroughLockedDoorFails() {
		
		player.moveRight();
		player.moveDown();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
		
		// try to move onto the door block but the door is locked
		// player should stay on same position
		player.moveDown();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
	}
	
	@Test
	void pickUpAndDropKey() {
		
		// pick up key1
		player.moveDown();
		Key k = (Key) player.getInventoryItem("key");
		
		ArrayList<Entity> pos11 = dungeon.getGridEntities(1, 1);
		
		assertEquals(k, key1);
		assertFalse(pos11.contains(key1));
		
		// pick up key2
		// should drop key1
		player.moveRight();
		player.moveRight();
		
		k = (Key) player.getInventoryItem("key");
		pos11 = dungeon.getGridEntities(1, 1);
		ArrayList<Entity> pos31 = dungeon.getGridEntities(3, 1);
		
		assertEquals(k, key2);
		assertTrue(pos11.contains(key1));
		assertFalse(pos31.contains(key2));
		
	}
	
	@Test
	void unlockDoorSuccess() {
		// pick up key1
		player.moveDown();
		
		// collide with door1
		player.moveRight();
		player.moveDown();
		
		// player is now on door block where the door is unlocked
		assertEquals(player.getX(), door1.getX());
		assertEquals(player.getY(), door1.getY());
		
		// door is not solid i.e. entities can go on top of it
		assertFalse(door1.isSolid());
		
		player.moveDown();
		
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 3);
	}
	
	@Test
	void unlockDoorFail() {
		// pick up key2
		player.moveRight();
		player.moveRight();
		player.moveDown();
		
		Key k = (Key) player.getInventoryItem("key");
		
		assertEquals(k, key2);
		
		// try to go through door1
		// should fail because player has key2 not key1
		player.moveLeft();
		player.moveDown();
		
		assertTrue(door1.isSolid());
		
		// player is on the block above the door block not on the door
		assertEquals(player.getX(), 2);
		assertEquals(player.getY(), 1);
	}
	

}
