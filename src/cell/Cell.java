package cell;

import cellgrid.CellGrid;
import javafx.scene.Node;
import parameters.Parameter;
import rule.Rule;

/**
 * Abstract class for Cell, provides essential methods and parameters
 * for a Cell in the simulation grid
 * 
 * All types of cells to be represented in a simulation must extend this class
 * 
 * @author Derek, Jimmy
 *
 */
public abstract class Cell
{

	private boolean nextStateFinalized;
	private int state;
	private int nextState;
	private Rule myRule;
	private int xPos;
	private int yPos;
	private Parameter myParameters;

	public Cell(Rule rule, int intialState, int x, int y)
	{
		this.myRule = rule;
		this.state = intialState;
		this.nextState = state;
		this.xPos = x;
		this.yPos = y;
		myParameters = rule.getParameterType(intialState);
	}

	public abstract Node getImage();

	/**
	 * @return   CellGrid that this cell is in
	 */
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
		myParameters.updateParameters();
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

	
	public Parameter getParameters(){
		return myParameters;
	}
}
