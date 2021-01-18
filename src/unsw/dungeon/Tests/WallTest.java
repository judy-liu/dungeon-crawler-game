package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class WallTest {
	
	private Dungeon dungeon;
	private Player player;
	private Wall w1;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 1, 1);
		w1 = new Wall(0, 0, dungeon);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
			
	}
	
	@Test
	void testSolid() {
		assertEquals(w1.isSolid(), true);
	}

}
