package display;

import cellsociety.CellSociety;
import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import xml.XMLParser;

public class SimulationGroup
{
	public static int BUTTON_ROOM = 100;

	private CellSociety cellSociety;
	private ComboBox<String> selectionBox;
	private int societyNumber;
	private int totalSocietyNumber;
	private double width;
	private double height;
	private int numCols;
	private int numRows;

	public SimulationGroup(CellSociety cellSociety, Animation animation, int societyNumber, int totalSocietyNumber)
	{
		this.cellSociety = cellSociety;
		this.societyNumber = societyNumber;
		this.totalSocietyNumber = totalSocietyNumber;
		numCols = (int) Math.ceil(Math.sqrt(totalSocietyNumber));
		numRows = (int) Math.ceil(Math.sqrt(totalSocietyNumber));
		width = getXScaleFactor() * SimulationTab.SIMULATIONS_WIDTH - BUTTON_ROOM;
		height = getYScaleFactor() * SimulationTab.SIMULATIONS_HEIGHT - BUTTON_ROOM;
		selectionBox = makeSelectionBox(animation, cellSociety);
	}

	public Group getSimulationImage()
	{
		Group simulationGroup = new Group();
		simulationGroup.getChildren().add(makeGridImage());
		simulationGroup.getChildren().add(selectionBox);
		return simulationGroup;
	}

	private Group makeGridImage()
	{
		Group group = new Group();

		for (int i = 0; i < cellSociety.getCellGrid().getHeight(); i++) {
			for (int j = 0; j < cellSociety.getCellGrid().getWidth(); j++) {
				Node addedCell = cellSociety.getCellGrid().getCell(i, j).getImage();
				group.getChildren().add(addedCell);
			}
		}
		group.setLayoutX(getXPosition());
		group.setLayoutY(getYPosition());
		group.setScaleX(group.getScaleX() * getXScaleFactor());
		group.setScaleY(group.getScaleY() * getYScaleFactor());
		return group;
	}

	private double getXScaleFactor()
	{
		return (1.0) / numCols - ((double) BUTTON_ROOM / (double) SimulationTab.SIMULATIONS_WIDTH);
	}

	private double getYScaleFactor()
	{
		return (1.0) / numRows - ((double) BUTTON_ROOM / (double) SimulationTab.SIMULATIONS_HEIGHT);
	}

	private double getXPosition()
	{
		return (societyNumber % numCols) * SimulationTab.SIMULATIONS_WIDTH / numCols;
	}

	private double getYPosition()
	{
		return Math.floor(societyNumber / numRows) * SimulationTab.SIMULATIONS_HEIGHT / numRows;
	}

	private ComboBox<String> makeSelectionBox(Animation animation, CellSociety cellSociety)
	{
		ObservableList<String> xmlFiles = FXCollections.observableArrayList(XMLParser.RULE_MAP.keySet());
		ComboBox<String> selectionBox = new ComboBox<String>(xmlFiles);
		selectionBox.setLayoutX(getXPosition() + width);
		selectionBox.setLayoutY(getYPosition() + height);
		selectionBox.setOnMousePressed((event) -> {
			animation.pause();
		});
		selectionBox.valueProperty().addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldXML, String newXML)
			{
				XMLParser parser = new XMLParser();
				cellSociety.setRule(parser.getRule(XMLParser.FILE_MAP.get(newXML)));
				animation.play();
			}

		});
		return selectionBox;
	}

}
