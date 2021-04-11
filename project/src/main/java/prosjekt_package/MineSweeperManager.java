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
	
	private MineSweeper game;
	
	public final static String SAVE_FOLDER = "src/ms/saves";
		
	public MineSweeperManager(MineSweeper game) {
		this.game = game;
	}
	
	private static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

	@Override
	public void writeToFile(String filename, MineSweeper game) throws FileNotFoundException { //lagre spill
		
		//lagre dataen slik at den er enkel å laste den opp igjen
		//gå gjennom hver tile, laste ned dataen fra hver tile
		//må lagre om den er åpnet, bombe eller flagget. 
		
	
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			writer.println("MineSweeper");
			writer.println(game.getWidth());
			writer.println(game.getHeight());
			writer.println(game.isGameOver());
			writer.println(game.isGameWon());
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.println(x + "," + y + "," + game.getTile(x, y).getType() + "," + game.getTile(x, y).getIsOpen());	
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
		
		
		
		
		
		/*Scanner scanner = new Scanner(new File(filename));
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();	
		}
		scanner.close();*/
		
		/*MineSweeper mineSweeper= null;
		
		try {
			FileInputStream fileIn = new FileInputStream(filename); //connection to an actual file, throws filenotfound automatisk
			ObjectInputStream in = new ObjectInputStream(fileIn);
			mineSweeper = (MineSweeper) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		catch(ClassNotFoundException c) {
			System.out.println("Minesweeper class not found");
			c.printStackTrace();
			return;
		}*/

	
}