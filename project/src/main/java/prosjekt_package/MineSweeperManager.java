package prosjekt_package;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;



public class MineSweeperManager implements MineSweeperFile {
	
	//lagre dataen slik at den er enkel � laste den opp igjen
	//g� gjennom hver tile, laste ned dataen fra hver tile
	//m� lagre om den er �pnet, bombe eller flagget. 
	
	private MineSweeper game;
	
	public final static String SAVE_FOLDER = "src/main/java/ms/saves/";
		
	public MineSweeperManager(MineSweeper game) {
		this.game = game;
	}
	
	private static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

	@Override
	public void writeToFile(String filename, MineSweeper game) throws FileNotFoundException { //lagre spill
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			writer.println("MineSweeper");
			writer.println(game.getWidth());
			writer.println(game.getHeight());
			writer.println(game.isGameOver());
			writer.println(game.isGameWon());
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.println(x + "," + y + "," + game.getTile(x, y).getType() + "," + game.getTile(x, y).getIsOpen());	
				//hver tile blir printet ut p� egen linje
				}
			}
			writer.flush(); //sikrer at du blir ferdig
			writer.close(); //lukker
			
		} 
	}
	
	
	/*catch(FileNotFoundException e) {
		System.out.println("File not found");
		e.printStackTrace();
	}*/


	@Override
	public MineSweeper readFromFile(String filename) throws FileNotFoundException { //hente spill
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
			int width = scanner.nextInt();
			int height = scanner.nextInt();
			
			MineSweeper game = new MineSweeper(width, height);

			if (scanner.nextBoolean()) {
				game.setGameOver();
			}

			if (scanner.nextBoolean()) {
				game.setGameWon();
			}

			scanner.nextLine();

			String board = scanner.next();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					char symbol = board.charAt(y * width + x);
					game.getTile(x, y).setType(symbol);
				}
			}
			return game;
		}
	}
	
//	public static void main(String[] args) throws FileNotFoundException{
//		new MineSweeperManager().writeToFile("test");
//	}
}