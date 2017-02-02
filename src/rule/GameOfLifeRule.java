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
	private final Map<Integer, Paint> stateColorMap = makeStateMap();

	public GameOfLifeRule(CellGrid myGrid)
	{
		super(myGrid);
	}

	private static Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> colorMap = new HashMap<Integer, Paint>();
		colorMap.put(DEAD, DEAD_COLOR);
		colorMap.put(ALIVE, ALIVE_COLOR);
		return colorMap;
	}

	@Override
	public Map<Integer, Paint> getStateMap()
	{
		return stateColorMap;
	}

	@Override
	public int getNextState(Cell cell)
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
				return DEAD;
			} else if (numAlive == 2 || numAlive == 3) {
				return ALIVE;
			}

		} else if (numAlive == 3) {
			return ALIVE;
		}
		return cell.getState();

	}

}
