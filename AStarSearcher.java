import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}
	
	public double hCalculator(int currentX, int currentY){
		double test = Math.sqrt(Math.pow((currentX - maze.getGoalSquare().X),2)+ Math.pow((currentY - maze.getGoalSquare().Y),2));
		return test;
	}
	
	public void updateMaze(State node){
		while(node.getParent() != null){
			maze.setOneSquare(node.getSquare(), '.');
			node = node.getParent();
		}
		
		
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
				
		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		
		// TODO initialize the root state and add
		State rootState = new State(maze.getPlayerSquare(), null, 0, 0);
		StateFValuePair root = new StateFValuePair(rootState,hCalculator(maze.getPlayerSquare().X,maze.getPlayerSquare().Y));
		// to frontier list
		frontier.add(root);
		// ...

		while (!frontier.isEmpty()) {
			StateFValuePair node = frontier.poll();
			noOfNodesExpanded++;
			if(node.getState().isGoal(maze)){
				cost = node.getState().getDepth();
				updateMaze(node.getState().getParent());
				return true;
			}
			explored[node.getState().getX()][node.getState().getY()] = true;
			ArrayList<State> children = node.getState().getSuccessors(explored, maze);
			if(node.getState().getDepth()+1 > maxDepthSearched){
				maxDepthSearched = node.getState().getDepth()+1;
			}
			for(State state : children){
				
				Iterator<StateFValuePair> itr = frontier.iterator();
				boolean toAdd = true;
				StateFValuePair stateToRemove = null;
				while(itr.hasNext()) {
					StateFValuePair curr = itr.next();
					if((curr.getState().getX() == state.getX() && curr.getState().getY() == state.getY()) || explored[state.getX()][state.getY()]) {
						toAdd =false;
						stateToRemove = curr;
					}
				}
				if(toAdd) {
					frontier.add(new StateFValuePair(state,hCalculator(state.getX(),state.getY()) + state.getGValue()));
				}else if(stateToRemove.getState().getGValue() > state.getGValue()) {
					frontier.remove(stateToRemove);
					frontier.add(new StateFValuePair(state,hCalculator(state.getX(),state.getY()) + state.getGValue()));
				}
				
				if(frontier.size()> maxSizeOfFrontier){
					maxSizeOfFrontier = frontier.size();
				}

			}
			
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		}
		return false;
		// TODO return false if no solution
	}

}
