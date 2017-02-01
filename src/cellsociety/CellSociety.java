package cellsociety;

import Display.Display;
import cell.Cell;
import cell.GameOfLifeCell;
import cellGrid.CellGrid;

public class CellSociety
{
	public final int GRID_SIZE = 50;

	private CellGrid cellGrid;

	public CellSociety()
	{
		cellGrid = new CellGrid(GRID_SIZE);
		for (int i = 0; i < cellGrid.getGrid().length; i++) {
			for (int j = 0; j < cellGrid.getGrid()[i].length; j++) {
				cellGrid.getGrid()[i][j] = new GameOfLifeCell(0);
			}
		}
	}

	public void setup()
	{

	}

	public void step(Display display)
	{
		for (Cell[] cellRow : cellGrid.getGrid()) {
			for (Cell currCell : cellRow) {
				currCell.updateCell();
			}
		}
		display.displayGrid(cellGrid);
	}

}
