package cellsociety;

import java.util.Random;

import cell.Cell;
import cellGrid.CellGrid;
import display.Display;
import rule.GameOfLifeRule;
import rule.Rule;

public class CellSociety
{
	public final int GRID_SIZE = 50;

	private CellGrid cellGrid;
	private Rule rule;

	public CellSociety()
	{
		cellGrid = new CellGrid(GRID_SIZE);
		rule = new GameOfLifeRule(cellGrid);
		for (int i = 0; i < cellGrid.getHeight(); i++) {
			for (int j = 0; j < cellGrid.getWidth(); j++) {
				Random rand = new Random();
				Cell addedCell = null;
				if (rand.nextDouble() < 0.5) {
					addedCell = new Cell(rule, 1, i, j);
				} else {
					addedCell = new Cell(rule, 0, i, j);
				}
				cellGrid.setCell(i, j, addedCell);
			}
		}
		// cellGrid.getGrid()[3][3] = new Cell(rule, 1, 3, 3);
		// cellGrid.getGrid()[4][3] = new Cell(rule, 1, 4, 3);
		// cellGrid.getGrid()[3][4] = new Cell(rule, 1, 3, 4);
		// cellGrid.getGrid()[4][4] = new Cell(rule, 1, 4, 4);
	}

	public void setup()
	{

	}

	public void step(Display display)
	{
		cellGrid.updateCellGrid();
		display.displayGrid(cellGrid);
	}

}
