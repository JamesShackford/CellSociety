package rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class SugarRule extends Rule {

	public static final int AGENT = 1;
	public static final int EMPTY = 0;
	public static final int MIN_SUGAR_STATE = 2;
	public static final Paint AGENT_COLOR = Color.RED;
	public static final Color SUGAR_COLOR = Color.LIGHTBLUE;
	public static final Paint EMPTY_COLOR = Color.WHITE;
	private SugarGrid sugarGrid;
	private int sugarGrowBackRate;
	private int sugarGrowBackInterval;
	//need a way to determine interval number for sim
	private int intervalNumber;

	public SugarRule(int sugarGrowBackRate, int sugarGrowBackInterval) {
		this.sugarGrowBackRate = sugarGrowBackRate;
		this.sugarGrowBackInterval = sugarGrowBackInterval;
	}

	@Override
	public void determineNextState(Cell cell) {
		if (cell.getState() == AGENT) {
			Collection<Cell> neighbors = getNeighbors(cell.getX(), cell.getY());
			Cell nextCell;
			if (neighbors.size() > 0) {
				nextCell = getNextCell(neighbors, cell.getX(), cell.getY());
			} else {
				nextCell = cell;
			}
			moveAgent(cell, nextCell);
			consumeSugar(nextCell.getX(), nextCell.getY());
			if (sugarGrid.getAgentSugar(nextCell.getX(), nextCell.getY()) <= 0) {
				kill(nextCell);
			}
			nextCell.setNextStateFinalized(true);
		} else {
			if ((intervalNumber % sugarGrowBackInterval) == 0 && !sugarGrid.atMaxSugar(cell.getX(), cell.getY())) {
				growSugarLevel(cell.getX(), cell.getY());
			}
		}

	}

	private void growSugarLevel(int x, int y) {
		int currentSugarLevel = sugarGrid.getSugarLevel(x, y);
		sugarGrid.setSugarLevel(x, y, currentSugarLevel + sugarGrowBackRate);
		if (sugarGrid.getSugarLevel(x, y) > sugarGrid.getMaxSugarLevel(x, y)) {
			sugarGrid.setSugarLevel(x, y, sugarGrid.getMaxSugarLevel(x, y));
		}
	}

	private void kill(Cell cell) {
		cell.setNextState(sugarGrid.getSugarLevel(cell.getX(), cell.getY()));

	}

	private void consumeSugar(int x, int y) {
		int sugarToAdd = sugarGrid.getSugarLevel(x, y);
		sugarToAdd -= sugarGrid.getAgentMetabolism(x, y);
		sugarGrid.setSugarLevel(x, y, 0);
		int currentAgentSugar = sugarGrid.getAgentSugar(x, y);
		int newAgentSugar = currentAgentSugar + sugarToAdd;
		sugarGrid.setAgentSugar(x, y, newAgentSugar);
	}

	private void moveAgent(Cell current, Cell next) {
		int state = current.getState();
		current.setNextState(sugarGrid.getSugarLevel(current.getX(), current.getY()));
		next.setNextState(state);
		moveAgentData(current, next);
	}

	private void moveAgentData(Cell current, Cell next) {
		sugarGrid.setAgentSugar(next.getX(), next.getY(), sugarGrid.getAgentSugar(current.getX(), current.getY()));
		sugarGrid.setAgentMetabolism(next.getX(), next.getY(),
				sugarGrid.getAgentMetabolism(current.getX(), current.getY()));
		sugarGrid.setAgentVision(next.getX(), next.getY(), sugarGrid.getAgentVision(current.getX(), current.getY()));
	}

	private Cell getNextCell(Collection<Cell> neighbors, int currentX, int currentY) {
		int maxSugar = findMaxSugar(neighbors);
		removeLowSugarNeighbors(neighbors, maxSugar);
		return minDistNeighbor(neighbors, currentX, currentY);

	}

	private Cell minDistNeighbor(Collection<Cell> neighbors, int currentX, int currentY) {
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

	private double distance(Cell cell, double x, double y) {
		return Math.sqrt((cell.getX() - x) * (cell.getX() - x) + (cell.getY() - y) * (cell.getY() - y));
	}

	private void removeLowSugarNeighbors(Collection<Cell> neighbors, int maxSugar) {
		for (Cell cell : neighbors) {
			if (sugarGrid.getSugarLevel(cell.getX(), cell.getY()) < maxSugar) {
				neighbors.remove(cell);
			}
		}
	}

	private int findMaxSugar(Collection<Cell> neighbors) {
		int max = Integer.MIN_VALUE;
		for (Cell cell : neighbors) {
			if (sugarGrid.getSugarLevel(cell.getX(), cell.getY()) > max) {
				max = sugarGrid.getSugarLevel(cell.getX(), cell.getY());
			}
		}
		return max;
	}

	private Collection<Cell> getNeighbors(int x, int y) {
		Map<String, Cell> neighbors = cellGrid.getNeighborsSides(x, y);
		removeIneligible(neighbors);
		for (String position : neighbors.keySet()) {
			Cell neighbor = neighbors.get(position);
			for (int i = 0; i < sugarGrid.getAgentVision(x, y); i++) {
				Map<String, Cell> nextNeighbors = cellGrid.getNeighbors(neighbor.getX(), neighbor.getY());
				if (nextNeighbors.get(position) != null) {
					neighbors.put(position, nextNeighbors.get(position));
					neighbor = nextNeighbors.get(position);
				}
			}
		}
		return neighbors.values();
	}

	private void removeIneligible(Map<String, Cell> cellMap) {
		for (String key : cellMap.keySet()) {
			if (cellMap.get(key) == null || cellMap.get(key).getState() == AGENT
					|| cellMap.get(key).nextStateFinalized()) {
				cellMap.remove(key);
			}
		}
	}

	@Override
	public void setCellGrid(CellGrid cellGrid) {
		this.cellGrid = cellGrid;
		this.sugarGrid = new SugarGrid(cellGrid.getWidth(), cellGrid.getHeight());
	}

	@Override
	public Map<Integer, Paint> makeStateMap() {
		Map<Integer, Paint> stateMap = new HashMap<Integer, Paint>();
		stateMap.put(AGENT, AGENT_COLOR);
		stateMap.put(EMPTY, EMPTY_COLOR);
		return stateMap;
	}

	@Override
	public Paint getColor(int state) {
		if (state == AGENT || state == EMPTY) {
			return super.getColor(state);
		} 
		Color sugarColor = SUGAR_COLOR;
		for (int i = MIN_SUGAR_STATE; i < state; i++) {
			sugarColor = sugarColor.darker();
		}
		return sugarColor;

	}

}