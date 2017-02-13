package cellsociety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Paint;
import rule.Rule;

public class CellSociety
{
	private Rule rule;
	private Map<Paint, List<Integer>> numberVsTimeMap;
	private int time;

	public CellSociety(Rule rule)
	{
		this.rule = rule;
		numberVsTimeMap = new HashMap<Paint, List<Integer>>();
		time = -1;
	}

	public CellGrid getCellGrid()
	{
		return rule.getCellGrid();
	}

	public void setRule(Rule rule)
	{
		this.rule = rule;
		this.numberVsTimeMap = new HashMap<Paint, List<Integer>>();
		time = 0;
	}

	public Rule getRule()
	{
		return rule;
	}

	public void updateTimes()
	{
		time++;
		CellGrid myCellGrid = this.getCellGrid();
		for (List<Integer> timeList : numberVsTimeMap.values()) {
			timeList.add(0);
		}
		for (int i = 0; i < myCellGrid.getHeight(); i++) {
			for (int j = 0; j < myCellGrid.getWidth(); j++) {
				Cell currCell = myCellGrid.getCell(i, j);
				int currState = currCell.getState();
				// Paint stateColor =
				// currCell.getRule().getStateMap().get(currState);
				Paint stateColor = currCell.getRule().getColor(currState);
				if (numberVsTimeMap.get(stateColor) == null) {
					List<Integer> stateNumberList = new ArrayList<Integer>();
					for (int k = 0; k < time; k++) {
						stateNumberList.add(0);
					}
					stateNumberList.add(1);
					numberVsTimeMap.put(stateColor, stateNumberList);
				} else {
					List<Integer> currStates = numberVsTimeMap.get(stateColor);
					currStates.set(currStates.size() - 1, currStates.get(currStates.size() - 1) + 1);
					numberVsTimeMap.put(stateColor, currStates);
				}
			}
		}
	}

	public Map<Paint, List<Integer>> getNumberVsTimeMap()
	{
		return numberVsTimeMap;
	}

}
