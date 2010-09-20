package p323.partA;

// name: Erik Sugiarto Soeryadji
// login: erikss
// name: Cheong Hsueh-Hsen
// login: hcheong

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String line = null;
		BufferedReader buffer = null;
		int n = 0;
		Board hexBoard = null;
		
		// reading from standard input, try read the first line
		// if no file given, should print the exception and exit 
		try {
			buffer = new BufferedReader(new InputStreamReader(System.in));
			line = buffer.readLine();
		} catch (IOException e) {
			System.err.println("Error:" + e.getMessage());
			System.exit(-1);
		}
		
		// get the board size
		try {
			n = Integer.parseInt(line);
		}
		catch (NumberFormatException e) {
			System.err.println("First line must contain an integer.");
			System.exit(-1);
		}
		
		// System.out.println(n);
		hexBoard = new Board(n);
		
		
		// read the whole board entry
		int row = 0;
		try {
			while ((line = buffer.readLine()) != null) {
				// System.out.println(line);
				
				// if it keeps reading input when the number of row bigger
				// than the board size, print error
				if (row >= n) {
					System.err.println("error: reading more than the board size");
					System.exit(-1);
				}
				
				// tokenize the line input
				StringTokenizer st = new StringTokenizer(line);
				
				// if row size (number of col) doesn't match
				if (st.countTokens() < n) {
					System.out.println("row " + row + " size doesn't match");
					System.exit(-1);
				}
				
				int col = 0;
				while (st.hasMoreElements()) {
					String token = st.nextElement().toString();
					// System.out.println(token.charAt(0));
					hexBoard.addPiece(row, col, token.charAt(0));
					col++;
				}
				row++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// finish reading, but the row number is not the same with the board size
		if (row != n) {
			System.err.println("error: reading less than the board size");
			System.exit(-1);
		}
		
		// finish reading input
		// then the main part, check if someone is winning
		hexBoard.checkForWinning();
	}
}