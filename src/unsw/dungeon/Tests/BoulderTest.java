package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class BoulderTest {

	private Dungeon dungeon;
	private Player player;
	private Boulder boulder;
	
	@BeforeEach
	void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 1, 3);
		boulder = new Boulder(dungeon, 1, 2);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(boulder);
	}

	@Test
	void canMove() {
		// push boulder in 4 directions
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 1);
		
		player.moveLeft();
		player.moveUp();
		player.moveRight();
		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 1);
		
		player.moveUp();
		player.moveRight();
		player.moveDown();
		assertEquals(boulder.getX(), 2);
		assertEquals(boulder.getY(), 2);
		
		player.moveRight();
		player.moveDown();
		player.moveLeft();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 2);
		
	}
	
	@Test
	void blockedByOtherBoulder() {
		Boulder boulder2 = new Boulder(dungeon, 1, 1);
		dungeon.addEntity(boulder2);
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 2);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
	}
	
	@Test
	void blockedByWall() {
		Wall wall = new Wall(1, 1, dungeon);
		dungeon.addEntity(wall);
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 2);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
	}
	
	@Test
	void blockedByEnemy() {
		Enemy enemy = new Enemy(dungeon, 1, 1);
		dungeon.addEntity(enemy);
		enemy.setPlayer(player);
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 2);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
	}
	
	@Test
	void blockedByLockedDoor() {
		Door door = new Door(1, 1, 0, dungeon);
		dungeon.addEntity(door);
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 2);
		assertEquals(player.getX(), 1);
		assertEquals(player.getY(), 3);
	}

	@Test
	void notBlockedByNonSolids() {
		Treasure treasure = new Treasure(1, 1, dungeon);
		Switch switch1 = new Switch(dungeon, 1, 1);
		Sword sword = new Sword(1, 1, dungeon);
		InvincibilityPotion potion = new InvincibilityPotion(1, 1, dungeon);
		Key key = new Key(1, 1, 0, dungeon);
		
		dungeon.addEntity(key);
		dungeon.addEntity(potion);
		dungeon.addEntity(sword);
		dungeon.addEntity(switch1);
		dungeon.addEntity(treasure);
		
		player.moveUp();
		assertEquals(boulder.getX(), 1);
		assertEquals(boulder.getY(), 1);
	}
	
}
