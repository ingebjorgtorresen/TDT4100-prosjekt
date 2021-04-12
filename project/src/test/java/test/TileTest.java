package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import prosjekt_package.Tile;

public class TileTest {
	
	private Tile tile;
	
	@BeforeEach
	public void setup() {
		tile = new Tile(0,0, 0);
		//her kan negative verdier teas inn -> ikke bra
	}
	
	@Test
	public void testSetValidType() {
		tile.setType('e');
		assertTrue(tile.isEmpty()); 
		tile.setType('o');
		assertTrue(tile.isBomb());
	}
	
	@Test
	//@DisplayName(value = "")
	public void testSetInvalidType() {
		assertThrows(IllegalArgumentException.class, () -> tile.setType('?'),
				"IllegalArgumentException should be thrown when tile is an invalid value");
	}

}

