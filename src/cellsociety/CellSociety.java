package cellsociety;

import cellgrid.CellGrid;
import rule.Rule;

public class CellSociety
{
	private Rule rule;

	public CellSociety(Rule rule)
	{
		this.rule = rule;
	}

	public CellGrid getCellGrid()
	{
		return rule.getCellGrid();
	}

	public void setRule(Rule rule)
	{
		this.rule = rule;
	}

}
