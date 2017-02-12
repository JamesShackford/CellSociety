package cellsociety;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cellgrid.CellGrid;
import display.Display;
import rule.Rule;

public class CellSociety
{
	private Map<Rule, CellGrid> grids;

	public CellSociety(Rule rule)
	{
		grids = new HashMap<Rule, CellGrid>();
		grids.put(rule, rule.getCellGrid());
	}

	public void addRule(Rule rule)
	{
		grids.put(rule, rule.getCellGrid());
	}

	public int getRandomState(Rule rule)
	{
		int numPossibleStates = rule.getStateMap().size();
		Random rand = new Random();
		int randStateNumber = rand.nextInt(numPossibleStates);
		Integer[] possibleStates = new Integer[numPossibleStates];
		possibleStates = rule.getStateMap().keySet().toArray(possibleStates);
		return possibleStates[randStateNumber];
	}

	public void step(Display display)
	{
		Collection<CellGrid> gridCollection = grids.values();

		for (CellGrid cellGrid : gridCollection) {
			cellGrid.updateCellGrid();
		}

		display.displayGrids(gridCollection);
	}

}
