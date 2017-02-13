package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import property.DoubleProperty;
import property.Property;

public class SegregationRule extends Rule
{
	// 1 = alive, 0 = dead for state
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final Paint X_COLOR = Color.BLUE;
	public static final Paint O_COLOR = Color.RED;
	public static final Paint EMPTY_COLOR = Color.WHITE;

	public static final List<String> DATA_FIELDS = makeDataFields();

	private DoubleProperty threshold = new DoubleProperty("threshold");

	public SegregationRule(Map<String, String> dataValues)
	{
		for (Property<?> currProperty : this.getProperties()) {
			currProperty.setValue(dataValues.get(currProperty.getName()));
		}
		CellGrid grid = new CellGrid(this.getStartingConfiguration().getValue(), this,
				this.getHeight().getValue().intValue(), this.getWidth().getValue().intValue(),
				this.getCellType().getValue());
		this.setCellGrid(grid);
	}

	@Override
	public void determineNextState(Cell cell)
	{
		Map<String, Cell> map = cell.getNeighbors();
		List<Cell> emptyCells = new ArrayList<Cell>();
		int numNeighbors = 0;
		int numSimilar = 0;
		for (Cell neighbor : map.values()) {
			if (neighbor.getState() != EMPTY) {
				numNeighbors++;
			}
			if (neighbor.getState() == cell.getState()) {
				numSimilar++;
			}
		}
		for (int i = 0; i < this.getCellGrid().getHeight(); i++) {
			for (int j = 0; j < this.getCellGrid().getWidth(); j++) {
				Cell currCell = this.getCellGrid().getCell(i, j);
				if (currCell.getState() == EMPTY && currCell.getNextState() == EMPTY) {
					emptyCells.add(currCell);
				}
			}
		}
		double percentSame = ((double) numSimilar) / ((double) numNeighbors);
		if (percentSame <= threshold.getValue()) {
			Random rand = new Random();
			int randEmptyCell = rand.nextInt(emptyCells.size());
			emptyCells.get(randEmptyCell).setNextState(cell.getState());
			emptyCells.get(randEmptyCell).setState(cell.getState());
			cell.setNextState(EMPTY);
			cell.setState(EMPTY);
		}

	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(X, X_COLOR);
		stateMap.put(O, O_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

	@Override
	public List<Property<?>> getProperties()
	{
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.addAll(this.getGlobalProperties());
		properties.add(threshold);
		return properties;
	}

	private static List<String> makeDataFields()
	{
		List<String> fields = new ArrayList<String>();
		fields.addAll(GLOBAL_DATA_FIELDS);
		fields.add("threshold");
		return fields;
	}

	@Override
	public String getSimTypeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Property<?>> getPropertiesNewConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
