package cellsociety;

import java.util.Random;

import cellgrid.CellGrid;
import display.Display;
import rule.Rule;

public class CellSociety
{

	private CellGrid cellGrid;
	private Rule rule;

	public CellSociety(Rule rule)
	{
		this.rule = rule;
		cellGrid = rule.getCellGrid();

		// cellGrid.getGrid()[3][3] = new Cell(rule, 1, 3, 3);
		// cellGrid.getGrid()[4][3] = new Cell(rule, 1, 4, 3);
		// cellGrid.getGrid()[3][4] = new Cell(rule, 1, 3, 4);
		// cellGrid.getGrid()[4][4] = new Cell(rule, 1, 4, 4);
	}

	public int getRandomState()
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
		cellGrid.updateCellGrid();
		display.displayGrid(cellGrid);
	}

}
