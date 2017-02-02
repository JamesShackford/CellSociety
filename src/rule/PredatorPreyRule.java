package rule;

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
	public static final Paint EMPTY_COLOR = Color.BLUE;
	public static final Paint FISH_COLOR = Color.SALMON;
	public static final Paint SHARK_COLOR = Color.GRAY;
	private Map<Integer, Paint> stateColorMap;

	public PredatorPreyRule(CellGrid cellGrid) {
		super(cellGrid);
		stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(EMPTY, EMPTY_COLOR);
		stateColorMap.put(FISH, FISH_COLOR);
		stateColorMap.put(SHARK, SHARK_COLOR);
	}

	@Override
	public Map<Integer, Paint> getStateMap() {
		return stateColorMap;
	}

	@Override
	public int getNextState(Cell cell) {
		Map<String, Cell> map = getCellGrid().getNeighborsWrap(cell.getX(), cell.getY());
		if (cell.getState() == FISH) {
			return FISH;
		}
		return EMPTY;
		
	}

}
