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

	/**
	 * @param n
	 * @param name 
	 * 
	 */
	public ErikssGame(int n, String name) {
		// TODO Auto-generated constructor stub
		ArrayList<XYLocation> moves = new ArrayList<XYLocation>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				XYLocation loc = new XYLocation(i, j);
				moves.add(loc);
			}
		}
		initialState.put("moves", moves);
		initialState.put("player", name);
		initialState.put("utility", new Integer(0));
		initialState.put("board", new Board(n));
		initialState.put("level", new Integer(0));
		presentState = initialState;
		
	}

	@Override
	protected int computeUtility(GameState arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAlphaBetaValue(GameState arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMiniMaxValue(GameState state) {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public GameState makeMove(GameState state, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean terminalTest(GameState arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
