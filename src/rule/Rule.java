package rule;

import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Paint;
import parameters.Parameter;

public abstract class Rule
{
	CellGrid cellGrid;
	private Map<Integer, Paint> stateColorMap;

	public Rule()
	{
		stateColorMap = makeStateMap();
	}

	public Rule(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
		stateColorMap = makeStateMap();
	}

	public Paint getColor(int state){
		return stateColorMap.get(state);
	}
	

	public abstract void determineNextState(Cell cell);

	public abstract Map<Integer, Paint> makeStateMap();

	public CellGrid getCellGrid()
	{
		return cellGrid;
	}

	public void setCellGrid(CellGrid cellGrid)
	{
		this.cellGrid = cellGrid;
	}
	
	public Map<Integer, Paint> getStateMap(){
		return stateColorMap;
	}
	
	public void duplicateStats(Cell current, Cell newCell){
		newCell.getParameters().replaceParameters(current.getParameters().getParameterClone());
	}
	
	public Parameter getParameterType(int intialState){
		return new Parameter();
	}

}
