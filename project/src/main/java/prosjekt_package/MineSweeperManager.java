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

import javafx.scene.control.Button;



public class MineSweeperManager implements MineSweeperFile {
	
	//lagre dataen slik at den er enkel å laste den opp igjen
	//gå gjennom hver tile, laste ned dataen fra hver tile
	//må lagre om den er type, om den er åpnet eller flagget. 
	
	private MineSweeper game;
	
	public final static String SAVE_FOLDER = "src/main/java/ms/saves/";
		
	public MineSweeperManager(MineSweeper game) {
		this.game = game;
	}
	
	protected static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

	@Override
	public void writeToFile(String filename, MineSweeper game) throws FileNotFoundException { //lagre spill
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			writer.println(game.getWidth());
			writer.println(game.getHeight());
			writer.println(game.isGameOver());
			writer.println(game.isGameWon());
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					Tile tile = game.getTile(x, y);
					writer.println(x +"," + y + "," + tile.getType() + "," + tile.getIsOpen() + "," + game.getTile(x, y).getIsFlagged());
				//hver tile blir printet ut på egen linje
				//sjekke hva som skjer med isFlagged, første tile blir true...
				}
			}
			
			writer.flush(); //sikrer at du blir ferdig
			writer.close(); //lukker
			
		} 
	}


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
			
			while(scanner.hasNext()) {
				String tile = scanner.next();
				String[] info = tile.split(",");
				
				int x = Integer.parseInt(info[0]); 
				int y = Integer.parseInt(info[1]);
				
				char symbol = tile.charAt(4);
				boolean open = Boolean.parseBoolean(info[3]);
				boolean flagged = Boolean.parseBoolean(info[4]);
				
				game.getTile(x, y).setType(symbol);
				
				if (open == true) {
					game.getTile(x, y).setIsOpen();
				}
				
				if(flagged == true) {
					game.getTile(x, y).setIsFlagged(true);
				}
			}
			game.getNeighbourTiles(); //finner nabobomber
			scanner.close();
			return game;
		}
	}
}