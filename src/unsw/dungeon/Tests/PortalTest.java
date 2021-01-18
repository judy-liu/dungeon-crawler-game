package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class PortalTest {

	@Test
	void testPlayerTeleportBothPortalsFree() {
		Dungeon dungeon = new Dungeon(5, 5);
		Player player = new Player(dungeon, 2, 2);
		Portal portal = new Portal(dungeon, 0, 0, 0);
		Portal portal2 = new Portal(dungeon, 4, 4, 0);
		portal.setDest(portal2);
		portal2.setDest(portal);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(portal);
		dungeon.addEntity(portal2);
		
		assertEquals(portal.getX(), 0);
		assertEquals(portal.getY(), 0);
		assertEquals(portal2.getX(), 4);
		assertEquals(portal2.getY(), 4);
		
		player.moveLeft();
		player.moveLeft();
		player.moveUp();
		player.moveUp();
		
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 4);
		
		player.moveUp();
		
		assertEquals(player.getX(), 4);
		assertEquals(player.getY(), 3);
		
		player.moveDown();
		
		assertEquals(player.getX(), 0);
		assertEquals(player.getY(), 0);
		
	}
	
	private Dungeon dungeon2;
	private Player player2;
	private Portal portal1;
	private Portal portal2;
	
	@BeforeEach
	public void setUp() {
		dungeon2 = new Dungeon(7, 7);
		player2 = new Player(dungeon2, 0, 0);
		
		portal1 = new Portal(dungeon2, 2, 0, 0);
		portal2 = new Portal(dungeon2, 4, 4, 0);
		portal1.setDest(portal2);
		portal2.setDest(portal1);
		
		dungeon2.setPlayer(player2);
		dungeon2.addEntity(portal1);
		dungeon2.addEntity(portal2);
	}
	
	@Test
	void testBoulderTeleportSuccess() {
		
		Boulder boulder = new Boulder(dungeon2, 1, 0);

		dungeon2.addEntity(boulder);
		
		// player moves right which moves boulder onto portal
		// boulder is teleported because other portal is free
		player2.moveRight();
		
		assertEquals(player2.getX(), 1);
		assertEquals(player2.getY(), 0);
		
		assertEquals(boulder.getX(), 4);
		assertEquals(boulder.getY(), 4);
		
		// player moves right which moves player onto portal
		// boulder is moved one block to the right because that is
		// the players last direction and right side block is free
		// the player is teleported to the matching portal
		player2.moveRight();
		
		assertEquals(player2.getX(), 4);
		assertEquals(player2.getY(), 4);
		
		assertEquals(boulder.getX(), 5);
		assertEquals(boulder.getY(), 4);
		
	}
	
	@Test
	void testPlayerTeleportFail() {
		
		Boulder boulder = new Boulder(dungeon2, 1, 0);
		Wall wall = new Wall(5, 4, dungeon2);
		
		dungeon2.addEntity(boulder);
		dungeon2.addEntity(wall);
		
		// player moves right which moves boulder onto portal
		// boulder is teleported because other portal is free
		player2.moveRight();
		
		// move player onto portal
		// player is not teleported because the destination portal
		// contains a boulder which cannot be moved in the direction
		// the player moved onto the portal because there is a
		// wall blocking the boulder
		player2.moveRight();
		
		assertEquals(player2.getX(), 2);
		assertEquals(player2.getY(), 0);
		
		assertEquals(boulder.getX(), 4);
		assertEquals(boulder.getY(), 4);
		
	}
	
	@Test
	void testBoulderTeleportFail() {
		
		Boulder boulder1 = new Boulder(dungeon2, 1, 0);
		
		// creating boulder that blocks the destination portal
		Boulder boulder2 = new Boulder(dungeon2, 4, 4);
		
		dungeon2.addEntity(boulder1);
		dungeon2.addEntity(boulder2);
		
		// push the boulder next to it onto portal
		// boulder should sit on portal but not teleport
		player2.moveRight();
		
		assertEquals(boulder1.getX(), 2);
		assertEquals(boulder1.getY(), 0);
		
		// should push boulder to the next block on the right
		// player itself is on portal
		// player should be able to teleport and push the boulder
		// that sits on the destination portal to the right
		player2.moveRight();
		
		assertEquals(player2.getX(), 4);
		assertEquals(player2.getX(), 4);
		
		assertEquals(boulder1.getX(), 3);
		assertEquals(boulder1.getY(), 0);
		
		assertEquals(boulder2.getX(), 5);
		assertEquals(boulder2.getY(), 4);
		
	}
	
	@Test
	void testEnemyTeleportSuccess() {
		Enemy enemy = new Enemy(dungeon2, 3, 0);
		enemy.setPlayer(player2);
		
		dungeon2.addEntity(enemy);
		
		// moving right should make the enemy move left
		// if the enemy moves left it should move onto portal
		// enemy should be teleported
		player2.moveRight();
		enemy.move();
		
		assertEquals(enemy.getX(), 4);
		assertEquals(enemy.getY(), 4);
	}
	
	@Test
	void testEnemyTeleportFailure() {
		Enemy enemy = new Enemy(dungeon2, 3, 0);
		enemy.setPlayer(player2);
		Boulder boulder = new Boulder(dungeon2, 4, 4);
		
		dungeon2.addEntity(enemy);
		dungeon2.addEntity(boulder);
		
		// moving right should make the enemy move left
		// if the enemy moves left it should move onto portal
		// but destination portal contains a boulder 
		// so enemy cannot be teleported
		player2.moveRight();
		enemy.move();
		
		assertEquals(enemy.getX(), 2);
		assertEquals(enemy.getY(), 0);
	}

}
