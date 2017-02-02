package cell;

import Rule.Rule;

public class Cell {

	private boolean isEdge;
	private boolean isCorner;
	private int state;
	private Rule myRule;
	private int xPos;
	private int yPos;

	public Cell(Rule rule) {
		myRule = rule;
	}

	public Cell(Rule rule, int intialState) {
		myRule = rule;
		state = intialState;
	}

	public void updateCell() {
		this.setState(myRule.getNextState(this));
	}

	public int getState() {
		return state;
	}

	public void setState(int s) {
		state = s;
	}

	public boolean getIsEdge() {
		return isEdge;
	}

	public void setEdge(boolean isEdge) {
		this.isEdge = isEdge;
	}

	public boolean getIsCorner() {
		return isCorner;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

}
