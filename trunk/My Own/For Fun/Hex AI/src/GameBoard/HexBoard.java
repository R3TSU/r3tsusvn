/**
 * 
 */
package GameBoard;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author acer
 *
 */
public class HexBoard extends Board {
	private ArrayList<HashSet<Integer>> path;
	private int nodes;
	
	/**
	 * 
	 */
	public HexBoard(int size) {
		super(size);
		nodes = size*size;
		path = new ArrayList<HashSet<Integer>>();
		initEdges();
	}

	private void initEdges() {
		for (int i=0;i<size*size;i++) {
			path.add(new HashSet<Integer>());
		}
		
		for (int i=0;i<size;i++) {
			for (int j=0;j<size;j++) {
				if (i < size-1) {
					addPath(toNode(i,j), toNode(i+1,j));
				}
				if (j < size-1) {
					addPath(toNode(i,j), toNode(i,j+1));
					if (i > 0) {
						addPath(toNode(i,j), toNode(i-1,j+1));
					}
				}
			}
		}
		
		for (int i=0;i<size;i++) {
			addPathOne(toNode(i,0),nodes);
			addPathOne(toNode(i,size-1),nodes+1);
			addPathOne(toNode(0,i),nodes+2);
			addPathOne(toNode(size-1,i),nodes+3);
		}
		
	}
	
	private void addPath(int node, int node2) {
		HashSet<Integer> n = path.get(node);
		n.add(node2);
		n = path.get(node2);
		n.add(node);
	}
	
	private void addPathOne(int node, int node2) {
		HashSet<Integer> n = path.get(node);
		n.add(node2);
	}
	
	public int toNode(int row, int col) {
		return row*size+col;
	}
	
	public int toRow(int node) {
		return node/size;
	}
	
	public int toCol(int node) {
		return node%size;
	}
}