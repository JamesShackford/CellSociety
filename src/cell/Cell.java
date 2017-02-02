package cell;

import rule.Rule;

public class Cell
{

	private int state;
	private Rule myRule;
	private int xPos;
	private int yPos;

	public Cell(Rule rule, int x, int y)
	{
		myRule = rule;
		xPos = x;
		yPos = y;
	}

	public Cell(Rule rule, int intialState, int x, int y)
	{
		myRule = rule;
		state = intialState;
		xPos = x;
		yPos = y;
	}

	public void updateCell()
	{
		System.out.println(this.getState() == myRule.getNextState(this));
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
