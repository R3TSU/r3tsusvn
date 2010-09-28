import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import p323.hex.*;
import p323.partA.Board;

import aima.core.search.adversarial.GameAgent;
import aima.core.util.datastructure.XYLocation;
/**
 * 
 */ 
public class Erikss1 implements Player, Piece {

	private String myPiece;
	private String yourPiece;
	private ErikssGame player;

	@Override
	public int getWinner() {
		
//		//board.printBoard();
//		if (player.hasEnded()) {
//			System.out.println("COL");
//		}
		if (player.hasEnded()) {
			Board board = player.getBoard(player.getState());
			if (board.WINNER == 'C') {
				//System.out.println("COL");
				return COL;
			}
				
			if (board.WINNER == 'R') {
				//System.out.println("ROW");
				return ROW;
			}
		}
		return 0;
	}

	@Override
	public int init(int n, int p) {
		switch (p) {
		case ROW:
			myPiece = "R";
			yourPiece = "C";
			break;
		case COL:
			myPiece = "C";
			yourPiece = "R";
			break;
		}
		player = new ErikssGame(n,myPiece);
		//player.printState(player.getState());
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
		Move m = new Move();
		XYLocation loc = player.minimaxDecision();
		
		m.Row = loc.getXCoOrdinate();
		m.Col = loc.getYCoOrdinate();
		switch (myPiece.charAt(0)) {
		case 'R':
			m.P = ROW;
			break;
		case 'C':
			m.P = COL;
			break;
		}
		
		//System.out.println("mymove: " + m.Col + " "+m.Row);
		//player.printState(player.getState());
		return m;
	}

	@Override
	public int opponentMove(Move m) {
		//System.out.println("yourmove: " + m.Col + " "+m.Row);
		if (player.getBoard(player.getState()).checkEmptyPos(m.Col, m.Row) != 0) {
			return -1;
		}
		
		XYLocation loc = new XYLocation(m.Row, m.Col);
		player.opponentDecision(loc);//player.printState(player.getState());
		return 0;
	}

	@Override
	public void printboard(PrintStream output) {
		output.println("player " + myPiece);
		player.getBoard(player.getState()).printBoard();
	}
}
