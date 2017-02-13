package display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cellsociety.CellSociety;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GraphTab extends AbstractTab
{
	private final String TAB_NAME = "Graph";
	private int numCols;
	private int numRows;
	private Tab tab;

	public GraphTab(List<CellSociety> cellSocieties)
	{
		super(cellSocieties);
		this.tab = new Tab();
		tab.setText(TAB_NAME);
	}

	@Override
	public Tab updateTab()
	{
		updateTime();
		numCols = (int) Math.ceil(Math.sqrt(this.getSocieties().size()));
		numRows = (int) Math.ceil(Math.sqrt(this.getSocieties().size()));
		// for (Paint color : stateNumberMap.keySet()) {
		// XYChart.Series<Number, Number> series = lineChart.getData().get(i);
		// series.getNode().setStyle(String.format("-fx-stroke: #%s;",
		// color.toString().replace("0x", "")));
		// i++;
		// }

		// for (XYChart.Series<Number, Number> series : lineChart.getData()) {
		// series.getNode().setStyle(String.format("-fx-stroke: #%s;"), );
		// }
		tab.setContent(makeGraphs());

		return tab;
	}

	public BorderPane makeGraphs()
	{
		BorderPane pane = new BorderPane();
		GridPane graphs = new GridPane();
		for (int i = 0; i < this.getSocieties().size(); i++) {
			int rowNumber = (int) Math.floor(i / numRows);
			int colNumber = (i % numCols);
			graphs.add(makeGraph(this.getSocieties().get(i)), colNumber, rowNumber);
			// tab.setContent(makeGraph(society));
		}
		pane.setCenter(graphs);
		return pane;
	}

	public void updateTime()
	{
		for (CellSociety cellSociety : this.getSocieties()) {
			cellSociety.updateTimes();
		}
	}

	private LineChart<Number, Number> makeGraph(CellSociety currSociety)
	{
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		yAxis.setLabel("Number of Cells");
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Number of Cells vs. Time");

		List<XYChart.Series<Number, Number>> allSeries = new ArrayList<XYChart.Series<Number, Number>>();
		Map<Paint, List<Integer>> stateNumberMap = currSociety.getNumberVsTimeMap();
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

		lineChart.getData().addAll(allSeries);

		int i = 0;
		for (Paint color : stateNumberMap.keySet()) {
			XYChart.Series<Number, Number> series = lineChart.getData().get(i);
			series.getNode().setStyle(String.format("-fx-stroke: #%s;", color.toString().replace("0x", "")));
			i++;
		}

		return lineChart;
	}

}
