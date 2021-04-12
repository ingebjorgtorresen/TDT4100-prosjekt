package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prosjekt_package.MineSweeper;
import prosjekt_package.MineSweeperManager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Arrays;

public class MineSweeperManagerTest {
	
	private MineSweeper game;
	private MineSweeperManager manager = new MineSweeperManager(game);
	
	@BeforeEach
	public void setup() {
		createBoard();
	}
	
	@Test
	public void testLoad() {
		MineSweeper savedGame = game; //required to ignore Eclipse warning
		try {
			savedGame = manager.readFromFile("test-saved_file");
		} catch (FileNotFoundException e ) {
			fail("Coul not load saved file");
			return:
		}
		assertEquals(game.toString(), savedGame.toString())
		assertFalse(game.isGameOver()); //må teste om gave er over eller kan spilles videre/game won
	}
	 @Test
	 public void testLoadNonExistingFile() {
		 assertThrows(
				 FileNotFoundException.class, ()-> game = manager.readFromFile("moo")
				 , "File not found - should throw FileNotFoundExeption"
				 );
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
			 testFile = Files.readAllBytes(Path.of(MineSweeperManager.getFilePath("test-saved_file")));
		 } catch(IOException e ) {
			 fail("Could not load test file");
		 }
		 
		 try {
			 newFile = Files.readAllBytes(Path.of(MineSweeperManager.getFilePath("test-save-new")));
		 } catch(IOException e ) {
			 fail("Could not load test file");
		 }
		 
		 assertNotNull(testFile);
		 assortNotNull(newFile);
		 assertTrue(Arrays.equals(testFile, newFile));
	 }
	 
	 @AfterAll //rydde opp etter seg
	 static void teardown() {
		 File newTestSaveFile = new File(MineSweeperManager.getFilePath("test-save-new"));
		 newTestSaveFile.delete();		 
	 }
}
