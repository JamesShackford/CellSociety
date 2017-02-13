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
	public static int BUTTON_ROOM = 25;

	private CellSociety cellSociety;
	private ComboBox<String> selectionBox;
	private int societyNumber;
	private double width;
	private double height;
	private int numCols;
	private int numRows;

	public SimulationGroup(CellSociety cellSociety, Animation animation, int societyNumber, int totalSocietyNumber)
	{
		this.cellSociety = cellSociety;
		this.societyNumber = societyNumber;
		numCols = (int) Math.ceil(Math.sqrt(totalSocietyNumber));
		numRows = (int) Math.ceil(Math.sqrt(totalSocietyNumber));
		width = getXScaleFactor() * SimulationTab.SIMULATIONS_WIDTH;
		height = getYScaleFactor() * SimulationTab.SIMULATIONS_HEIGHT;
		selectionBox = makeSelectionBox(animation, cellSociety);
	}

	public Group getSimulationImage()
	{
		// Group simulationGroup = new Group();
		// simulationGroup.getChildren().add(makeGridImage());
		// simulationGroup.getChildren().add(selectionBox);
		// return simulationGroup;
		return makeGridImage();
	}

	private Group makeGridImage()
	{
		Group societyGroup = new Group();

		for (int i = 0; i < cellSociety.getCellGrid().getHeight(); i++) {
			for (int j = 0; j < cellSociety.getCellGrid().getWidth(); j++) {
				Node addedCell = cellSociety.getCellGrid().getCell(i, j).getImage(height, width);
				societyGroup.getChildren().add(addedCell);
			}
		}
		// societyGroup.setLayoutX(getXPosition());
		// societyGroup.setLayoutY(getYPosition());
		// societyGroup.getChildren().add(selectionBox);
		// societyGroup.setScaleX(societyGroup.getScaleX() * getXScaleFactor());
		// societyGroup.setScaleY(societyGroup.getScaleY() * getYScaleFactor());

		societyGroup.getChildren().add(selectionBox);
		return societyGroup;
	}

	private double getXScaleFactor()
	{
		return (1.0) / numCols - ((double) BUTTON_ROOM / (double) SimulationTab.SIMULATIONS_WIDTH);
	}

	private double getYScaleFactor()
	{
		return (1.0) / numRows - ((double) BUTTON_ROOM / (double) SimulationTab.SIMULATIONS_HEIGHT);
	}

	public int getRowNumber()
	{
		return (int) Math.floor(societyNumber / numRows);
	}

	public int getColNumber()
	{
		return (societyNumber % numCols);
	}

	private ComboBox<String> makeSelectionBox(Animation animation, CellSociety cellSociety)
	{
		ObservableList<String> xmlFiles = FXCollections.observableArrayList(XMLParser.RULE_MAP.keySet());
		ComboBox<String> selectionBox = new ComboBox<String>(xmlFiles);
		// System.out.println(getXPosition() + width);
		// System.out.println(getYPosition() + height);
		selectionBox.setLayoutX(width / 2);
		selectionBox.setLayoutY(height);
		selectionBox.setValue(xmlFiles.get(0));
		selectionBox.setOnMousePressed((event) -> {
			animation.stop();
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
