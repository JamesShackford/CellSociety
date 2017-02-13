package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import property.Property;

public class GameOfLifeRule extends Rule
{
	// 1 = alive, 0 = dead for state
	public static final int ALIVE = 1;
	public static final int DEAD = 0;
	public static final Paint ALIVE_COLOR = Color.WHITE;
	public static final Paint DEAD_COLOR = Color.BLACK;
	public static final List<String> DATA_FIELDS = makeDataFields();

	public GameOfLifeRule(Map<String, String> dataValues)
	{
		for (Property<?> currProperty : this.getProperties()) {
			currProperty.setValue(dataValues.get(currProperty.getName()));
		}
		CellGrid grid = new CellGrid(this.getStartingConfiguration().getValue(), this,
				this.getHeight().getValue().intValue(), this.getWidth().getValue().intValue());
		this.setCellGrid(grid);
	}

	@Override
	public void determineNextState(Cell cell)
	{
		Map<String, Cell> map = cell.getNeighbors();
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

	@Override
	public List<Property<?>> getProperties()
	{
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.addAll(this.getGlobalProperties());
		return properties;
	}

	private static List<String> makeDataFields()
	{
		List<String> fields = new ArrayList<String>();
		fields.addAll(GLOBAL_DATA_FIELDS);
		return fields;
	}

}
