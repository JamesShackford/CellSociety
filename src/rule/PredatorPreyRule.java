package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import parameters.Parameter;
import parameters.PredatorPreyParameter;

/**
 * 
 * @author Derek
 *
 */
public class PredatorPreyRule extends Rule {
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	public static final Paint EMPTY_COLOR = Color.BLUE;
	public static final Paint FISH_COLOR = Color.YELLOW;
	public static final Paint SHARK_COLOR = Color.GRAY;

	private int starveTime;
	private int breedTime;

	public PredatorPreyRule(int starveTime, int breedTime) {
		this.starveTime = starveTime;
		this.breedTime = breedTime;
	}

	public PredatorPreyRule(CellGrid cellGrid, int starveTime, int breedTime) {
		super(cellGrid);
		this.starveTime = starveTime;
		this.breedTime = breedTime;
	}

	//this method should probably be refactored
	@Override
	public void determineNextState(Cell cell)
	{
		Map<String, Cell> neighbors = cell.getNeighborsWrap();
		if (!cell.nextStateFinalized()) {
			List<Cell> emptyNeighbors = getEligibleNeighborsOfState(neighbors, EMPTY);
			if (cell.getState() == FISH) {
				updateGenericSpeciesTraits(cell);
				if (emptyNeighbors.size() != 0) {
					if (readyToReproduce(cell)) {
						reproduce(cell, emptyNeighbors);
					} else {
						move(cell, emptyNeighbors);
					}
				}
			} else if (cell.getState() == SHARK) {
				updateGenericSpeciesTraits(cell);
				incrementStarveTime(cell);
				List<Cell> fishNeighbors = new ArrayList<Cell>();
				fishNeighbors = getEligibleNeighborsOfState(neighbors, FISH);
				if (readyToStarve(cell)) {
					kill(cell);
				} else if (fishNeighbors.size() != 0) {
					eat(fishNeighbors, cell);
				} else if (emptyNeighbors.size() != 0) {
					if (readyToReproduce(cell)) {
						reproduce(cell, emptyNeighbors);
					} else {
						Cell movedTo = move(cell, emptyNeighbors);
						incrementStarveTime(movedTo);
					}
				}
			} 
		}
	}

	private void updateGenericSpeciesTraits(Cell cell) {
		cell.setNextStateFinalized(true);
		incrementBreedTime(cell);
	}

	private Cell move(Cell cell, List<Cell> emptyNeighbors) {
		Cell movedTo = duplicateToRandCell(cell, emptyNeighbors);
		incrementBreedTime(movedTo);
		kill(cell);
		return movedTo;
	}

	private void reproduce(Cell cell, List<Cell> emptyNeighbors) {
		Cell movedTo = duplicateToRandCell(cell, emptyNeighbors);
		resetBreedTime(movedTo);
		resetBreedTime(cell);
	}

	@Override
	public void setCellGrid(CellGrid cellGrid) {
		this.cellGrid = cellGrid;
	}

	/**
	 * Currently shark moves to adjacent fish cell and eats fish, leaves
	 * previous cell as empty (eat in motion)
	 * 
	 * Could instead have shark stay in place and fish cell set to empty if that
	 * makes more sense (eat in place)
	 * 
	 * @param fishList
	 *            list of adjacent fish cells
	 * @param cell
	 *            current shark cell
	 */
	private void eat(List<Cell> fishList, Cell cell) {
		Cell movedTo = duplicateToRandCell(cell, fishList);
		kill(cell);
		resetStarveTime(movedTo);
	}

	/**
	 * 
	 * @param neighbors
	 *            Map of all neighbors
	 * @param state
	 *            state to match return list to
	 * @return list of cells in neighbors of given state
	 */
	private List<Cell> getEligibleNeighborsOfState(Map<String, Cell> neighbors, int state) {
		List<Cell> cellList = new ArrayList<Cell>();
		for (Cell cell : neighbors.values()) {
			if (cell.getState() == state && !cell.nextStateFinalized()) {
				cellList.add(cell);
			}
		}
		return cellList;
	}

	/**
	 * Moves state to random cell in cellList set cell moved to
	 * nextStateFinalized = true
	 * 
	 * @param cellList
	 *            list of cells where one is randomly chosen for the current
	 *            cell to move to
	 * @param state
	 *            state to be moved
	 */
	private Cell duplicateToRandCell(Cell cell, List<Cell> cellList) {
		int index = randIndex(cellList.size());
		cellList.get(index).setNextState(cell.getState());
		cellList.get(index).setNextStateFinalized(true);
		duplicateStats(cell, cellList.get(index));
		return cellList.get(index);
	}

	/**
	 * 
	 * 
	 * @param topBound
	 *            topBound of random num generation non-inclusive
	 * @return random num between 0 and topBound-1 inclusive
	 */
	private int randIndex(int topBound) {
		return (int) (Math.random() * topBound);
	}

	/**
	 * Sets input cell to EMPTY, killing it
	 * 
	 * @param cell
	 *            cell to be killed
	 */
	private void kill(Cell cell) {
		cell.setNextState(EMPTY);
		resetBreedTime(cell);
		resetStarveTime(cell);
	}

	private void resetBreedTime(Cell cell) {
		cell.getParameters().set(PredatorPreyParameter.NEXT_BREED, 0);
	}
	
	private void resetStarveTime(Cell cell) {
		cell.getParameters().set(PredatorPreyParameter.NEXT_STARVE, 0);
	}

	private boolean readyToReproduce(Cell cell) {
		return cell.getParameters().get(PredatorPreyParameter.BREED) >= breedTime;
	}

	private boolean readyToStarve(Cell cell) {
		return cell.getParameters().get(PredatorPreyParameter.STARVE) >= starveTime;
	}

	private void incrementBreedTime(Cell cell) {
		cell.getParameters().incrementParameter(PredatorPreyParameter.NEXT_BREED, PredatorPreyParameter.BREED);
	}
	
	private void incrementStarveTime(Cell cell) {
		cell.getParameters().incrementParameter(PredatorPreyParameter.NEXT_STARVE, PredatorPreyParameter.STARVE);
	}

	@Override
	public Map<Integer, Paint> makeStateMap() {
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(FISH, FISH_COLOR);
		stateMap.put(SHARK, SHARK_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

	@Override
	public Parameter getParameterType(int intialState) {
		Parameter newParameter = new PredatorPreyParameter();
		return newParameter;
		
	}
}