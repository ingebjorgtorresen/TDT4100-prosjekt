package prosjekt_package;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prosjekt_package.MineSweeper;
import prosjekt_package.MineSweeperManager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MineSweeperManagerTest {
	
	private MineSweeper game;
	private MineSweeperManager manager = new MineSweeperManager(game);
	
	@BeforeEach
	public void setup() {
		game = new MineSweeper(4, 4);
		
		game.board[0][2].setBomb();
		game.board[1][1].setBomb();
		game.board[2][0].setBomb();
		game.board[3][0].setBomb();
		
		game.getNeighbourTiles();
	}
	
	@Test
	public void testLoad() {
		MineSweeper savedGame; 
		
		try {
			savedGame = manager.readFromFile("test_save_file");
		} catch (FileNotFoundException e ) {
			fail("Could not load saved file");
			return;
		}
		
		assertEquals(game.toString(), savedGame.toString()); //
		assertFalse(game.isGameOver());
	}
	 @Test
	 public void testLoadNonExistingFile() {
		 assertThrows(FileNotFoundException.class, ()-> game = manager.readFromFile("moo"),
				 "File not found. Should throw FileNotFoundExeption");
	 }
	 
	 
	 @Test
	 public void testLoadInvalidFile() {
		 assertThrows(Exception.class, () -> game = manager.readFromFile("invalid_save"),
				 "Should throw exception if file is invalid!"); //filen kan ikke leses
	 }
	 
	 @Test
	 public void testSave() {
		 try {
			 manager.writeToFile("test-save-new", game);
		 } catch (FileNotFoundException e) {
			 fail("Could not save file");
		 }
		 
		 byte[] testFile = null, newFile = null;
		 
		 try {
			 testFile = Files.readAllBytes(Path.of(MineSweeperManager.getFilePath("test_save_file"))); //denne ligger i saves
		 } catch(IOException e ) {
			 fail("Could not load test file");
		 }
		 
		 try {
			 newFile = Files.readAllBytes(Path.of(MineSweeperManager.getFilePath("test-save-new")));
		 } catch(IOException e ) {
			 fail("Could not load file");
		 }
		 
		 assertNotNull(testFile);
		 assertNotNull(newFile);
		 assertTrue(Arrays.equals(testFile, newFile)); //sjekker at alt er blitt lagret riktig
	 }
	 
	 @AfterAll //rydde opp etter seg
	 static void teardown() {
		 File newTestSaveFile = new File(MineSweeperManager.getFilePath("test-save-new"));
		 newTestSaveFile.delete();		 
	 }
}
