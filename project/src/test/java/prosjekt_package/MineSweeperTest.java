package prosjekt_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prosjekt_package.MineSweeper;

class MineSweeperTest {
	
	private MineSweeper emptyGame;
	private MineSweeper game;

	@BeforeEach
	public void setup() {
		emptyGame = new MineSweeper(0,0);
		game = new MineSweeper(10, 10);
	}
	
	@Test
	public void testConstructor() {
		//tester ugylidge verdier
		//hvis vi ikke hadde hatt validering i konstruktøren ville vi fått ""NegativeArraySizeException"
		assertThrows(IllegalArgumentException.class, () -> {new MineSweeper(-3, 5 );}); 
		assertThrows(IllegalArgumentException.class, () -> {new MineSweeper(5, -8 );}); 
		assertThrows(IllegalArgumentException.class, () -> {new MineSweeper(-7, -5);});
		
		//tester set-up
		assertEquals(emptyGame.getHeight(), 0, "Expected height to be 0");
		assertEquals(emptyGame.getWidth(), 0, "Expected width to be 0");
		
		//tester et vanlig game
		assertEquals(game.getHeight(), 10, "Expected height to be 10");
		assertEquals(game.getWidth(), 10, "Expected width to be 10");
		
		//sjekker at alle tilene er tomme
		for(int y = 0; y< game.getHeight(); y++) {
			for(int x =0; x< game.getWidth(); x++) {
				assertEquals(game.getTile(x,y).getType(), 'e'); //sjekker at alle er tomme i starten
			}
		}
	}
	
	@Test
	public void testGameOver() {
		//her kan man vel egt teste litt mer 
		emptyGame.setGameOver();
		assertTrue(emptyGame.isGameOver());
		
	}
	
	@Test
	public void testGameWon() {
		emptyGame.setGameWon();
		assertTrue(emptyGame.isGameWon());
		
	}
	
	@Test
	public void testGetNeighbourTiles() {
		MineSweeper nGame = new MineSweeper(10,10);
		
		nGame.board[1][1].setBomb();
		nGame.board[1][2].setBomb();
		nGame.board[1][9].setBomb();
		nGame.board[2][2].setBomb();
		nGame.board[3][2].setBomb();
		nGame.board[4][5].setBomb();
		nGame.board[4][1].setBomb();
		nGame.board[6][7].setBomb();
		nGame.board[8][5].setBomb();
		nGame.board[9][9].setBomb();
		
		nGame.getNeighbourTiles();
		
		assertEquals(nGame.board[0][1].getNeighbourBombs(), 2, 
				"Expected 2 but was " + nGame.board[0][1].getNeighbourBombs());
		assertEquals(nGame.board[0][9].getNeighbourBombs(), 1, 
				"Expected 1 but was " + nGame.board[0][9].getNeighbourBombs());
		assertEquals(nGame.board[2][1].getNeighbourBombs(), 4, 
				"Expected 4 but was " + nGame.board[2][1].getNeighbourBombs());
		assertEquals(nGame.board[2][3].getNeighbourBombs(), 3, 
				"Expected 3 but was " + nGame.board[2][3].getNeighbourBombs());
		assertEquals(nGame.board[7][0].getNeighbourBombs(), 0, 
				"Expected 0 but was " + nGame.board[7][0].getNeighbourBombs());
		
		//tile[0][1] -> 2 bombe naboer
		//tile[0][9] -> 1 nabo bombe
		//tile[2][1] -> 4 nabo bombe
		//tile[2][3] -> 3 nabo bombe
		//tile[1][5] -> 0 naboer (eller tile [7][0])	
	}
	
	@Test
	public void testIsTile() {
		assertTrue(game.isTile(9,9), "Should return true, tile inside board");
		
		assertFalse(game.isTile(11,11), "Should return false, tile outside board");
	}
	
	@Test
	public void testGetTile() {
		//sjekker at den kaster unntak hvis man prøver å hente en tile utenfor brettet
		assertThrows(IllegalArgumentException.class, () -> game.getTile(11, 11), 
				"Should throw IllegalArgumentExcption, but nothing was thrown");
	}
	
	@Test
	public void testSetIsOpen() {
		//sjekker lukket først
		assertFalse(game.board[5][5].getIsOpen(), "This tile should not be open");
		
		//sjekker om åpnet
		game.board[5][5].setIsOpen();
		assertTrue(game.board[5][5].getIsOpen(), "This tile should be open");
	}
	
	@Test
	public void testOpenEmptyTiles(){
		MineSweeper oGame = new MineSweeper(4,4);
		oGame.board[0][1].setBomb();
		oGame.board[0][2].setBomb();
		oGame.board[1][3].setBomb();
		oGame.board[3][3].setBomb();
		
		oGame.getNeighbourTiles();
		
		oGame.openEmptyTiles(oGame.board[3][0]);
		
		assertTrue(oGame.board[3][1].getIsOpen(), "Tile should be open");
		assertTrue(oGame.board[3][2].getIsOpen(), "Tile should be open");
		
		assertTrue(oGame.board[2][0].getIsOpen(), "Tile should be open");
		assertTrue(oGame.board[2][1].getIsOpen(), "Tile should be open");
		assertTrue(oGame.board[2][2].getIsOpen(), "Tile should be open");
		
		assertTrue(oGame.board[1][0].getIsOpen(), "Tile should be open");
		assertTrue(oGame.board[1][1].getIsOpen(), "Tile should be open");
		
		assertFalse(oGame.board[0][0].getIsOpen(), "Tile should not be open");
		assertFalse(oGame.board[3][3].getIsOpen(), "Tile should not be open"); //dette er en bombe
		assertFalse(oGame.board[1][2].getIsOpen(), "Tile should not be open"); //diagonal skal ikke åpnes
		
		
		
	}
	
	@Test
	public void testSetBombs() {
		
		game.setBombs();
		int foundBombs = 0;
		
		for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				 
				if(game.getTile(x, y).getType() == 'o')
					foundBombs ++;
			}
		}
		//Sjekker at antall bomber satt er lik bomber man skal ha
		assertEquals(game.getBombNumber(), foundBombs, "Expected " + game.getBombNumber() + " but was " + foundBombs);
	}

}
