package cell;

import Rule.Rule;

public class Cell
{

	private int state;
	private Rule myRule;
	private int xPos;
	private int yPos;

	public Cell(Rule rule)
	{
		myRule = rule;
	}

	public Cell(Rule rule, int intialState)
	{
		myRule = rule;
		state = intialState;
	}

	public void updateCell()
	{
		this.setState(myRule.getNextState(this));
	}

	public int getState()
	{
		return state;
	}

	public void setState(int s)
	{
		state = s;
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

}
