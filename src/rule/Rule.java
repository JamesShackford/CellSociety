package rule;

import java.util.Map;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Paint;

public abstract class Rule
{
	CellGrid cellGrid;

	public Rule(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}

	public abstract Map<Integer, Paint> getStateMap();

	public abstract int getNextState(Cell cell);

	public CellGrid getCellGrid()
	{
		return cellGrid;
	}

	public void setCellGrid(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}
}
