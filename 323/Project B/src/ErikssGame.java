import java.util.ArrayList;

import p323.partA.Board;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.GameState;
import aima.core.util.datastructure.XYLocation;

/**
 * 
 */

/**
 * @author acer
 *
 */
public class ErikssGame extends Game {
	private char player;
	
	/**
	 * @param n
	 * @param name 
	 * 
	 */
	public ErikssGame(int n, String name) {
		// TODO Auto-generated constructor stub
		ArrayList<XYLocation> moves = new ArrayList<XYLocation>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				XYLocation loc = new XYLocation(i, j);
				moves.add(loc);
			}
		}
		
		player = name.charAt(0);
		initialState.put("moves", moves);
		//initialState.put("player", name);
		initialState.put("player", "R"); // assume row always starts first
		initialState.put("utility", new Integer(0));
		initialState.put("board", new Board(n));
		initialState.put("level", new Integer(0));
		presentState = initialState;
		
	}
	
	
	
	@Override
	/**
	 * compute the utility function of this state
	 * use the more specialised compute utility to calculate
	 */
	protected int computeUtility(GameState state) {
		int utility = computeUtility((Board) state.get("board"), this.getLevel(state));
		return utility;
	}

	@Override
	public int getAlphaBetaValue(GameState arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMiniMaxValue(GameState state) {
		if (this.player == 'R') {
			if (getPlayerToMove(state).equalsIgnoreCase("R")) {
				return maxValue(state);
			} else {
				System.out.println("!!!!");
				return minValue(state);
			}
		} else {
			if (getPlayerToMove(state).equalsIgnoreCase("C")) {
				return maxValue(state);
			} else {
				System.out.println("!!!!");
				return minValue(state);
			}
		}
		
	}

	/**
	 * getSuccessorStates
	 * implementing abstract method
	 * generate an arraylist of all possible states after making a move
	 * based on all different moves available in the current state
	 * (this does include equivalent moves)
	 * @param state
	 */
	@Override
	public ArrayList<GameState> getSuccessorStates(GameState state) {
		GameState temp = presentState; // temporary current state
		ArrayList<GameState> retVal = new ArrayList<GameState>(); // all states
		int parentLevel = getLevel(state);
		
		// retrieving all possible moves
		for (int i = 0; i < getMoves(state).size(); i++) {
			XYLocation loc = (XYLocation) getMoves(state).get(i);
			
			// now create the board state that include this location
			GameState aState = makeMove(state, loc);
			aState.put("moveMade", loc); // the move taken
			aState.put("level", new Integer(parentLevel + 1)); // getting deeper
			retVal.add(aState);
		}
		
		presentState = temp;
		return retVal;
	}
	
	/**
	 * overidden abstract method
	 * makeMove should mark a move based on the object given
	 * and return as a gamestate
	 * @param state
	 * @param o
	 * 
	 */
	@Override
	public GameState makeMove(GameState state, Object o) {
		
		XYLocation loc = (XYLocation) o;
		return makeMove(state, loc.getXCoOrdinate(), loc.getYCoOrdinate());
	}
	
	/**
	 * Terminal TEst
	 * when there is a winner
	 */
	@Override
	protected boolean terminalTest(GameState state) {
		Board b = getBoard(state);
		b.DFS();
		return b.WON;
	}
	
	/**
	 * polymorphic makeMove, a more specialize marking with two coordinate
	 * 
	 * @param state
	 * @param xCoOrdinate
	 * @param yCoOrdinate
	 * @return
	 */
	private GameState makeMove(GameState state, int xCoOrdinate, int yCoOrdinate) {
		GameState temp = getMove(state, xCoOrdinate, yCoOrdinate); // the new move made state
		if (temp != null) {
			presentState = temp;
		}
		return presentState;
	}
	
	/**
	 * getMove
	 * the one who will mark the move
	 * @param state
	 * @param xCoOrdinate
	 * @param yCoOrdinate
	 * @return
	 */
	private GameState getMove(GameState state, int xCoOrdinate, int yCoOrdinate) {
		GameState retVal = null;
		XYLocation loc = new XYLocation(xCoOrdinate, yCoOrdinate);
		ArrayList moves = getMoves(state); // this state moves
		ArrayList newMoves = (ArrayList) moves.clone(); // the new state moves after marking the new position

		// just in case the coordinate doesn't match with the moves state
		if (moves.contains(loc)) {
			int index = newMoves.indexOf(loc);
			newMoves.remove(index); // remove this location from further move

			retVal = new GameState(); // create the new state
			retVal.put("moves", newMoves); // fill in with the new moves
			Board newBoard = getBoard(state).cloneBoard(); // the board state should be the same

			// now mark the movement
			if (getPlayerToMove(state) == "R") {
				newBoard.addPiece(yCoOrdinate, xCoOrdinate, 'R');
				retVal.put("player", "C"); // next player to move is the opponent

			} else {
				newBoard.addPiece(yCoOrdinate, xCoOrdinate, 'C');
				retVal.put("player", "R");

			}
			
			retVal.put("board", newBoard);
			retVal.put("utility", new Integer(computeUtility(newBoard, getLevel(state) + 1)));
			retVal.put("level", new Integer(getLevel(state) + 1));
			// presentState = retVal;
		}
		return retVal;
	}
	
	/**
	 * computeUtility
	 * return the value of the winner
	 * @param newBoard
	 * @param playerToMove
	 * @return
	 */
	private int computeUtility(Board newBoard, int level) {
		//newBoard.printBoard();
		if (newBoard.WON) { // just to make sure
			//System.out.print(newBoard.WINNER);
			//System.out.print(" " +playerToMove);
			if (newBoard.WINNER == this.player) {
				return newBoard.getSize()*newBoard.getSize() - level;
				//return 1;
			} else {
				//return -1;
				return -(newBoard.getSize()*newBoard.getSize() - level);
			}
		}
		return 0;
	}

	public Board getBoard(GameState state) {
		return (Board) state.get("board");
	}
	
	
	
	public void printPossibleMoves() {
		//System.out.println("Possible moves");
		
		ArrayList moves = getMoves(presentState);//System.out.println(moves.size());
		for (int i = 0; i < moves.size(); i++) {
			XYLocation moveLoc = (XYLocation) moves.get(i);
			//System.out.println(moveLoc);
			GameState newState = getMove(presentState,
					moveLoc.getXCoOrdinate(), moveLoc.getYCoOrdinate());
			//this.printState(newState);
			Board board = (Board) newState.get("board");
			//board.printBoard();
			//			System.out.println("utility = " + computeUtility(newState));
			//System.out.println("");
		}

	}
	
	public void printState(GameState state) {
		System.out.println("++++++++++");
		System.out.println("print state");
		System.out.print("player is ");
		if (state.get("player") == null) {
			System.out.println("NULL");
		} else {
			System.out.println(getPlayerToMove(state));
		}
		
		
		System.out.print("utility is ");
		if (state.get("utility") == null) {
			System.out.println("NULL");
		} else {
			System.out.println(getUtility(state));
		}
		
		System.out.println("board is ");
		if (state.get("board") == null) { 
			System.out.println("NULL");
		} else {
			((Board) state.get("board")).printBoard();
		}
		System.out.print("level is ");
		if (state.get("level") == null) {
			System.out.println("NULL");
		} else {
			System.out.println(getLevel(state));
		}
			
		
		if (state.get("moves") == null) {
			System.out.println("NULL");
		} else {
			//getMoves(state);
			printPossibleMoves();
		}
		
		
		
		System.out.print("next is ");
		if (state.get("next") == null) {
			System.out.println("NULL");
		} else {
			
		}
		
		
		System.out.print("moveMade is ");
		if (state.get("moveMade") == null) {
			System.out.println("NULL");
		} else {
			
		}
	}
	
	
	public XYLocation minimaxDecision() {//printPossibleMoves();
		getMiniMaxValue(presentState);
		GameState nextState = (GameState) presentState.get("next");
		//printState(presentState);
		makeMove(presentState, nextState.get("moveMade"));
		return (XYLocation) nextState.get("moveMade");
	}
	
	public void opponentDecision(XYLocation loc) {
		makeMove(presentState, loc);//printPossibleMoves();
	}
}
