package rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Paint;
import parameters.Parameter;
import property.IntArrayProperty;
import property.IntProperty;
import property.Property;

public abstract class Rule
{
	CellGrid cellGrid;
	private Map<Integer, Paint> stateColorMap;
	public static final List<String> GLOBAL_DATA_FIELDS = Arrays
			.asList(new String[] { "initial_states", "width", "height" });
	private IntArrayProperty startingConfiguration = new IntArrayProperty("initial_states");
	private IntProperty width = new IntProperty("width");
	private IntProperty height = new IntProperty("height");

	public Rule(Map<String, String> dataValues)
	{
		// for (Property<?> currProperty : this.getProperties()) {
		// currProperty.setValue(dataValues.get(currProperty.getName()));
		// }
		// CellGrid grid = new
		// CellGrid(this.getStartingConfiguration().getValue(), this);
		// this.setCellGrid(grid);
	}

	public Rule()
	{
		stateColorMap = makeStateMap();
	}

	public Rule(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
		stateColorMap = makeStateMap();
	}

	public Paint getColor(int state)
	{
		return stateColorMap.get(state);
	}

	public abstract void determineNextState(Cell cell);

	public abstract Map<Integer, Paint> makeStateMap();

	public CellGrid getCellGrid()
	{
		return cellGrid;
	}

	public void setCellGrid(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}

	public Map<Integer, Paint> getStateMap()
	{
		return stateColorMap;
	}

	public void duplicateStats(Cell current, Cell newCell)
	{
		newCell.getParameters().replaceParameters(current.getParameters().getParameterClone());
	}

	public Parameter getParameterType(int intialState)
	{
		return new Parameter();
	}

	public abstract List<Property<?>> getProperties();

	public List<Property<?>> getGlobalProperties()
	{
		List<Property<?>> globalProperties = new ArrayList<Property<?>>();
		globalProperties.add(startingConfiguration);
		globalProperties.add(width);
		globalProperties.add(height);
		return globalProperties;
	}

	public IntArrayProperty getStartingConfiguration()
	{
		return startingConfiguration;
	}

	public IntProperty getWidth()
	{
		return width;
	}

	public IntProperty getHeight()
	{
		return height;
	}
}
