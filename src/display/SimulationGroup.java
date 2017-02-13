package display;

import cellsociety.CellSociety;
import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import property.Property;
import xml.XMLParser;

public class SimulationGroup
{
	public static int BUTTON_ROOM = 125;

	private CellSociety cellSociety;
	private ComboBox<String> selectionBox;
	private VBox dynamicUpdaters;
	private int societyNumber;
	private Button saveButton = makeSaveButton();
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
		dynamicUpdaters = getDynamicUpdaters(animation);
	}

	public BorderPane getSimulationImage()
	{
		return makeGridImage();
	}

	private BorderPane makeGridImage()
	{
		BorderPane borderPane = new BorderPane();

		Group societyGroup = new Group();

		for (int i = 0; i < cellSociety.getCellGrid().getHeight(); i++) {
			for (int j = 0; j < cellSociety.getCellGrid().getWidth(); j++) {
				Node addedCell = cellSociety.getCellGrid().getCell(i, j).getImage(height, width);
				societyGroup.getChildren().add(addedCell);
			}
		}
		borderPane.setCenter(societyGroup);
		borderPane.setBottom(dynamicUpdaters);
		return borderPane;
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

	public VBox getDynamicUpdaters(Animation animation)
	{
		VBox vbox = new VBox();
		for (Property<?> property : cellSociety.getRule().getProperties()) {
			Node dynamicUpdater = property.makeDynamicUpdater();
			if (dynamicUpdater != null) {
				dynamicUpdater.setOnMousePressed((event) -> {
					animation.stop();
				});
				dynamicUpdater.setOnMouseReleased((event) -> {
					animation.play();
				});
				vbox.getChildren().add(dynamicUpdater);
			}
		}
		vbox.getChildren().add(selectionBox);
		vbox.getChildren().add(saveButton);
		vbox.setAlignment(Pos.CENTER);
		return vbox;
	}

	private ComboBox<String> makeSelectionBox(Animation animation, CellSociety cellSociety)
	{
		ObservableList<String> xmlFiles = FXCollections.observableArrayList(XMLParser.RULE_MAP.keySet());
		ComboBox<String> selectionBox = new ComboBox<String>(xmlFiles);
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
	
	private Button makeSaveButton(){
		Button button = new Button();
		button.setText("Save");
		button.setOnAction((event) -> {
			cellSociety.getRule().saveConfiguration();
		});
		button.setLayoutX(width/4);
		button.setLayoutY(height);
		return button;
	}

}
