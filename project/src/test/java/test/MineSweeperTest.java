package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prosjekt_package.MineSweeper;

class MineSweeperTest {

	@BeforeEach
	public void setup() {
		createBoard();
	}
	
	@Test
	public void testCobstructor() {
		game = new MineSweeper(9, 9);
		
		assertEquals(game.getHeight(), 9);
		assertEquals(game.getWidth(), 9);
		
		for(int y = 0; y< game.getHeight(); y++) {
			for(int x =0; x< game.getWidth(); x++) {
				assertEquals(game.getTile(x,y).getType(), 'e'); //sjekker at alle er tomme i starten
			}
		}
	}

}
