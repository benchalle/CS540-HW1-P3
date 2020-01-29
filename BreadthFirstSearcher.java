import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	public void updateMaze(State node){
		while(node.getParent() != null){
			maze.setOneSquare(node.getSquare(), '.');
			node = node.getParent();
		}
	}
	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		State rootState = new State(maze.getPlayerSquare(), null, 0, 0);

		queue.add(rootState);

		while (!queue.isEmpty()) {
			State node = queue.pop();
			noOfNodesExpanded++;
			explored[node.getX()][node.getY()] = true;
			
			if(node.isGoal(maze)){
				cost = node.getDepth();
				maxDepthSearched = node.getDepth();
				updateMaze(node.getParent());
				return true;
			}
			ArrayList<State> children = node.getSuccessors(explored, maze);
				
			
			
			for(State state : children){
				boolean toAdd = true;
					if(queue.isEmpty()) {
						queue.add(state);
					}else {
					Iterator<State> itr = queue.iterator();
					while(itr.hasNext()) {
						State curr = itr.next();
						if((state.getX() == curr.getX() && state.getY() == curr.getY()) || explored[state.getX()][state.getY()]){
							toAdd = false;
						}
					}
					if(toAdd) {

						queue.add(state);
					}
					}
				if(queue.size() > maxSizeOfFrontier){
					maxSizeOfFrontier = queue.size();
				}
			}
			
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
		}
			return false;
		// TODO return false if no solution
	}
}
