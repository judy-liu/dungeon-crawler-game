package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class EnemyTest {

	private Dungeon dungeon;
	private Player player;
	private Enemy enemy;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(6, 5);
		player = new Player(dungeon, 1, 1);
		enemy = new Enemy(dungeon, 4, 2);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(enemy);
		enemy.setPlayer(player);
		
	}

	@Test
	public void testEnemyBecomesObserver() {
		// assert that enemy has attached itself to the player
		assertEquals(player.getObservers().size(), 1);
		assertTrue(player.getObservers().contains(enemy));
	}
	
	@Test
	public void testEnemyMovesCloser() {
		player.moveRight();
		enemy.move();
		
		// since player moved right and enemy is to the right of the enemy
		// the enemy must not move right itself
		assertNotEquals(enemy.getX(), 5);
		
		// player y-position is above enemy so the enemy
		// should move up or stay in current position
		assertNotEquals(enemy.getY(), 3);
		
	}
	
	@Test
	public void testEnemyMovesAway() {
		InvincibilityPotion potion = new InvincibilityPotion(0, 0, dungeon);
		dungeon.addEntity(potion);
		
		player.moveUp();
		enemy.move();
		player.moveLeft();
		enemy.move();
		
		assertEquals(player.isInvincible(), true);
		
		int x = enemy.getX(); 	// x-value at the time player becomes invincible
		int y = enemy.getY();   // y-value at the time player becomes invincible
		
		// enemy never comes closer to the player
		// compared to its original position
		player.moveRight();
		enemy.move();
		
		assertTrue(enemy.getX() >= x);
		assertTrue(enemy.getY() >= y);
		
		player.moveLeft();
		enemy.move();
		
		assertTrue(enemy.getX() >= x);
		assertTrue(enemy.getY() >= y);
		
		player.moveDown();
		enemy.move();
		assertTrue(enemy.getX() >= x);
		
	}

	
	@Test
	public void testPlayerDiesSameBlock() {
		dungeon = new Dungeon(6, 5);
		player = new Player(dungeon, 0, 0);
		enemy = new Enemy(dungeon, 2, 0);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(enemy);
		enemy.setPlayer(player);
		
		player.moveRight();
		enemy.move();
		
		assertFalse(dungeon.getAlive().get());
		
	}
	
	@Test
	public void testPlayerDiesCrossingBlock() {
		dungeon = new Dungeon(6, 5);
		player = new Player(dungeon, 0, 0);
		enemy = new Enemy(dungeon, 1, 0);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(enemy);
		enemy.setPlayer(player);
		
		player.moveRight();
		enemy.move();
		
		assertFalse(dungeon.getAlive().get());
	}
	
	
	@Test
	public void testBlockedByWall() {
		
		// create walls to block three of the four
		// enemy directions
		Wall wall1 = new Wall(4, 1, dungeon);
		Wall wall2 = new Wall(3, 2, dungeon);
		Wall wall3 = new Wall(4, 3, dungeon);
		
		dungeon.addEntity(wall1);
		dungeon.addEntity(wall2);
		dungeon.addEntity(wall3);
		
		player.moveLeft();
		enemy.move();
		
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 2);

	}
	
	@Test
	public void testBlockedByBoulder() {
		
		// create boulders to block three of the four
		// enemy directions
		Boulder boulder1 = new Boulder(dungeon, 4, 1);
		Boulder boulder2 = new Boulder(dungeon, 3, 2);
		Boulder boulder3 = new Boulder(dungeon, 4, 3);
		
		dungeon.addEntity(boulder1);
		dungeon.addEntity(boulder2);
		dungeon.addEntity(boulder3);
		
		player.moveLeft();
		enemy.move();
		
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 2);
	}
	
	@Test
	public void testBlockedByLockedDoor() {
		// create doors to block three of the four
		// enemy directions
		Door door1 = new Door(4, 1, 0, dungeon);
		Door door2 = new Door(3, 2, 1, dungeon);
		Door door3 = new Door(4, 3, 2, dungeon);
		
		dungeon.addEntity(door1);
		dungeon.addEntity(door2);
		dungeon.addEntity(door3);
		
		player.moveLeft();
		enemy.move();
		
		assertEquals(enemy.getX(), 5);
		assertEquals(enemy.getY(), 2);
	}
	
	
	@Test
	public void testNotBlockedByUnlockedDoor() {
		// create doors to block three of the four enemy directions
		// and then one door is opened by player
		Door door1 = new Door(4, 1, 0, dungeon);
		Door door2 = new Door(3, 2, 1, dungeon);
		Door door3 = new Door(4, 3, 2, dungeon);
		Door door4 = new Door(5, 2, 3, dungeon);
		
		Key key1 = new Key(1, 2, 1, dungeon);
		
		dungeon.addEntity(door1);
		dungeon.addEntity(door2);
		dungeon.addEntity(door3);
		dungeon.addEntity(door4);
		dungeon.addEntity(key1);
		
		player.moveDown();
		enemy.move();
		
		assertEquals(enemy.getX(), 4);
		assertEquals(enemy.getY(), 2);
		
		player.moveRight();
		enemy.move();
		
		assertEquals(enemy.getX(), 4);
		assertEquals(enemy.getY(), 2);
		
		// this move unlocks the door
		player.moveRight();
		enemy.move();
		
		// enemy has now moved in the direction of the unlocked door
		assertEquals(enemy.getX(), 3);
		assertEquals(enemy.getY(), 2);
		
	}
	
	

}
