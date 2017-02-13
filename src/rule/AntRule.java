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
import parameters.AntParameter;
import parameters.Parameter;
import property.DoubleProperty;
import property.IntProperty;
import property.Property;

public class AntRule extends Rule
{

	public static final int EMPTY = 0;
	public static final int FOOD = -1;
	public static final int NEST = -2;
	public static final int MIN_ANT = 1;

	public static final Paint FOOD_COLOR = Color.GREEN;
	public static final Color ANT_COLOR = Color.LIGHTSALMON;
	public static final Paint EMPTY_COLOR = Color.WHITE;
	public static final Paint NEST_COLOR = Color.BLUE;

	public static final List<String> DATA_FIELDS = makeDataFields();
	private IntProperty maxAntsPerCell = new IntProperty("max_ants");
	private DoubleProperty evapRate = new DoubleProperty("evap_rate");
	private IntProperty startFoodLevel = new IntProperty("start_food");
	private IntProperty startingAnts = new IntProperty("start_ants");
	private IntProperty maxPher = new IntProperty("max_pher");

	public AntRule(Map<String, String> dataValues)
	{
		for (Property<?> currProperty : this.getProperties()) {
			currProperty.setValue(dataValues.get(currProperty.getName()));
		}
		CellGrid grid = new CellGrid(this.getStartingConfiguration().getValue(), this,
				this.getHeight().getValue().intValue(), this.getHeight().getValue().intValue(),
				this.getCellType().getValue());
		this.setCellGrid(grid);
	}

	@Override
	public void determineNextState(Cell cell)
	{
		if (cell.getState() == NEST) {
			cell.getParameters().set(AntParameter.ANTS_WITH_FOOD, 0);
			cell.setNextState(NEST);
			cell.setNextStateFinalized(true);
			updateAnts(cell);
		} else if (cell.getState() == FOOD) {
			cell.getParameters().set(AntParameter.ANTS_WITH_FOOD, cell.getParameters().get(AntParameter.NUM_ANTS));
			cell.setNextState(FOOD);
			cell.setNextStateFinalized(true);
			updateAnts(cell);
		} else if (cell.getState() >= MIN_ANT) {
			updateAnts(cell);
			cell.setNextState(cell.getParameters().get(AntParameter.NEXT_NUM_ANTS));
		}
		if (cell.getState() != NEST && cell.getState() != FOOD) {
			evapPher(cell);
			cell.setNextState(cell.getParameters().get(AntParameter.NEXT_NUM_ANTS));
		}

	}

	private void updateAnts(Cell cell)
	{
		int numAnts = cell.getParameters().get(AntParameter.NUM_ANTS);
		int numAntsWithFood = numAntsWithFood(cell);
		for (int i = 0; i < numAntsWithFood; i++) {
			returnToNest(cell);
		}
		for (int i = 0; i < numAnts - numAntsWithFood; i++) {
			findFoodSource(cell);
		}
	}

	private void evapPher(Cell cell)
	{
		int currentFoodPher = cell.getParameters().get(AntParameter.FOOD_PHER);
		int currentNestPher = cell.getParameters().get(AntParameter.NEST_PHER);
		evap(cell, currentFoodPher, AntParameter.NEXT_FOOD_PHER);
		evap(cell, currentNestPher, AntParameter.NEXT_NEST_PHER);
	}

	private void evap(Cell cell, int currentLevel, int nextIndex)
	{
		if (currentLevel > 0) {
			double nextLevel = currentLevel - currentLevel * evapRate.getValue();
			if (nextLevel < 0) {
				nextLevel = 0;
			}
			cell.getParameters().set(nextIndex, (int) nextLevel);
		}
	}

	private void findFoodSource(Cell ant)
	{
		Cell moveTo = determineNextCell(ant, AntParameter.FOOD_PHER);
		moveAnt(ant, moveTo);
		dropPher(moveTo, AntParameter.NEST_PHER, AntParameter.NEXT_NEST_PHER);
	}

	private int numAntsWithFood(Cell cell)
	{
		return cell.getParameters().get(AntParameter.ANTS_WITH_FOOD);
	}

	private void returnToNest(Cell ant)
	{
		Cell moveTo = determineNextCell(ant, AntParameter.NEST_PHER);
		moveFoodAnt(ant, moveTo);
		dropPher(moveTo, AntParameter.FOOD_PHER, AntParameter.NEXT_FOOD_PHER);
	}

	private void dropPher(Cell cell, int currentPherIndex, int nextPherIndex)
	{
		int desiredLevel = getDesiredLevel(cell, currentPherIndex);
		if (cell.getParameters().get(currentPherIndex) < desiredLevel) {
			cell.getParameters().set(nextPherIndex, desiredLevel);
		}
	}

	/**
	 * returns desired pheromone level for cell. Calculated by taking the
	 * average pheromone level of surrounding cells
	 * 
	 * @param cell
	 * @return
	 */
	private int getDesiredLevel(Cell cell, int pherIndex)
	{
		int level = 0;
		Map<String, Cell> neighborsMap = cell.getNeighbors();
		for (Cell neighbor : neighborsMap.values()) {
			level += neighbor.getParameters().get(pherIndex);
		}
		level = level / neighborsMap.size();
		return level;
	}

	private void moveFoodAnt(Cell current, Cell next)
	{
		moveAnt(current, next);
		current.getParameters().decrementParameter(AntParameter.NEXT_ANTS_WITH_FOOD, AntParameter.NEXT_ANTS_WITH_FOOD);
		next.getParameters().incrementParameter(AntParameter.NEXT_ANTS_WITH_FOOD, AntParameter.NEXT_ANTS_WITH_FOOD);
	}

	private void moveAnt(Cell current, Cell next)
	{
		current.getParameters().decrementParameter(AntParameter.NEXT_NUM_ANTS, AntParameter.NEXT_NUM_ANTS);
		next.getParameters().incrementParameter(AntParameter.NEXT_NUM_ANTS, AntParameter.NEXT_NUM_ANTS);
	}

	private Cell determineNextCell(Cell ant, int pheromoneType)
	{
		Map<String, Cell> neighborsMap = ant.getNeighbors();
		Collection<Cell> neighbors = neighborsMap.values();
		removeIneligible(neighbors);
		ArrayList<Cell> weightedList = new ArrayList<Cell>();
		for (Cell neighbor : neighbors) {
			for (int i = 0; i <= neighbor.getParameters().get(pheromoneType); i++) {
				weightedList.add(neighbor);
			}
		}
		if (weightedList.size() > 0) {
			int randIndex = randIndex(weightedList.size());
			return weightedList.get(randIndex);
		}
		return ant;

	}

	private Cell determineNextCellNonRandom(Cell ant, int pheromoneType)
	{
		Map<String, Cell> neighborsMap = ant.getNeighbors();
		Collection<Cell> neighbors = neighborsMap.values();
		removeIneligible(neighbors);
		int maxPher = 0;
		for (Cell neighbor : neighbors) {
			if (maxPher < neighbor.getParameters().get(pheromoneType)) {
				maxPher = neighbor.getParameters().get(pheromoneType);
			}
		}
		if (neighbors.size() > 0) {
			for (Cell neighbor : neighbors) {
				if (maxPher < neighbor.getParameters().get(pheromoneType)) {
					return neighbor;
				}
			}
		}
		return ant;

	}

	private void removeIneligible(Collection<Cell> neighbors)
	{
		Collection<Cell> toRemove = new ArrayList<Cell>();
		for (Cell cell : neighbors) {
			if (cell.getParameters().get(AntParameter.NUM_ANTS) >= maxAntsPerCell.getValue()) {
				toRemove.add(cell);
			}
		}
		neighbors.removeAll(toRemove);
	}

	@Override
	public Map<Integer, Paint> makeStateMap()
	{
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(FOOD, FOOD_COLOR);
		stateMap.put(NEST, NEST_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

	@Override
	public Paint getColor(int state)
	{
		if (state == FOOD || state == EMPTY || state == NEST) {
			return super.getColor(state);
		}
		Color antColor = ANT_COLOR;
		for (int i = MIN_ANT; i < state; i++) {
			antColor = antColor.darker();
		}
		return antColor;

	}

	@Override
	public Parameter getParameterType(int initialState)
	{
		return new AntParameter(initialState, startFoodLevel.getValue(), startingAnts.getValue(), maxPher.getValue());
	}

	@Override
	public List<Property<?>> getProperties()
	{
		List<Property<?>> properties = new ArrayList<Property<?>>();
		properties.addAll(this.getGlobalProperties());
		properties.add(maxAntsPerCell);
		properties.add(startFoodLevel);
		properties.add(startingAnts);
		properties.add(maxPher);
		properties.add(evapRate);
		return properties;
	}

	private static List<String> makeDataFields()
	{
		List<String> fields = new ArrayList<String>();
		fields.addAll(GLOBAL_DATA_FIELDS);
		fields.add("max_ants");
		fields.add("evap_rate");
		fields.add("start_food");
		fields.add("start_ants");
		fields.add("max_pher");
		return fields;
	}

}
