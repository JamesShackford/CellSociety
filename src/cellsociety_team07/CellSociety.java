package cellsociety_team07;

import cell.Cell;
import cellGrid.CellGrid;

public class CellSociety
{
	public final int GRID_SIZE = 50;

	private CellGrid cellGrid;

	CellSociety()
	{
		cellGrid = new CellGrid(GRID_SIZE);
	}

	public void setup()
	{

	}

	public void step()
	{
		for (Cell[] cellRow : cellGrid.getGrid()) {
			for (Cell currCell : cellRow) {
				currCell.updateCell();
			}
		}
	}

}
