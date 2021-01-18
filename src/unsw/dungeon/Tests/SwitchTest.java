package unsw.dungeon.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class SwitchTest {

	private Dungeon dungeon;
	private Player player;
	private Switch s1;
	private Switch s2;
	private Switch s3;
	private Switch s4;
	private Boulder b1;
	private Boulder b2;
	private Boulder b3;
	private Boulder b4;
	
	@BeforeEach
	public void setUp() {
		dungeon = new Dungeon(4, 4);
		player = new Player(dungeon, 0, 3);
		s1 = new Switch(dungeon, 0, 1);
		s2 = new Switch(dungeon, 1, 1);
		s3 = new Switch(dungeon, 2, 1);
		s4 = new Switch(dungeon, 3, 1);
		b1 = new Boulder(dungeon, 0, 2);
		b2 = new Boulder(dungeon, 1, 2);
		b3 = new Boulder(dungeon, 2, 2);
		b4 = new Boulder(dungeon, 3, 2);
		dungeon.setPlayer(player);
		dungeon.addEntity(player);
		dungeon.addEntity(s1);
		dungeon.addEntity(s2);
		dungeon.addEntity(s3);
		dungeon.addEntity(s4);
		dungeon.addEntity(b1);
		dungeon.addEntity(b2);
		dungeon.addEntity(b3);
		dungeon.addEntity(b4);
	}
	
	// check that switch is triggered
	@Test
	void testTriggerSwitchOn() {
		assertEquals(s1.isTriggered(), false);
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp();
		
		assertEquals(s1.isTriggered(), true); // check that s1 is triggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
	}
	
	// check that switch is untriggered
	@Test
	void testTriggerSwitchOff() {
		assertEquals(s1.isTriggered(), false);
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp();
		
		assertEquals(s1.isTriggered(), true); // check that s1 is triggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp();
		
		assertEquals(s1.isTriggered(), false); // check that s1 is untriggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
	
	}

	// check that multiple switches can be triggered on and off
	@Test
	void testTriggerMultipleSwitches() {
		assertEquals(s1.isTriggered(), false);
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp();
		
		assertEquals(s1.isTriggered(), true); // check that s1 is triggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp();
		
		assertEquals(s1.isTriggered(), false); // check that s1 is untriggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveDown();
		player.moveDown(); // 03
		player.moveRight(); // 13
		player.moveUp(); // 12 trigger s2
		
		assertEquals(s1.isTriggered(), false); // check that s1 is untriggered
		assertEquals(s2.isTriggered(), true);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
		player.moveUp(); // 11 untrigger s2
		
		assertEquals(s1.isTriggered(), false); // check that s1 is untriggered
		assertEquals(s2.isTriggered(), false);
		assertEquals(s3.isTriggered(), false);
		assertEquals(s4.isTriggered(), false);
		
	}
}
