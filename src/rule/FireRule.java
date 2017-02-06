package rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FireRule extends Rule
{
	public static final int FIRE = 2;
	public static final int TREE = 1;
	public static final int DEAD = 0;
	public static final Color COLOR_OF_FIRE = Color.RED;
	public static final Color COLOR_OF_TREE = Color.FORESTGREEN;
	public static final Color COLOR_OF_DEAD = Color.WHITESMOKE;
	private double myProbFire;

	public FireRule(double probFire)
	{
		super();
		myProbFire = probFire;
	}

	public FireRule(CellGrid myGrid, double probFire)
	{
		super(myGrid);
		myProbFire = probFire;
	}

	@Override
	public int getNextState(Cell cell)
	{
		Map<String, Cell> neighbors = getCellGrid().getNeighborsSides(cell.getX(), cell.getY());
		if (cell.getState() == FIRE || cell.getState() == DEAD) {
			return DEAD;
		}
		Random catchFire = new Random();
		for (Cell neighborCell : neighbors.values()) {
			if (neighborCell != null && neighborCell.getState() == FIRE && catchFire.nextDouble() < myProbFire) {
				return FIRE;
			}
		}
		return cell.getState();
	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		HashMap<Integer, Paint> stateColorMap = new HashMap<Integer, Paint>();
		stateColorMap.put(2, COLOR_OF_FIRE);
		stateColorMap.put(1, COLOR_OF_TREE);
		stateColorMap.put(0, COLOR_OF_DEAD);
		return stateColorMap;
	}
}