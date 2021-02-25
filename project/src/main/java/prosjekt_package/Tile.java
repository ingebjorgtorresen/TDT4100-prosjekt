package prosjekt_package;

import javafx.scene.text.Text;

public class Tile {
	
	private char type = 'e'; //dette tiles, standard er tom. Vi fyller evt. med tall og bomber senere. 

	private int x;
	private int y;
	
	private boolean isBomb; 
	
	private boolean isOpen = false;
	
	private int neighbours;
	
	private int neighbourBombs;
	
	//private Text number = new Text();
	
	public Tile (int x, int y, int neighbours) {
		this.x = x;
		this.y = y;
		this.type = 'e';
		this.neighbours = neighbours;
		
	}
	
	public void setBomb() {
		type = 'o';
	}
	    
	public boolean isBomb() {
		return type == 'o';
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
    
    public int getNeighbours() {
    	return neighbours;
    }
    public int getNeighbourBombs() {
    	return neighbourBombs;
    }
    
    public void setNeighbourBombs(int bombs) {
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
    
    
    
    
    /*public void setOpen(boolean tileCondition) {
    	this.isOpen = tileCondition;
    }*/
    
    public void openTile() {
    	/*if (isOpen) {
    		return;
    	} */
    	
    	if (isBomb) {
    		System.out.println("Game over");
    		//scene.set --- sluttside
    		return;
    	}
    	
    	isOpen = true;
    	
    }
    
    
     
}
