import java.io.PrintStream;

import p323.hex.*;
import p323.partA.Board;

import aima.core.search.adversarial.GameAgent;
/**
 * 
 */ 
public class Erikss1 implements Player, Piece {
	GameAgent player = new GameAgent(new ErikssGame());
	Board board;
	String myPiece;

	// implements Player
	@Override
	public int getWinner() {
		// TODO Auto-generated method stub
		board.DFS();
		if (board.WON) {
			if (board.WINNER == 'C') return COL;
			if (board.WINNER == 'R') return ROW;
		}
		return 0;
	}

	@Override
	public int init(int n, int p) {
		switch (p) {
		case ROW:
			myPiece = "R";
			break;
		case COL:
			myPiece = "C";
			break;
		}
		
		board = new Board(n);
		return 0;
	}

	@Override
	public Move makeMove() {
		Move m = new Move();

		return m;
	}

	@Override
	public int opponentMove(Move m) {
		board.addPiece(m.Row, m.Col, myPiece.charAt(0));
		return 0;
	}

	@Override
	public void printboard(PrintStream output) {
		output.println("player " + myPiece);
		board.printBoard();
	}


}
