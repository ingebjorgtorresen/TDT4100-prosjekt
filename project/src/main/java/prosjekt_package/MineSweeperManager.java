package prosjekt_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MineSweeperManager implements MineSweeperFile {

	@Override
	public void writeToFile(String filename, String entry) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			//mangler en linje med kode her
			writer.flush(); //sikrer at du blir ferdig
			writer.close(); //lukker
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
	}

	@Override
	public void readFromFile(String filename) {
		Scanner scanner = new Scanner(new File(filename));
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
		}
		
		scanner.close();
		
	}
	
	

}
