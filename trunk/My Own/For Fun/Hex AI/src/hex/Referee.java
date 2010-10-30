
package hex;

import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/*   
 *   Referee:
 *      A mediator between two players. It is responsible for initializing 
 *      the players, passing the moves made by each player to their opponent,
 *      and terminating the game in response to one of the players detecting
 *      a win or an illegal move. 
 *      
 *      It is the responsibility of the players to check whether they have won and
 *      maintain the board state.
 *
 *   @author masudm
 */

public class Referee implements Piece {

	private static Player P1;
	private static Player P2;
	
	/*
	 * Input arguments: board size, path of player1 and path of player2
	 */
	public static void main(String[] args)
	{
		
		Move P1Move = new Move();
		Move P2Move = new Move();
		System.out.println("Referee started !");
		
		/* load players */
		try{
			P1 = (Player)(Class.forName(args[1]).newInstance());
			P2 = (Player)(Class.forName(args[2]).newInstance());
		}
		catch(Exception e){
			System.out.println("Error "+ e.getMessage());
			System.exit(1);
		}
		
		/* initialise players with size of baord */
		P1.init((Integer.valueOf(args[0])).intValue(), ROW);
		P2.init((Integer.valueOf(args[0])).intValue(), COL);
		
		/* while there is no winner */
		while(P1.getWinner()==EMPTY && P2.getWinner()==EMPTY)
		{
			P1Move = P1.makeMove();
			P1.printBoard(System.out);
			if(P2.opponentMove(P1Move)<0)
			{
				System.out.println("Exception: Player 2 rejected the move of player 1.");
				P2.printBoard(System.out);
				System.exit(1);
			}
			if(P1.getWinner()==EMPTY && P2.getWinner()==EMPTY)
			{
				P2Move = P2.makeMove();
				P2.printBoard(System.out);
				if(P1.opponentMove(P2Move)<0)
				{
					System.out.println("Exception: Player 1 rejected the move of player 2.");
					P1.printBoard(System.out);
					System.exit(1);
				}
			
			}
		}
		
		System.out.println("Player one (Row) indicate winner as: "+ P1.getWinner());
		System.out.println("Player two (Col) indicate winner as: "+ P2.getWinner());
		System.out.println("Referee Finished !");
	}
	
}
