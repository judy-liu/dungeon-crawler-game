package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Treasure;

class TreasureTest {

	private Dungeon dungeon;
	private Player player;
	private Treasure t1;
	private Treasure t2;
	private Treasure t3;
	private Treasure t4;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(1, 5);
		player = new Player(dungeon, 0, 4);
		t1 = new Treasure(0, 1, dungeon);
		t2 = new Treasure(0, 2, dungeon);
		t3 = new Treasure(0, 3, dungeon);
		t4 = new Treasure(0, 0, dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(t1);
		dungeon.addEntity(t2);
		dungeon.addEntity(t3);
		dungeon.addEntity(t4);
	}
	
	@Test
	void pickupTest() {
		assertEquals(player.getInventory().size(), 0);
		player.moveUp();
		assertEquals(player.getInventory().size(), 1);
		player.moveUp();
		assertEquals(player.getInventory().size(), 2);
		player.moveUp();
		assertEquals(player.getInventory().size(), 3);
		player.moveUp();
		assertEquals(player.getInventory().size(), 4);
	}
	
	@Test
	void pickupTestFail() {
		assertEquals(player.getInventory().size(), 0);
		player.moveUp();
		assertEquals(player.getInventory().size(), 1);
		player.moveDown();
		
		// check that treasure has disappeared from the dungeon ground
		assertEquals(dungeon.getGridEntities(0, 3).size(), 0);
		
		// check that player can't pickup treasure bc it has been picked up before
		player.moveUp();
		assertEquals(player.getInventory().size(), 1);
		
		player.moveUp();
		assertEquals(player.getInventory().size(), 2);
		
	}

}
