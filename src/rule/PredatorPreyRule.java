package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Things that still need to be determined:
 * How to keep track of if a cell is ready to reproduce
 * How to keep track of how close a shark is to starving
 * Should probably determine precedence of what happens when two or more cells try to move or reproduce into the same
 * 	empty cell. Right now the precedence is simply whichever cell moves to or reproduces into an empty cell first gets precedence,
 * 	i.e., the earlier cell called by CellGrid in updatedCellGrid() gets precedence
 * 
 * 
 * Things that need to change in other classes:
 * int getNextState() becomes void determineNextState() and sets nextState for current cell and appropriate adjacent cells
 *    - this would simplify a line CellGrid
 * Cell needs a boolean nextStateFinalized parameter that, when true, does not allow modification of this cells nextState
 *    - updateCell would reset nextStateFinalized to false for the next step
 *    
 * @author Derek
 *
 */
public class PredatorPreyRule extends Rule {
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	public static final int STARVE_TIME = 5;
	public static final Paint EMPTY_COLOR = Color.BLUE;
	public static final Paint FISH_COLOR = Color.SALMON;
	public static final Paint SHARK_COLOR = Color.GRAY;

	public PredatorPreyRule(CellGrid cellGrid)
	{
		super(cellGrid);
	}
	
	
	//I think it might be a good idea to make getNextState() a void method called determineNextState() or something 
	//like that since in this sim sometimes needs to change the state of adjacent cells and not just its own cell
	//
	//also this method should probably be refactored
	@Override
	public void determineNextState(Cell cell) {
		Map<String, Cell> neighbors = getCellGrid().getNeighborsWrap(cell.getX(), cell.getY());
		if (!cell.nextStateFinalized()) {
			List<Cell> emptyNeighbors = new ArrayList<Cell>();
			if (cell.getState() == FISH) {
				emptyNeighbors = getNeighborsOfState(neighbors, EMPTY);
				if (emptyNeighbors.size() != 0) {
					if (cell.readyToReproduce()) {
						moveToRandCell(emptyNeighbors, cell.getState());
					} else {
						moveToRandCell(emptyNeighbors, cell.getState());
						cell.setNextState(EMPTY);
					}
				}
			} else if (cell.getState() == SHARK) {
				List<Cell> fishNeighbors = new ArrayList<Cell>();
				fishNeighbors = getNeighborsOfState(neighbors, FISH);
				if (cell.getStarveTime() == STARVE_TIME) {
					kill(cell);
				} else if (fishNeighbors.size() != 0) {
					eat(fishNeighbors, cell);
				} else if (emptyNeighbors.size() != 0) {
					if (cell.readyToReproduce()) {
						moveToRandCell(emptyNeighbors, cell.getState());
					} else {
						moveToRandCell(emptyNeighbors, SHARK);
						cell.setNextState(EMPTY);
					}
				}
			}
		}
	}

	/**
	 * Currently shark moves to adjacent fish cell and eats fish, leaves previous cell as
	 * empty (eat in motion)
	 * 
	 * Could instead have shark stay in place and fish cell set to empty if that
	 * makes more sense (eat in place)
	 * 
	 * @param fishList   list of adjacent fish cells
	 * @param cell       current shark cell
	 */
	private void eat(List<Cell> fishList, Cell cell) {
		Cell movedTo = moveToRandCell(fishList, SHARK);
		cell.setNextState(EMPTY);
		movedTo.resetStarveTime();
	}

	/**
	 * 
	 * @param neighbors	Map of all neighbors
	 * @param state		state to match return list to
	 * @return list of cells in neighbors of given state
	 */
	private List<Cell> getNeighborsOfState(Map<String, Cell> neighbors, int state) {
		List<Cell> cellList = new ArrayList<Cell>();
		for(Cell cell : neighbors.values()){
			if(cell.getState() == state){
				cellList.add(cell);
			}
		}
		return cellList;
	}

	/**
	 * Moves state to random cell in cellList
	 * set cell moved to nextStateFinalized = true
	 * 
	 * @param cellList	list of cells where one is randomly chosen for the current cell to move to
	 * @param state		state to be moved
	 */
	private Cell moveToRandCell(List<Cell> cellList, int state) {
		int index = randIndex(cellList.size());
		cellList.get(index).setNextState(state);
		cellList.get(index).setNextStateFinalized(true);
		return cellList.get(index);

	}
	
	/**
	 * 
	 * 
	 * @param topBound   topBound of random num generation non-inclusive
	 * @return				random num between 0 and topBound-1 inclusive
	 */
	private int randIndex(int topBound){
		return (int)(Math.random()*topBound);
	}

	/**
	 * Sets input cell to EMPTY, killing it
	 * 
	 * @param cell		cell to be killed
	 */
	private void kill(Cell cell) {
		cell.setNextState(EMPTY);
		cell.setNextStateFinalized(true);
	}


	@Override
	public Map<Integer, Paint> makeStateMap() {
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(FISH, FISH_COLOR);
		stateMap.put(SHARK, SHARK_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}
}