package p323.partA;

// name: Erik Sugiarto Soeryadji
// login: erikss
// name: Cheong Hsueh-Hsen
// login: hcheong
/**
 * Board Class for Hex
 * similar representation as a matrix that each node is connected to
 * its horizontal and vertical neighbour
 * except that it has additional edges to accomodate the hex.
 * Also, besides having a board position in form of coordinate x and y
 * (explained as cell) it has also internal representation for graph purpose
 * in board to contain the path.
 * this representation is named by counting from 0 to number of cells
 * counting to the left from top-left (0,0) as 0 to bottom-right (n-1,n-1) 
 * as square(n-1)
 */
public class Board {
	private boolean board[][]; // contain the hex path
	private Cell pieces[][]; //who owns the hex piece
	private int size; // size of board or n
	public int getSize() {
		return size;
	}


	private int NUMBER_OF_NODES; // number of nodes or hex pieces
	private boolean visited[][]; // notes if the board is visited or not
	
	public boolean WON = false; // someone wins this game
	public char WINNER;
	private Cell winningPath[]; // the winning path
	private int counter = 0; // number of node on winning path
	
	/**
	 * Constructor method. Also creates all the relevant edges for a hex board.
	 * @param size
	 */
	public Board(int size) {
		this.size = size;
		this.NUMBER_OF_NODES = size*size;
		// board of n x n would have n (possible) squared edges
		board = new boolean[NUMBER_OF_NODES][NUMBER_OF_NODES];
		pieces = new Cell[size][size];
		visited = new boolean[size][size];
		winningPath = new Cell[NUMBER_OF_NODES];
		
		// initialize for edges, at start assume each hex has no edges to others
		for(int h = 0; h < this.NUMBER_OF_NODES; h++){ // every horizontal
			for(int v = h; v < this.NUMBER_OF_NODES; v++){ // every vertical
				board[h][v] = false;
				board[v][h] = false;
			}
		}
		resetVisitedGraph();
		
		Cell c; // newly created cell
//		int position;
		
		// now create the pieces
		for(int x = 0; x < size; x++){ 	// actual horizontal board position
			for(int y = 0; y < size; y++){  // actual vertical board position
				c = new Cell(y, x);
				pieces[y][x] = c;
			}
		}
		
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				// System.out.println(y +"  " + x);
				// as long as there is another piece in x direction / next column
				if (x < size-1) {
					// System.out.println("add horizon");
					addEdge(pieces[y][x], pieces[y][x+1]); // horizontal edges
					if (y > 0) {
						// System.out.println("add diagonal");
						addEdge(pieces[y][x], pieces[y-1][x+1]); // diagonal
					}
				}
				// as long as there is another piece in y direction / next row
				if (y < size-1) {
					// System.out.println("add verti");
					addEdge(pieces[y][x], pieces[y+1][x]); // vertical edges
					//System.out.println("");
				}
				
			}
		}
	}
	
	/**
	 * Method to create or connect two cells. kind of similar with creating 
	 * an edge in a graph
	 * @param c1
	 * @param c2
	 */
	private void addEdge(Cell c1, Cell c2) {
		// System.out.println(c1.toString() + " to " + c2.toString());
		int p1, p2;
		p1 = toPosition(c1);
		p2 = toPosition(c2);
		board[p1][p2] = true;
		board[p2][p1] = true;
	}
	public int checkEmptyPos(int y, int x) {
		if (this.pieces[y][x].getOwner() == 'R' || this.pieces[y][x].getOwner() == 'C') {
			// already got player
			return 1;
		}
		return 0;
	}
	
	
	/**
	 * addPiece add the player into corresponding position
	 * @param x x position
	 * @param y y position
	 * @param piece player code
	 */
	public void addPiece(int y, int x, char piece) {
		// only add when the input is one of C, R or -
	
		if(piece == 'R' || piece == 'C' || piece == '-'){
			pieces[y][x].setOwner(piece);
		} else {
			System.err.print("error: Invalid piece, " + piece);
			System.err.println(" at position " + y + ", " + x);
			System.exit(1);
		}
	}
	
	/**
	 * Method to get the coordinate (cell) from a board position.
	 */
	private Cell toCoordinate(int position){
		Cell coordinate = this.pieces[position/size][position%size];
		return coordinate;
	}
	
	/**
	 * Method to get the board position from a given coordinate (cell).
	 */
	private int toPosition(Cell c){
		int position = c.getY()*size + c.getX();
		return position;
	}
	
	/**
	 * our own Depth First Search, for hex
	 * it will check the Row player first, by looking if there is a 
	 * connected path from left to right.
	 * Then do the same for Col player, but looking from top to bottom.
	 * There is no need to find the other direction, since for a winning
	 * condition, both players need to have at least one piece on each side
	 */
	public void DFS() {
		Cell c;
		// check for winning for Row player
		// check only the left column of the board
		for (int y = 0; y < size; y++) {
			// c = toCoordinate(h); // get the coordinate of the position
			c = pieces[y][0];
			// if there's a Row piece, and if the board position 
			// is not visited..
			if (c.getOwner() == 'R') {
				// start explore to see if the other side of the board 
				// is reachable
				// System.out.println(c.toString());
				explore(c, 'R');
				if(WON){
					break;
				} else {
					
				}
				resetVisitedGraph();
			}
			
			// else (This position belong to Col or nothing in here),
			// check another row
		}
		
		// if Row wins, then no need to find for Col
		if (WON) {
			return;
		}
		
		// now, check for winning for Col player
		// check only the top row of the board
		
		for (int x = 0; x < size; x++){				
			// c = toCoordinate(v); // get the coordinate of the position
			c = pieces[0][x];
			// if there's a Col piece, and if the board position 
			// is not visited..
			if (c.getOwner() == 'C') {
				// System.out.println(c.toString());
				explore(c, 'C');
				if(WON){
					break;
				} else {
				}
				resetVisitedGraph();
			}
		}
	}
	
	/**
	 * explore the node
	 */
	private void explore(Cell c, char piece) {
		// start with marking this cell as wisited
		visited[c.getY()][c.getX()] = true;
		// also a possibility on winning path
		winningPath[counter++] = c;
		
		Cell next;
		// now check for the whole nodes for possible path
		for (int x = 0; x < NUMBER_OF_NODES; x++) {
			next = toCoordinate(x);
			// System.out.println(toPosition(c) + "  " + x);
			// if there is a path, and an unvisited one, and the same player, 
			// then this is the correct one
			if (!WON && board[toPosition(c)][x] &&
				!visited[next.getY()][next.getX()] &&
				next.getOwner() == piece) {
				// System.out.println("next:" + next.toString());
				// if we will reach the right side of the board and it's an 'R'
				if(next.getX() == size-1 && piece == 'R'){
					WINNER = 'R';
					WON = true;
					winningPath[counter++] = next;
					break;
				} else if (next.getY() == size-1 && piece == 'C'){
				// if we will reach the bottom row of the board and it's an 'C'
					WINNER = 'C';
					WON = true;
					winningPath[counter++] = next;
					break;
				} else {
				// else, still somewhere in between, so keep explore
					explore(next, piece);
				}
			}
			// else, which means no player's next moves, or already 
			// visit every possible edges
			// then finish exploring
		}
	}		
	/**
	 * Reset the visited node path, for a new exploring
	 * also reseting the winning path
	 */
	private void resetVisitedGraph(){
		for(int x = 0; x < this.size; x++){
			for(int y = 0; y < this.size; y++){
				visited[y][x] = false;
			}
		}
		
		for (int i=0 ; i < counter; i++) {
			winningPath[i] = null;
		}
		counter = 0;
	}
	
	/**
	 * Main method, to check for winning
	 * it will run the DFS and see if there is a winner
	 * if yes, then print the winner and the winning path
	 * else, print nothing
	 */
	public void checkForWinning() {
		//checkBoard();
		DFS();
		if(WON){
			Cell c;
			System.out.println(WINNER);
			for (int i=0; i < counter; i++) {
				c = winningPath[i];
				System.out.println(c.getY() + " " + c.getX());
			}
		} 
	}
	
	public void checkBoard() {
		for (int i=0; i < size*size; i++) {
			for (int j=0; j<size*size; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printBoard() {
		for (int i=0; i < size; i++) {
			for (int s=0; s < i; s++) {
				System.out.print(" ");
			}
			for (int j=0; j<size; j++) {
				System.out.print((pieces[i][j]).getOwner());
				System.out.print(" ");
			}
			
			System.out.println();
		}
	}

	public Board cloneBoard() {
		// TODO Auto-generated method stub
		return (Board) clone();
	}
	

	public Object clone() {
		Board newBoard = new Board(size);
		//public boolean WON = false; // someone wins this game
		//public char WINNER;
		//private Cell winningPath[]; // the winning path
		//private int counter = 0; // number of node on winning path
		for (int i=0; i < size; i++) {
			for (int j=0; j<size; j++) {
				newBoard.addPiece(i, j, this.pieces[i][j].getOwner());
			}
		}
		
		return newBoard;
		
	}
}