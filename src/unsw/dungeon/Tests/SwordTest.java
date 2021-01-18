package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class SwordTest {

	private Dungeon dungeon;
	private Player player;
	private Sword sword;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(7, 5);
		player = new Player(dungeon, 2, 1);
		sword = new Sword(2, 2, dungeon);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(sword);	
	}
	
	@Test
	void cannotPickUpTwoSwords() {
		Sword sword2 = new Sword(2, 3, dungeon);
		dungeon.addEntity(sword2);
		
		// obtain the sword
		player.moveDown();
		
		// check the player has a sword in inventory
		// and that the grid no longer has the sword
		Sword s = (Sword) player.getInventoryItem("sword");
		ArrayList<Entity> grid22 = dungeon.getGridEntities(2, 2);
		assertEquals(s, sword);
		assertFalse(grid22.contains(sword));
		
		player.moveDown();
		ArrayList<Entity> grid23 = dungeon.getGridEntities(2, 3);
		assertTrue(grid23.contains(sword2));
	}

	
	@Test
	void enemyDiesWhenHit() {
		Enemy enemy = new Enemy(dungeon, 2, 0);
		enemy.setPlayer(player);
		dungeon.addEntity(enemy);
		
		// obtain the sword
		player.moveDown();
		enemy.move();
		
		// swing up to kill enemy
		player.swingSwordUp();
		
		// enemy is no longer in the above block
		ArrayList<Entity> grid21 = dungeon.getGridEntities(2, 1);
		assertFalse(grid21.contains(enemy));
		
		// sword usage is now 4
		assertEquals(sword.getHits().get(), 4);
	}
	
	@Test
	void emptySwingNoDecrease() {
		
		// obtain the sword
		player.moveDown();

		player.swingSwordDown();
		player.swingSwordUp();
		player.swingSwordLeft();
		player.swingSwordRight();
		
		// sword usage is still 5
		assertEquals(sword.getHits().get(), 5);
		
		// move player to left edge of dungeon
		player.moveLeft();
		player.moveLeft();
		
		// swing sword left should not work as it is outside of dungeon
		player.swingSwordLeft();
		assertEquals(sword.getHits().get(), 5);
		
	}
	
	@Test
	void upToFiveHits() {
		Enemy enemy1 = new Enemy(dungeon, 2, 0); // up enemy
		Enemy enemy2 = new Enemy(dungeon, 0, 2); // left enemy
		Enemy enemy3 = new Enemy(dungeon, 2, 4); // down enemy
		Enemy enemy4 = new Enemy(dungeon, 4, 2); // right enemy
		Enemy enemy5 = new Enemy(dungeon, 6, 2); // right right enemy
		
		dungeon.addEntity(enemy1);
		dungeon.addEntity(enemy2);
		dungeon.addEntity(enemy3);
		dungeon.addEntity(enemy4);
		dungeon.addEntity(enemy5);
		
		enemy1.setPlayer(player);
		enemy2.setPlayer(player);
		enemy3.setPlayer(player);
		enemy4.setPlayer(player);
		enemy5.setPlayer(player);
		
		// obtain sword
		player.moveDown();
		enemy1.move();
		enemy2.move();
		enemy3.move();
		enemy4.move();
		enemy5.move();
		
		assertEquals(enemy1.getX(), 2);
		assertEquals(enemy1.getY(), 1);
		
		assertEquals(enemy2.getX(), 1);
		assertEquals(enemy2.getY(), 2);
		
		assertEquals(enemy3.getX(), 2);
		assertEquals(enemy3.getY(), 3);
		
		assertEquals(enemy4.getX(), 3);
		assertEquals(enemy4.getY(), 2);
		
		// kill the adjacent 4 enemies
		player.swingSwordDown();
		player.swingSwordUp();
		player.swingSwordLeft();
		player.swingSwordRight();
		
		assertEquals(sword.getHits().get(), 1);
		
		// move player towards right right enemy
		player.moveRight();
		enemy5.move();
		player.swingSwordRight();
		
		// no more hits from sword
		assertEquals(sword.getHits().get(), 0);
		
		// sword is no longer in players inventory
		assertEquals(player.getInventoryItem("sword"), null);
		assertEquals(player.getInventory().size(), 0);
	}

}
