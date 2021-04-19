package prosjekt_package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import prosjekt_package.Tile;

public class TileTest {
	
	//Unntakshåndtering:
			//MineSweeperManager:
			//Unntakshåndtering når man leser og skriver fra fil - sendes til kontrolleren
		
			//MineSweeper:
			//getRandom() har unntsak hvis talllet ikke er større en 0
			//getTile() har unntak hvis tile er utenfor brett
		
			//Tile
			//setType() har unntak hvis type ikke er gyldig verdi. 
			//har en validateNumber som sjekker om tallet er negativt, funker i konstruktør og sette naboBomber. 
	
	private Tile tile;
	
	@BeforeEach
	public void setup() {
		tile = new Tile(0,0, 0);
		//pass på negative verdier i konstrutøren
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
	
	@Test
	public void testValidCoordinates() {
		
	}

}

