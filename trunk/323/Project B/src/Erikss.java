import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

import p323.hex.*;
import p323.partA.Board;

public class Erikss implements Player, Piece {
	private char piece; // My piece
	private char oPiece;
	private int size;
	private Board state;
	
	@Override
	public int getWinner() {
		state.DFS();
		if (state.WON) {
			if (state.WINNER == 'C') return COL;
			if (state.WINNER == 'R') return ROW;
		}
		return 0;
	}

	@Override
	public int init(int n, int p) {
		size = n;
		switch (p) {
		case ROW:
			piece = 'R';
			oPiece = 'C';
			break;
		case COL:
			piece = 'C';
			oPiece = 'R';
			break;
		}
		state = new Board(n);
		return 0;
	}

	@Override
	public Move makeMove() {
//		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			buffer.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Random randomMove = new Random();
		Move m = new Move();
		m.Row = randomMove.nextInt(size);
		m.Col = randomMove.nextInt(size);
		while (state.checkEmptyPos(m.Col, m.Row) != 0){
			// find another place
			m.Row = randomMove.nextInt(size);
			m.Col = randomMove.nextInt(size);
		}
		
		//System.out.println("mymove: " + m.Col + " "+m.Row);
		state.addPiece(m.Col, m.Row, piece);
		return m;
	}

	@Override
	public int opponentMove(Move m) {
		//System.out.println("yourmove: " + m.Col + " "+m.Row);
		
		if (state.checkEmptyPos(m.Col, m.Row) != 0) {
			return -1;
		}
		
		state.addPiece(m.Col, m.Row, oPiece);
		return 0;
	}

	@Override
	public void printboard(PrintStream output) {
		output.println("player " + piece);
		state.printBoard();
	}
	
}