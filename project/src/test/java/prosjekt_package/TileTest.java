package prosjekt_package;

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
		//pass på negative verdier i konstrutøren
	}
	
	@Test
	public void testConstructor() { 
		//try-catch kan også brukes
		
		//tester ulovlige verdier
		assertThrows(IllegalArgumentException.class, () -> {new Tile(-3, 5, 0);}); 
		assertThrows(IllegalArgumentException.class, () -> {new Tile(5, -8, 0);}); 
		assertThrows(IllegalArgumentException.class, () -> {new Tile(4, 4, -10);}); 
		assertThrows(IllegalArgumentException.class, () -> {new Tile(-7, -5, -8);});
		
		//tester et "tomt" tile-objekt
		assertEquals(0, tile.getX(), "Expcected x to be zero");
		assertEquals(0, tile.getY(), "Expcected y to be zero");
		assertEquals(0, tile.getNeighbours(), "Expected neighbours to be zero");
		
		//tester et tile-objekt med koordinater og naboer
		Tile testTile = new Tile(10, 10, 2);
		assertEquals(10, testTile.getX(), "Expcected x to be 10");
		assertEquals(10, testTile.getY(), "Expected y to be 10");
		assertEquals(2, testTile.getNeighbours(), "Expected neighbours to be zero");
		
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
	
	@Test
	public void testSetValidNeighbours() {
		tile.setNeighbourBombs(5);
		assertEquals(5, tile.getNeighbourBombs(), "Should be 5");
	
		assertThrows(IllegalArgumentException.class, () -> {tile.setNeighbourBombs(-5);}); //sjekker om den kaster unntak
	}
	
	//teste boolean isFlagged - isOpened
	@Test
	public void testSetIsFlagged () {
		tile.setIsFlagged(true);
		assertTrue(tile.getIsFlagged());
		
		tile.setIsFlagged(false);
		assertFalse(tile.getIsFlagged());
		
		tile.setIsOpen();
		assertFalse(tile.getIsFlagged());
	}
	
	@Test
	public void testSetIsOpened() {
		//først skal den være lukket
		assertFalse(tile.getIsOpen());
		
		tile.setIsOpen();
		assertTrue(tile.getIsOpen());
		
	}

}

