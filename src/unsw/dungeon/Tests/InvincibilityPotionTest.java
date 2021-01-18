package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class InvincibilityPotionTest {

	private Dungeon dungeon;
	private Player player;
	private Enemy enemy;
	private InvincibilityPotion ip;
	private InvincibilityPotion ip1;
	
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 1, 1);
		enemy = new Enemy(dungeon, 2, 2);
		enemy.setPlayer(player);
		ip = new InvincibilityPotion(1, 0, dungeon);
		ip1 = new InvincibilityPotion(0, 0, dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(enemy);
		dungeon.addEntity(ip);
		dungeon.addEntity(ip1);
			
	}
	
	@Test
	public void testInvincibility() {
		System.out.println("~~~~~~~~~~~~~~~~ testInvincibility() ~~~~~~~~~~~~~~~~");
		player.moveUp();
		
		// assert player is invincible now
		assertTrue(player.isInvincible());
	}
	
	@Test
	void testCantPickupAnother() {
		player.moveUp();
		assertTrue(player.isInvincible());
		
		player.moveLeft();
		assertTrue(player.isInvincible());
	}
	
	@Test
	public void testInvincibilityLimit() {
		System.out.println("~~~~~~~~~~~~~~~~ testInvincibilityLimit() ~~~~~~~~~~~~~~~~"); 
		player.moveUp();
		// assert player is invincible now
		assertTrue(player.isInvincible());
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 9);
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 8);
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 7);
		
		player.moveUp();
		player.moveUp();
		player.moveUp();
		assertEquals(player.getInvincibilityState(), 4);
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		
		assertEquals(player.getInvincibilityState(), 1);
		
		player.moveUp();
		assertEquals(player.getInvincibilityState(), 0);
		
		
		player.moveUp();
		assertEquals(player.getInvincibilityState(), 0);
	}
	
	
	@Test
	public void testInvincibilityEffectOnEnemySuccessful() {
		System.out.println("~~~~~~~~~~~~~~~~ testInvincibilityEffectOnEnemySuccessful() ~~~~~~~~~~~~~~~~"); 
		
		Dungeon dungeon2 = new Dungeon(1, 4);
		Player player2 = new Player(dungeon2, 0, 3);
		InvincibilityPotion ip2 = new InvincibilityPotion(0, 2, dungeon2);
		Enemy enemy2 = new Enemy(dungeon2, 0, 1);
		enemy2.setPlayer(player2);
		dungeon2.setPlayer(player2);
		dungeon2.addEntity(player2);
		dungeon2.addEntity(enemy2);
		dungeon2.addEntity(ip2);
							
		// check that the enemy still exists in the dungeon.
		int count = 0;
		for (int i = 0; i < dungeon2.getWidth(); i++) {
			for (int j = 0; j < dungeon2.getHeight(); j++) {
				ArrayList<Entity> s = dungeon2.getGridEntities(i, j);
				for (Entity e : s) {
					if (e.getClass() == Enemy.class) {
						count++;
					}
				}
			}
		}
		
		assertEquals(count, 1);
		
		player2.moveUp();
		player2.moveUp();
				
		// check enemy is dead
		count = 0;
		for (int i = 0; i < dungeon2.getWidth(); i++) {
			for (int j = 0; j < dungeon2.getHeight(); j++) {
				ArrayList<Entity> s = dungeon2.getGridEntities(i, j);
				for (Entity e : s) {
					if (e.getClass() == Enemy.class) {
						count++;
					}
				}
			}
		}
		
		assertEquals(count, 0);
			
	}
	
	@Test
	public void testInvincibilityEffectOnEnemyFail() {
		System.out.println("~~~~~~~~~~~~~~~~ testInvincibilityEffectOnEnemyFail() ~~~~~~~~~~~~~~~~"); 

		player.moveUp();
		// assert player is invincible now
		assertEquals(player.isInvincible(), true);
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 9);
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 8);
		
		player.moveDown();
		assertEquals(player.getInvincibilityState(), 7);
		
		player.moveUp();
		player.moveUp();
		player.moveUp();
		assertEquals(player.getInvincibilityState(), 4);
		
		player.moveDown();
		player.moveDown();
		player.moveDown();
		
		assertEquals(player.getInvincibilityState(), 1);
		
		player.moveUp();
		assertEquals(player.getInvincibilityState(), 0);
		
		player.moveLeft();
		assertEquals(player.getInvincibilityState(), 0);
		
		// check that the enemy still exists in the dungeon.
		int count = 0;
		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int j = 0; j < dungeon.getHeight(); j++) {
				ArrayList<Entity> s = dungeon.getGridEntities(i, j);
				for (Entity e : s) {
					if (e.getClass() == Enemy.class) {
						count++;
					}
				}
			}
		}
		
		assertEquals(count, 1);
		
	}
	
	@Test
	void testRunoutOfEffect() {
		
		System.out.println("~~~~~~~~~~~~~~~~ testRunoutOfEffect() ~~~~~~~~~~~~~~~~");
		Dungeon dungeon2 = new Dungeon(1, 12); // v long dungeon
		Player player2 = new Player(dungeon2, 0, 11);
		InvincibilityPotion ip2 = new InvincibilityPotion(0, 10, dungeon2);
		Enemy enemy2 = new Enemy(dungeon2, 0, 0);
		enemy.setPlayer(player2);
		dungeon2.setPlayer(player2);
		dungeon2.addEntity(player2);
		dungeon2.addEntity(enemy2);
		dungeon2.addEntity(ip2);
		
		// check enemy is still alive
		int count = 0;
		for (int i = 0; i < dungeon2.getWidth(); i++) {
			for (int j = 0; j < dungeon2.getHeight(); j++) {
				ArrayList<Entity> s = dungeon2.getGridEntities(i, j);
				for (Entity e : s) {
					if (e.getClass() == Enemy.class) {
						count++;
					}
				}
			}
		}
		
		assertEquals(count, 1);
		
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp();
		player2.moveUp(); 
		player2.moveDown();
		player2.moveUp();
		player2.moveUp();
		
		// check enemy is still alive
		count = 0;
		for (int i = 0; i < dungeon2.getWidth(); i++) {
			for (int j = 0; j < dungeon2.getHeight(); j++) {
				ArrayList<Entity> s = dungeon2.getGridEntities(i, j);
				for (Entity e : s) {
					if (e.getClass() == Enemy.class) {
						count++;
					}
				}
			}
		}
		
		assertEquals(count, 1);		
		
	}

}

