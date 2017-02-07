package rule;

import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameOfLifeRule extends Rule
{
	// 1 = alive, 0 = dead for state
	public static final int ALIVE = 1;
	public static final int DEAD = 0;
	public static final Paint ALIVE_COLOR = Color.WHITE;
	public static final Paint DEAD_COLOR = Color.BLACK;

	public GameOfLifeRule()
	{
		super();
	}

	public GameOfLifeRule(CellGrid myGrid)
	{
		super(myGrid);
	}

	@Override
	public void determineNextState(Cell cell)
	{
		Map<String, Cell> map = getCellGrid().getNeighbors(cell.getX(), cell.getY());
		int numAlive = 0;
		for (Cell neighborCell : map.values()) {
			if (neighborCell != null) {
				if (neighborCell.getState() == ALIVE) {
					numAlive++;
				}
			}

		}

		// need to simplify this if statement structure
		if (cell.getState() == ALIVE) {
			if (numAlive < 2 || numAlive > 3) {
				cell.setNextState(DEAD);
			} else if (numAlive == 2 || numAlive == 3) {
				cell.setNextState(ALIVE);
			}

		} else if (numAlive == 3) {
			cell.setNextState(ALIVE);
		}

	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(DEAD, DEAD_COLOR);
		stateMap.put(ALIVE, ALIVE_COLOR);
		return stateMap;
	}

}
