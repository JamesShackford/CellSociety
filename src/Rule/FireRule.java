package Rule;

import java.util.HashMap;
import java.util.Map;

import Rule.Rule;
import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FireRule extends Rule{
	public static final int FIRE = 2;
	public static final int TREE = 1;
	public static final int DEAD = 0;
	public static final Color COLOR_OF_FIRE = Color.RED;
	public static final Color COLOR_OF_TREE = Color.RED;
	public static final Color COLOR_OF_BURNT = Color.RED;
	private CellGrid myGrid;
	private Map<Integer, Paint> stateColorMap;
	
	public FireRule(CellGrid myGrid) {
		super(myGrid);
	}

	@Override
	public Map<Integer, Paint> getStateMap() {
		stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(2, COLOR_OF_FIRE);
		stateColorMap.put(1, COLOR_OF_TREE);
		stateColorMap.put(0, COLOR_OF_BURNT);
		return stateColorMap;
	}

	@Override
	public int getNextState(Cell cell) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
