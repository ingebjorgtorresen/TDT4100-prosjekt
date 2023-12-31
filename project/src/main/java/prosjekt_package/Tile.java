package prosjekt_package;

public class Tile {
	
	private char type = 'e'; //dette tiles, standard er tom. Vi fyller evt. med tall og bomber senere. 
	
	private int x;
	private int y;
	
	
	private int neighbours;
	
	private boolean isOpen;
	
	private boolean isFlagged;
	
	private int neighbourBombs;
	
	public Tile (int x, int y, int neighbours) {
		validateNumber(x);
		validateNumber(y);
		validateNumber(neighbours);
		
		this.x = x;
		this.y = y;
		this.type = 'e';
		this.neighbours = neighbours;
		this.isOpen = false;
		this.isFlagged = false;
		
	}
	
	public boolean validateNumber(int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Number can't be negative");
		}
		return true;
	}
	
	public void setPress() {
		type = 't';
	}
	
	public void setBomb() {
		type = 'o';
	}
	    
	public boolean isBomb() {
		return type == 'o';
	}
	
	public boolean isEmpty() {
		return type == 'e'; 
	}
	
	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public char getType(){
    	return type;
    }
    
    public int getNeighbours() { //nabo tiles
    	return neighbours;
    }
    public int getNeighbourBombs() { //naboer med bomber, finnes i minesweeper
    	return neighbourBombs;
    }
    
    public boolean getIsFlagged() {
    	return this.isFlagged;
    }
    
    public boolean getIsOpen() {
    	return isOpen;
    }
    
    public void setIsOpen() {
    	if(this.getIsFlagged()==false) {
    		this.isOpen = true;
    	}
    }
    
    public void setIsFlagged(boolean bool) {
    	if(this.getIsOpen() == false) {
    		this.isFlagged = bool;
    	}
    }
    
    public void setNeighbourBombs(int bombs) {
    	validateNumber(bombs);
    	this.neighbourBombs = bombs;
    }
    
    @Override
    public String toString() {
    	if(type == 'e') {
    		return "" + neighbourBombs;
    	}
    	else if (type == 'o') {
    		return "o";
    	}
    	return "";
    }

	public void setType(char symbol) {
		if("eot".indexOf(symbol) == -1) { //unntak hvis det ikke er gydig tilstand, liste med gylidge tilstand
			throw new IllegalArgumentException("Not a valid state"); 
		}
		type = symbol;
	}
	
	
    
   
}
