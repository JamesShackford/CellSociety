package Rule;

import java.util.HashMap;
import java.util.Map;

import Rule.Rule;
import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameOfLifeRule extends Rule {

	// 1 = alive, 0 = dead for state
	public static final int ALIVE = 1;
	public static final int DEAD = 0;
	private Map<Integer, Paint> stateColorMap;

	public GameOfLifeRule(CellGrid myGrid) {
		super(myGrid);
		stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(DEAD, Color.RED);
		stateColorMap.put(ALIVE, Color.BLUE);

	}

	public Map<Integer, Paint> getStateMap() {
		return stateColorMap;
	}

	public int getNextState(Cell cell) {
		Map<String, Cell> map = getCellGrid().getNeighbors(cell.getX(), cell.getY());
		int numAlive = 0;
		for (Cell neighborCell : map.values()) {
			if (neighborCell != null) {
				if (neighborCell.getState() == 1) {
					numAlive++;
				}
			}

		}

		// need to simplify this if statement structure
		if (cell.getState() == 1) {
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
