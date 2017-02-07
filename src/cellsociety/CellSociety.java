package cellsociety;

import java.util.Random;

import cell.Cell;
import cell.RectangularCell;
import cellgrid.CellGrid;
import display.Display;
import rule.Rule;

public class CellSociety
{
	public final int GRID_SIZE = 50;

	private CellGrid cellGrid;
	private Rule rule;

	public CellSociety(Rule rule)
	{
		this.rule = rule;
		this.setup();

		// cellGrid.getGrid()[3][3] = new Cell(rule, 1, 3, 3);
		// cellGrid.getGrid()[4][3] = new Cell(rule, 1, 4, 3);
		// cellGrid.getGrid()[3][4] = new Cell(rule, 1, 3, 4);
		// cellGrid.getGrid()[4][4] = new Cell(rule, 1, 4, 4);
	}

	public void setup()
	{
		cellGrid = new CellGrid(GRID_SIZE);
		rule.setCellGrid(cellGrid);
		for (int i = 0; i < cellGrid.getHeight(); i++) {
			for (int j = 0; j < cellGrid.getWidth(); j++) {
				Cell addedCell = new RectangularCell(rule, getRandomState(), i, j);
				cellGrid.setCell(i, j, addedCell);
			}
		}
	}

	public int getRandomState()
	{
		int numPossibleStates = rule.getStateMap().size();
		Random rand = new Random();
		int randStateNumber = rand.nextInt(numPossibleStates);
		Integer[] possibleStates = new Integer[numPossibleStates];
		possibleStates = rule.getStateMap().keySet().toArray(possibleStates);
		return possibleStates[randStateNumber];
	}

	public void step(Display display)
	{
		cellGrid.updateCellGrid();
		display.displayGrid(cellGrid);
	}

}
