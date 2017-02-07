package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SegregationRule extends Rule
{
	// 1 = alive, 0 = dead for state
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final Paint X_COLOR = Color.BLUE;
	public static final Paint O_COLOR = Color.RED;
	public static final Paint EMPTY_COLOR = Color.WHITE;

	private double threshold;

	public SegregationRule(double threshold)
	{
		super();
		this.threshold = threshold;
	}

	public SegregationRule(CellGrid myGrid, double threshold)
	{
		super(myGrid);
		this.threshold = threshold;
	}

	@Override
	public void determineNextState(Cell cell)
	{
		Map<String, Cell> map = getCellGrid().getNeighbors(cell.getX(), cell.getY());
		List<Cell> emptyCells = new ArrayList<Cell>();
		int numNeighbors = 0;
		int numSimilar = 0;
		for (Cell neighbor : map.values()) {
			if (neighbor.getState() != EMPTY) {
				numNeighbors++;
			}
			if (neighbor.getState() == cell.getState()) {
				numSimilar++;
			}
		}
		for (int i = 0; i < this.getCellGrid().getHeight(); i++) {
			for (int j = 0; j < this.getCellGrid().getWidth(); j++) {
				Cell currCell = this.getCellGrid().getCell(i, j);
				if (currCell.getState() == EMPTY && currCell.getNextState() == EMPTY) {
					emptyCells.add(currCell);
				}
			}
		}
		double percentSame = ((double) numSimilar) / ((double) numNeighbors);
		if (percentSame <= threshold) {
			Random rand = new Random();
			int randEmptyCell = rand.nextInt(emptyCells.size());
			emptyCells.get(randEmptyCell).setNextState(cell.getState());
			emptyCells.get(randEmptyCell).setState(cell.getState());
			cell.setNextState(EMPTY);
			cell.setState(EMPTY);
		}

	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(X, X_COLOR);
		stateMap.put(O, O_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

}
