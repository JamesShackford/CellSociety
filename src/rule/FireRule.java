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
import xml.FireXMLRule;

public class FireRule extends Rule
{
	public static final int FIRE = 2;
	public static final int TREE = 1;
	public static final int DEAD = 0;
	public static final Color COLOR_OF_FIRE = Color.RED;
	public static final Color COLOR_OF_TREE = Color.FORESTGREEN;
	public static final Color COLOR_OF_DEAD = Color.WHITESMOKE;
	public static final List<String> DATA_FIELDS = makeDataFields();
	private DoubleProperty myProbFire = new DoubleProperty("fire_probability");

	public FireRule(Map<String, String> dataValues)
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
		Map<String, Cell> neighbors = cell.getNeighborsSides();
		if (cell.getState() == FIRE || cell.getState() == DEAD) {
			cell.setNextState(DEAD);
		} else {
			Random catchFire = new Random();
			for (Cell neighborCell : neighbors.values()) {
				if (neighborCell != null && neighborCell.getState() == FIRE
						&& catchFire.nextDouble() <= myProbFire.getValue()) {
					cell.setNextState(FIRE);
				}
			}
		}
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

	@Override
	public List<Property<?>> getProperties()
	{
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.addAll(this.getGlobalProperties());
		properties.add(myProbFire);
		return properties;
	}
	
	

	private static List<String> makeDataFields()
	{
		List<String> fields = new ArrayList<String>();
		fields.addAll(GLOBAL_DATA_FIELDS);
		fields.add("fire_probability");
		return fields;
	}

	@Override
	public List<Property<?>> getPropertiesNewConfig() {
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.add(myProbFire);
		List<Property<?>> globalProps = new ArrayList<Property<?>>();
		globalProps.addAll(this.getGlobalProperties()); 
		globalProps.remove(0);
		properties.addAll(globalProps);
		return properties;
		
	}

	@Override
	public String getSimTypeCopy() {
		return FireXMLRule.DATA_TYPE_COPY;
		
	}

}
