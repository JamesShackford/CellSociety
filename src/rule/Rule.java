package rule;

import java.util.Map;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Paint;

public abstract class Rule
{
	CellGrid cellGrid;
	private Map<Integer, Paint> stateColorMap = makeStateMap();

	public Rule()
	{
	}

	public Rule(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}

	public Map<Integer, Paint> getStateMap()
	{
		return stateColorMap;
	}

	public abstract int getNextState(Cell cell);

	public abstract Map<Integer, Paint> makeStateMap();

	public CellGrid getCellGrid()
	{
		return cellGrid;
	}

	public void setCellGrid(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}
}
