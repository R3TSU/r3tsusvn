/**
 * 
 */
package GameBoard;

import hex.Piece;

/**
 * @author acer
 *
 */
public class Board implements Piece {
	private int board[][];
	protected int size;
	
	Board (int size) {
		board = new int[size][size];
		this.size = size;
	}
	
	public void setPiece(int row, int col, int piece) {
		board[row][col] = piece;
	}
	
	public int getPiece(int row, int col) {
		return board[row][col];
	}
	
	public int getSize() {
		return size;
	}
}
