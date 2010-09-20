import java.io.PrintStream;
import java.util.Random;

import p323.hex.*;
import p323.partA.Board;

/**
 * @author erikss
 *	v1 : add, init implementation, add private mypiece, add makemove implementation with default value
 *  v2 : add random, make movement random, also add Board internal state for the player, add board size for player
 *  v3 : initialize board, add move to the board, add print board in board
 *  v4 : get the opponent move into the board
 *  v5 : modify movement if already placed, adding method to the board
 *  v6 : check for winning, change board to have some public
 *   
 */ 
public class Erikss implements Player, Piece {
	private int myPiece; // My piece
	private int size;
	private Board state;
	
	@Override
	public int getWinner() {
		// TODO Auto-generated method stub
		state.DFS();
		if (state.WON) {
			if (state.WINNER == 'C') return COL;
			if (state.WINNER == 'R') return ROW;
		}
		return 0;
	}

	@Override
	public int init(int n, int p) {
		// TODO Auto-generated method stub
		size = n;
		myPiece = p;
		state = new Board(n);
		return 0;
	}

	@Override
	public Move makeMove() {
		// TODO Auto-generated method stub
		Random randomMove = new Random();
		Move m = new Move();
//		m.Row = -1;
		m.Row = randomMove.nextInt(size);
//		m.Col = -1;
		m.Col = randomMove.nextInt(size);
		m.P = myPiece;
		while (state.checkEmptyPos(m.Row, m.Col) < 0){
			// find another place
			m.Row = randomMove.nextInt(size);
			m.Col = randomMove.nextInt(size);
		}
		
		char piece = '-';
		switch (myPiece) {
		case ROW:
			piece = 'R';
			break;
		case COL:
			piece = 'C';
			break;
		default:
			piece = '-';
			break;
		}
		state.addPiece(m.Row, m.Col, piece);
		return m;
	}

	@Override
	public int opponentMove(Move m) {
		// TODO Auto-generated method stub
		char piece = '-';
		switch (m.P) {
		case ROW:
			piece = 'R';
			break;
		case COL:
			piece = 'C';
			break;
		default:
			piece = '-';
			break;
		}
		// havent got illegal move
		state.addPiece(m.Row, m.Col, piece);
		return 0;
	}

	@Override
	public void printboard(PrintStream output) {
		// TODO Auto-generated method stub
		output.println("player " + myPiece);
		state.printBoard();
	}

}
