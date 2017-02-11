package display;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GraphTab extends AbstractTab
{
	private final String TAB_NAME = "Graph";
	Map<Paint, List<Integer>> stateNumberMap;
	Tab tab;
	int time;

	public GraphTab()
	{
		this.tab = new Tab();
		time = -1;
		tab.setText(TAB_NAME);
		stateNumberMap = new HashMap<Paint, List<Integer>>();
	}

	@Override
	public Tab updateTab(Collection<CellGrid> grids)
	{
		Group graphs = new Group();

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Number of Cells");
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Number of Cells vs. Time");
		updateTime();

		for (CellGrid grid : grids) {
			lineChart.getData().addAll(makeGraph(grid));
		}
		int i = 0;
		for (Paint color : stateNumberMap.keySet()) {
			XYChart.Series<Number, Number> series = lineChart.getData().get(i);
			series.getNode().setStyle(String.format("-fx-stroke: #%s;", color.toString().replace("0x", "")));
			i++;
		}

		// for (XYChart.Series<Number, Number> series : lineChart.getData()) {
		// series.getNode().setStyle(String.format("-fx-stroke: #%s;"), );
		// }
		tab.setContent(lineChart);
		return tab;
	}

	public void updateTime()
	{
		time++;
		for (List<Integer> stateNumberList : stateNumberMap.values()) {
			stateNumberList.add(0);
		}
	}

	private List<XYChart.Series<Number, Number>> makeGraph(CellGrid grid)
	{
		List<XYChart.Series<Number, Number>> allSeries = new ArrayList<XYChart.Series<Number, Number>>();
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				Cell currCell = grid.getCell(i, j);
				int currState = currCell.getState();
				//Paint stateColor = currCell.getRule().getStateMap().get(currState);
				Paint stateColor = currCell.getRule().getColor(currState);
				if (stateNumberMap.get(stateColor) == null) {
					List<Integer> stateNumberList = new ArrayList<Integer>();
					for (int k = 0; k < time; k++) {
						stateNumberList.add(0);
					}
					stateNumberList.add(1);
					stateNumberMap.put(stateColor, stateNumberList);
				} else {
					List<Integer> currStates = stateNumberMap.get(stateColor);
					stateNumberMap.put(stateColor, incrementList(currStates, currStates.size() - 1));
				}
			}
		}
		for (Paint color : stateNumberMap.keySet()) {
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			for (int t = 0; t < stateNumberMap.get(color).size(); t++) {
				XYChart.Data<Number, Number> currData = new XYChart.Data<Number, Number>(t,
						stateNumberMap.get(color).get(t));
				Rectangle dataNode = new Rectangle(5, 5);
				dataNode.setFill(color);
				currData.setNode(dataNode);
				series.getData().add(currData);
			}
			allSeries.add(series);
		}
		return allSeries;
	}

	private List<Integer> incrementList(List<Integer> list, int index)
	{
		List<Integer> updatedList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			updatedList.add(i, list.get(i));
		}
		updatedList.set(index, list.get(index) + 1);
		return updatedList;
	}

}
