package prosjekt_package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import prosjekt_package.Tile;

public class TileTest {
	
	//Unntaksh�ndtering:
			//MineSweeperManager:
			//Unntaksh�ndtering n�r man leser og skriver fra fil - sendes til kontroll�ren
		
			//MineSweeper:
			//getRandom() har unntsak hvis talllet ikke er st�rre en 0
			//getTile() har unntak hvis tile er utenfor brett
		
			//Tile
			//setType() har unntak hvis type ikke er gyldig verdi. 
	
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

