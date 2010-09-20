package p323.hex;

/*
 *  Referee's implementation of a move - piece and (row, column) position
 */

public class Move implements Piece {
	
	public int Row;
	public int Col;
	public int P;   // type of piece in move
	
	public Move()
	{
		Row = -1;
		Col = -1;
		P = EMPTY;
	}
	
}