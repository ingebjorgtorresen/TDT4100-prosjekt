package prosjekt_package;

import java.util.Random;


public class MineSweeper { 
	
	private int height;
	private int width;
	protected Tile[][] board;

    private boolean isGameWon = false;
    private boolean isGameOver = false;
    
    private int bombNumber;
    
    //Konstrukt�r med brett, som tar inn tomme tiles
    public MineSweeper(int width, int height) {
    	validateNumber(width);
    	validateNumber(height);
    	
		this.height = height;
		this.width = width;
		
		this.board = new Tile[height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int neighbours = 0;
				
				board[y][x] = new Tile(x, y, neighbours);
				this.bombNumber = (height*width)/10; //antall bomber, deler p� 10 pga int/heltallsdivisjon
			}
		}
	}
    
    public boolean validateNumber(int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Number can't be negative");
		}
		return true;
	}
    
    
    //Hjelpemetode som tar inn koordinater til Tile, og sjekker om det er innenfor brettet
    public boolean isTile(int x, int y) {
		return x >= 0 && y >= 0 && x < getWidth() && y < getHeight(); 
	}
    
    //Hente blokk med xy-koordinatene, bruker isTile. 
    public Tile getTile(int x, int y) {
		if (!isTile(x, y)) {
			throw new IllegalArgumentException("Tile outside of board");
		}
		return this.board[y][x];
	}

    private static int getRandom(int limit) {
		if (0 >= limit) {
			throw new IllegalArgumentException("Limit must be greater than 0");
		}
		Random r = new Random();
		return r.nextInt(limit + 1);
    }
    
    public void getNeighbourTiles() { 
    	for(int y = 0; y < getHeight(); y++) {
    		for(int x = 0; x< getWidth(); x++) {
    			int bombs = 0;
    			if (isTile(y - 1, x - 1)) {
    	            if(board[y - 1][x-1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y - 1, x)) {
    	            if(board[y - 1][x].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y - 1, x + 1)) {
    	            if(board[y - 1][x+1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y, x - 1)) {
    	            if(board[y][x-1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y, x + 1)) {
    	            if(board[y][x+1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y + 1, x - 1)) {
    	            if(board[y + 1][x-1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y + 1, x)) {
    	            if(board[y + 1][x].getType() == 'o')
    	            	bombs ++;
    	        }
    	        if (isTile(y + 1, x + 1)) {
    	            if(board[y + 1][x + 1].getType() == 'o')
    	            	bombs ++;
    	        }
    	        board[y][x].setNeighbourBombs(bombs);
    		}
    	}  
    }
    
    public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	

	public Tile[][] getBoard() {
		return board;
	}
	
	public int getBombNumber() {
		return bombNumber;
	}
	
	public boolean isBomb(Tile tile) {
		return tile.getType() == 'o';
	}
	
	public void setBombs() {
		for (int i = bombNumber; i >= 1; i--) {
			int x = getRandom(getWidth() - 1);
			int y = getRandom(getHeight() - 1);
			
			if (board[y][x].getType() == 'o') {
				i++;
			}
			board[y][x].setBomb();
		}
	}
	
	public void setGameOver() {
		isGameOver = true; 
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void setGameWon() {
		isGameWon = true;
	}
	
	public boolean isGameWon() {
		return isGameWon;
	}
	
	public void setIsOpen(int x, int y) {
		this.board[y][x].setIsOpen();
	}
	
	public void openEmptyTiles(Tile tile) { //kan legge til tallene
		tile.setPress();
		int x = tile.getX();
		int y = tile.getY();
		
		tile.setIsOpen();
		
		for(int z = y-1; z <= y+1; z++) { //sjekker over og under
			if (!isTile(x,z)) {
				z++;
			}
			else if (board[z][x].getIsFlagged()==true) { //hvis flagget, ikke �pne
				z++;
			}
			else {
				Tile tile2 = getTile(x,z);
				
				if (tile2.getNeighbourBombs() == 0 && tile2.getType() != 't') { //sjekk heller for type = 'e' enn 0 naboer
					openEmptyTiles(tile2);
				}
				else if(tile2.getType() == 'e') {
					tile2.setIsOpen();
				}	
			}
		}
		for(int w = x-1; w <= x+1; w++) { //sjekker h�yre og venstre
			if (!isTile(y,w)) {
				w++;
			}
			else if (board[y][w].getIsFlagged()==true) {
				w++;
			}
			else {
				Tile tile2 = getTile(w,y);
				if (tile2.getNeighbourBombs() == 0 && tile2.getType() != 't') {
					openEmptyTiles(tile2);
				}
				else if(tile2.getType() == 'e') {
					tile2.setIsOpen();
				}
			}
		}
	}
	
	
	@Override
	public String toString() {
		String boardString = "";
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				boardString += getTile(x, y).toString(); //skriver ut brett :)
				}
			boardString += "\n";
			}	

		if (isGameWon) {
			boardString += "\n\nGame won!";
		} else if (isGameOver) {
			boardString = "\n\nGame over!";
        }
		return boardString;
	}
	
//	public static void main(String[] args) {
//		MineSweeper game = new MineSweeper(10, 10);
//		System.out.println(game);
//		
//		game.setBombs();	
//		game.getNeighbourTiles();
//		System.out.println(game.getBombNumber());
//		System.out.println(game);
//	}

}
