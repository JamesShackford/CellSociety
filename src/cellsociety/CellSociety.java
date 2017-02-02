package cellsociety;

import java.util.Random;

import Display.Display;
import Rule.GameOfLifeRule;
import Rule.Rule;
import cell.Cell;
import cellGrid.CellGrid;

public class CellSociety
{
	public final int GRID_SIZE = 50;

	private CellGrid cellGrid;
	private Rule rule;

	public CellSociety()
	{
		cellGrid = new CellGrid(GRID_SIZE);
		rule = new GameOfLifeRule(cellGrid);
		for (int i = 0; i < cellGrid.getGrid().length; i++) {
			for (int j = 0; j < cellGrid.getGrid()[i].length; j++) {
				Random rand = new Random();
				if (rand.nextDouble() < 0.5) {
					cellGrid.getGrid()[i][j] = new Cell(rule, 1, i, j);
				} else {
					cellGrid.getGrid()[i][j] = new Cell(rule, 0, i, j);
				}
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
