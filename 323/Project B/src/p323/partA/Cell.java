package p323.partA;

// Cell.java
// name: Erik Sugiarto Soeryadji
// login: erikss
// name: Cheong Hsueh-Hsen
// login: hcheong

/**
 * Cell Class
 * describing the hex cell of the game
 */
public class Cell {
	private int x, y;
	private char owner = '-'; // owner of this cells
	
	// Constructor
	public Cell (int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	public void setOwner(char owner) {
		this.owner = owner;
	}
	
	public char getOwner() {
		return owner;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	// print this cell
	public String toString() {
		return owner + " (" + y +"," + x +")";
	}
	
}
