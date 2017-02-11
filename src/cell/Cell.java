package cell;

import java.util.HashMap;
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
	
	public boolean isValid(int i, int j){
		return (i >= 0 && i < this.getCellGrid().getHeight()) && (j >= 0 && j < this.getCellGrid().getWidth());
	}
	
	public Map<String, Cell> getAround() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		for (int i = this.getX() - 1; i <= this.getX() + 1; i++){
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++){
				if (!(i == this.getX() && j == this.getY()) && isValid(i,j)){
					neighbors.put(i + " " + j, this.getCellGrid().getCell(i, j));
				}
			}
		}
		return neighbors;
	}
	
	public Map<String, Cell> getSides() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (this.getY() + 1 < this.getCellGrid().getWidth()) {
			neighbors.put("Bottom", this.getCellGrid().getCell(this.getX(), this.getY() + 1));
		}
		if (this.getY() - 1 >= 0) {
			neighbors.put("Top", this.getCellGrid().getCell(this.getX(), this.getY() - 1));
		}
		if (this.getX() - 1 >= 0) {
			neighbors.put("Center Left", this.getCellGrid().getCell(this.getX() - 1, this.getY()));
		}
		if (this.getX() + 1 < this.getCellGrid().getHeight()) {
			neighbors.put("Center Right", this.getCellGrid().getCell(this.getX() + 1, this.getY()));
		}
		return neighbors;
	}
	
	public Map<String, Cell> getAllAround() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		for (int i = this.getX() - 1; i <= this.getX() + 1; i++){
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++){
				if (!(i == this.getX() && j == this.getY())){
					neighbors.put(i + " " + j, this.getCellGrid().getCell(Math.floorMod(i, this.getCellGrid().getHeight()), Math.floorMod(j, this.getCellGrid().getWidth())));				}
			}
		}
		return neighbors;
	}
	
	public abstract Map<String, Cell> getNeighbors();
	public abstract Map<String, Cell> getNeighborsSides();
	public abstract Map<String, Cell> getNeighborsWrap();
}
