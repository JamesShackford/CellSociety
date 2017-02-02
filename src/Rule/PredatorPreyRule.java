package Rule;

import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PredatorPreyRule extends Rule {
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	private Map<Integer, Paint> stateColorMap;

	public PredatorPreyRule(CellGrid cellGrid) {
		super(cellGrid);
		stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(EMPTY, Color.BLUE);
		stateColorMap.put(FISH, Color.SALMON);
		stateColorMap.put(SHARK, Color.GRAY);
	}

	@Override
	public Map<Integer, Paint> getStateMap() {
		return stateColorMap;
	}

	@Override
	public int getNextState(Cell cell) {
		Map<String, Cell> map = getCellGrid().getNeighborsWrap(cell.getX(), cell.getY());
		if (cell.getState() == FISH){
			
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

}
