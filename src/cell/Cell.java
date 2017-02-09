package cell;

import java.util.Map;

import cellgrid.CellGrid;
import javafx.scene.Node;
import rule.Rule;

public abstract class Cell
{

	private boolean nextStateFinalized;
	private int state;
	private int nextState;
	private Rule myRule;
	private int xPos;
	private int yPos;

	public Cell(Rule rule, int intialState, int x, int y)
	{
		this.myRule = rule;
		this.state = intialState;
		this.nextState = state;
		this.xPos = x;
		this.yPos = y;
	}

	public abstract Node getImage();

	public CellGrid getCellGrid()
	{
		if (this.getRule() != null) {
			return this.getRule().getCellGrid();
		}
		return null;
	}

	public int getNextState()
	{
		return nextState;
	}

	public void updateCell()
	{
		this.setState(nextState);
		nextStateFinalized = false;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int newState)
	{
		state = newState;
	}

	public void setNextState(int newState)
	{
		nextState = newState;
	}

	public int getX()
	{
		return xPos;
	}

	public int getY()
	{
		return yPos;
	}

	public Rule getRule()
	{
		return myRule;
	}

	public boolean nextStateFinalized()
	{
		return nextStateFinalized;
	}

	public void setNextStateFinalized(boolean finalized)
	{
		nextStateFinalized = finalized;
	}
	public abstract Map<String, Cell> getNeighbors();
}
