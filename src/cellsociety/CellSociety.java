package cellsociety;

import java.util.Random;

import cellgrid.CellGrid;
import rule.Rule;

public class CellSociety
{
	private Rule rule;

	public CellSociety(Rule rule)
	{
		this.rule = rule;
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

	// public void step(Display display)
	// {
	// display.displayGrids(this);
	// }

	public CellGrid getCellGrid()
	{
		return rule.getCellGrid();
	}

	public void setRule(Rule rule)
	{
		this.rule = rule;
	}

}
