package prosjekt_package;

import java.io.FileNotFoundException;

public interface MineSweeperFile {
	
	public void writeToFile(String filename, MineSweeper game) throws FileNotFoundException;
	public MineSweeper readFromFile(String filename) throws FileNotFoundException;
	

}
