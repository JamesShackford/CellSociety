package rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import parameters.Parameter;
import parameters.SugarParameter;
import property.IntProperty;
import property.Property;

public class SugarRule extends Rule
{

	public static final int AGENT = -1;
	public static final int EMPTY = 0;
	public static final int MIN_SUGAR_STATE = 1;
	public static final Paint AGENT_COLOR = Color.BLUE;
	public static final Color SUGAR_COLOR = Color.LIGHTGREEN;
	public static final Paint EMPTY_COLOR = Color.WHITE;

	public static final List<String> DATA_FIELDS = makeDataFields();
	private IntProperty sugarGrowBackRate = new IntProperty("sugar_grow_back_rate");
	private IntProperty sugarGrowBackInterval = new IntProperty("sugar_grow_back_interval");
	private IntProperty upperMetabolismLimit = new IntProperty("upper_met_limit");
	private IntProperty lowerMetabolismLimit = new IntProperty("lower_met_limit");
	private IntProperty upperVisionLimit = new IntProperty("upper_vision_limit");
	private IntProperty lowerVisionLimit = new IntProperty("lower_vision_limit");
	private IntProperty upperAgentStartSugar = new IntProperty("upper_agent_start_sugar");
	private IntProperty lowerAgentStartSugar = new IntProperty("lower_agent_start_sugar");

	public SugarRule(Map<String, String> dataValues)
	{
		for (Property<?> currProperty : this.getProperties()) {
			currProperty.setValue(dataValues.get(currProperty.getName()));
			System.out.println(currProperty.getValue());
		}
		CellGrid grid = new CellGrid(this.getStartingConfiguration().getValue(), this,
				this.getHeight().getValue().intValue(), this.getWidth().getValue().intValue(),
				this.getCellType().getValue());
		this.setCellGrid(grid);
	}

	@Override
	public void determineNextState(Cell cell)
	{
		if (cell.getState() == AGENT) {
			if (cell.getParameters().get(SugarParameter.AGENT_SUGAR) <= 0) {
				kill(cell);
			} else {
				Cell nextCell = determineNextCell(cell);
				moveAgent(cell, nextCell);
				consumeSugar(nextCell);
				nextCell.setNextStateFinalized(true);
			}
		} else {
			growSugarIfReady(cell);
		}
		incrementGrowthStatus(cell);
	}

	private void incrementGrowthStatus(Cell cell)
	{
		cell.getParameters().incrementParameter(SugarParameter.NEXT_GROWTH, SugarParameter.GROWTH);
	}

	private void growSugarIfReady(Cell cell)
	{
		int growthNum = cell.getParameters().get(SugarParameter.GROWTH);
		int sugarLevel = cell.getState();
		int maxSugarLevel = cell.getParameters().get(SugarParameter.MAXSUGAR);
		if (growthNum % sugarGrowBackInterval.getValue() == 0 && sugarLevel < maxSugarLevel) {
			growSugarLevel(cell, maxSugarLevel);
		}
	}

	private Cell determineNextCell(Cell cell)
	{
		Collection<Cell> neighbors = getNeighbors(cell);
		if (neighbors.size() > 0) {
			return getNextCell(neighbors, cell.getX(), cell.getY());
		}
		return cell;
	}

	private void growSugarLevel(Cell cell, int maxSugar)
	{
		cell.setNextState(cell.getState() + sugarGrowBackRate.getValue());
		if (cell.getNextState() > maxSugar) {
			cell.setNextState(maxSugar);
		}
		cell.getParameters().set(SugarParameter.NEXT_GROWTH, 0);
	}

	private void kill(Cell cell)
	{
		cell.setNextState(EMPTY);
		cell.setNextStateFinalized(true);
		resetAgentParameters(cell);

	}

	private void resetAgentParameters(Cell cell)
	{
		cell.getParameters().set(SugarParameter.AGENT_METABOLISM, 0);
		cell.getParameters().set(SugarParameter.VISION, 0);
		cell.getParameters().set(SugarParameter.NEXT_AGENT_SUGAR, 0);
	}

	//
	private void consumeSugar(Cell cell)
	{
		int sugarToAdd = cell.getState();
		sugarToAdd -= cell.getParameters().get(SugarParameter.AGENT_METABOLISM);
		int currentAgentSugar = cell.getParameters().get(SugarParameter.AGENT_SUGAR);
		cell.getParameters().set(SugarParameter.NEXT_AGENT_SUGAR, currentAgentSugar + sugarToAdd);
	}

	//
	private void moveAgent(Cell current, Cell next)
	{
		int state = current.getState();
		next.setNextState(state);
		moveAgentData(current, next);
		kill(current);
	}

	//
	private void moveAgentData(Cell current, Cell next)
	{
		next.getParameters().set(SugarParameter.AGENT_METABOLISM,
				current.getParameters().get(SugarParameter.AGENT_METABOLISM));
		next.getParameters().set(SugarParameter.VISION, current.getParameters().get(SugarParameter.VISION));
		next.getParameters().set(SugarParameter.AGENT_SUGAR, current.getParameters().get(SugarParameter.AGENT_SUGAR));
		next.getParameters().set(SugarParameter.NEXT_AGENT_SUGAR,
				current.getParameters().get(SugarParameter.NEXT_AGENT_SUGAR));
	}

	private Cell getNextCell(Collection<Cell> neighbors, int currentX, int currentY)
	{
		int maxSugar = findMaxSugar(neighbors);
		removeLowSugarNeighbors(neighbors, maxSugar);
		return minDistNeighbor(neighbors, currentX, currentY);

	}

	private Cell minDistNeighbor(Collection<Cell> neighbors, int currentX, int currentY)
	{
		Cell closest = null;
		double minDist = Double.MAX_VALUE;
		for (Cell cell : neighbors) {
			if (distance(cell, currentX, currentY) < minDist) {
				minDist = distance(cell, currentX, currentY);
				closest = cell;
			}
		}
		return closest;

	}

	private double distance(Cell cell, double x, double y)
	{
		return Math.sqrt((cell.getX() - x) * (cell.getX() - x) + (cell.getY() - y) * (cell.getY() - y));
	}

	private void removeLowSugarNeighbors(Collection<Cell> neighbors, int maxSugar)
	{
		Collection<Cell> toRemove = new ArrayList<Cell>();
		for (Cell cell : neighbors) {
			if (cell.getState() < maxSugar) {
				toRemove.add(cell);
			}
		}
		neighbors.removeAll(toRemove);
	}

	private int findMaxSugar(Collection<Cell> neighbors)
	{
		int max = Integer.MIN_VALUE;
		for (Cell cell : neighbors) {
			if (cell.getState() > max) {
				max = cell.getState();
			}
		}
		return max;
	}

	private Collection<Cell> getNeighbors(Cell cell)
	{
		int vision = cell.getParameters().get(SugarParameter.VISION);
		Map<String, Cell> neighbors = cell.getNeighborsSides();
		for (String position : neighbors.keySet()) {
			Cell neighbor = neighbors.get(position);
			for (int i = 0; i < vision; i++) {
				Map<String, Cell> nextNeighbors = neighbor.getNeighborsSides();
				if (nextNeighbors.get(position) != null) {
					neighbors.put(position, nextNeighbors.get(position));
					neighbor = nextNeighbors.get(position);
				}
			}
		}
		removeIneligible(neighbors);
		return neighbors.values();
	}

	private void removeIneligible(Map<String, Cell> cellMap)
	{
		Collection<String> keysToRemove = new ArrayList<String>();
		for (String key : cellMap.keySet()) {
			if (cellMap.get(key) == null || cellMap.get(key).getState() == AGENT
					|| cellMap.get(key).nextStateFinalized()) {
				keysToRemove.add(key);
			}
		}
		cellMap.keySet().removeAll(keysToRemove);
	}

	@Override
	public void setCellGrid(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(AGENT, AGENT_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

	@Override
	public Paint getColor(int state)
	{
		if (state == AGENT || state == EMPTY) {
			return super.getColor(state);
		}
		Color sugarColor = SUGAR_COLOR;
		for (int i = MIN_SUGAR_STATE; i < state; i++) {
			sugarColor = sugarColor.darker();
		}
		return sugarColor;

	}

	@Override
	public Parameter getParameterType(int initialState)
	{
		return new SugarParameter(initialState, upperMetabolismLimit.getValue(), lowerMetabolismLimit.getValue(),
				upperVisionLimit.getValue(), lowerVisionLimit.getValue(), upperAgentStartSugar.getValue(),
				lowerAgentStartSugar.getValue());
	}

	@Override
	public List<Property<?>> getProperties()
	{
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.addAll(this.getGlobalProperties());
		properties.add(sugarGrowBackRate);
		properties.add(sugarGrowBackInterval);
		properties.add(upperMetabolismLimit);
		properties.add(lowerMetabolismLimit);
		properties.add(upperVisionLimit);
		properties.add(lowerVisionLimit);
		properties.add(upperAgentStartSugar);
		properties.add(lowerAgentStartSugar);
		return properties;
	}

	private static List<String> makeDataFields()
	{
		List<String> fields = new ArrayList<String>();
		fields.addAll(GLOBAL_DATA_FIELDS);
		fields.add("sugar_grow_back_rate");
		fields.add("sugar_grow_back_interval");
		fields.add("upper_met_limit");
		fields.add("lower_met_limit");
		fields.add("upper_vision_limit");
		fields.add("lower_vision_limit");
		fields.add("upper_agent_start_sugar");
		fields.add("lower_agent_start_sugar");
		return fields;
	}

}