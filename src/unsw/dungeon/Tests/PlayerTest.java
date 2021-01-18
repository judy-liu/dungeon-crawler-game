package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class PlayerTest {
	
	private Dungeon dungeon;
	private Player player;
	private Treasure t1;
	private Sword sword;
	
	@BeforeEach
	void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 2, 1);
		t1 = new Treasure(1, 1, dungeon);
		sword = new Sword(1, 0, dungeon);
		
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(t1);
		dungeon.addEntity(sword);
		
	}

	@Test
	void testAddToInventory() {
		assertEquals(player.getInventory().size(), 0);
		player.moveLeft();
		assertEquals(player.getInventory().size(), 1);
	}
	
	@Test
	void testRemoveFromInventory() {
		assertEquals(player.getInventory().size(), 0);
		player.moveLeft();
		assertEquals(player.getInventory().size(), 1);
		
		player.moveUp();
		assertEquals(player.getInventory().size(), 2);
		player.removeItem("sword");
		assertEquals(player.getInventory().size(), 1);
	}
	
	@Test
	void testGetItem() {
		assertEquals(player.getInventory().size(), 0);
		player.moveLeft();
		assertEquals(player.getInventory().size(), 1);
		
		player.moveUp();
		assertEquals(player.getInventory().size(), 2);
		Sword s = (Sword) player.getInventoryItem("sword");
		assertNotNull(s);
	}
}
