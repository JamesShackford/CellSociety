package display;

import java.util.ArrayList;
import java.util.List;

import cellsociety.CellSociety;
import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import rule.Rule;
import xml.XMLParser;

public class SimulationTab extends AbstractTab
{
	public static final int SIMULATIONS_HEIGHT = Display.DISPLAY_HEIGHT - 200;
	public static final int SIMULATIONS_WIDTH = Display.DISPLAY_WIDTH - 100;

	private final String TAB_NAME = "Simulation";
	private Animation animation;
	private Button pauseButton = makePauseButton();
	private Button startButton = makeStartButton();
	private Button stepButton = makeStepButton();
	private VBox speedSlider = makeSpeedSlider();
	private VBox numSimulationsSlider = makeNumSimulationsSlider();
	private List<SimulationGroup> cellGroups;
	private List<CellSociety> cellSocieties;
	private double defaultAnimationRate;

	public SimulationTab(Animation animation, List<CellSociety> cellSocieties)
	{
		super(cellSocieties);
		this.setTab(new Tab());
		this.getTab().setText(TAB_NAME);
		this.animation = animation;
		defaultAnimationRate = animation.getCurrentRate();
		this.cellSocieties = cellSocieties;
		cellGroups = makeCellGroups(animation, cellSocieties);
	}

	private List<SimulationGroup> makeCellGroups(Animation animation, List<CellSociety> societies)
	{
		List<SimulationGroup> simulationGroups = new ArrayList<SimulationGroup>();
		for (int i = 0; i < societies.size(); i++) {
			simulationGroups.add(new SimulationGroup(societies.get(i), animation, i, societies.size()));
		}
		return simulationGroups;
	}

	@Override
	public Tab updateTab()
	{
		this.getTab().setContent(makeSimulation());
		return this.getTab();
	}

	private BorderPane makeSimulation()
	{
		BorderPane simulation = new BorderPane();
		GridPane gridSimulations = new GridPane();
		for (int i = 0; i < cellGroups.size(); i++) {
			Group simulationImage = cellGroups.get(i).getSimulationImage();
			gridSimulations.add(simulationImage, cellGroups.get(i).getColNumber(), cellGroups.get(i).getRowNumber());
		}
		simulation.setCenter(gridSimulations);
		simulation.setBottom(makeButtonPane());
		gridSimulations.setAlignment(Pos.CENTER);

		return simulation;
	}

	private HBox makeButtonPane()
	{
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(pauseButton, startButton, stepButton, speedSlider, numSimulationsSlider);
		buttonBox.setAlignment(Pos.CENTER);
		return buttonBox;
	}

	private Button makePauseButton()
	{
		Button button = new Button();
		button.setText("Pause");
		button.setOnAction((event) -> {
			animation.pause();
		});
		// button.setLayoutX(xPos);
		// button.setLayoutY(yPos);
		return button;
	}

	private Button makeStartButton()
	{
		Button button = new Button();
		button.setText("Start");
		button.setOnAction((event) -> {
			animation.play();
		});
		// button.setLayoutX(xPos);
		// button.setLayoutY(yPos);
		return button;
	}

	private Button makeStepButton()
	{
		Button button = new Button();
		button.setText("Step");
		button.setOnAction((event) -> {
			animation.play();
			for (CellSociety currSociety : cellSocieties) {
				currSociety.getCellGrid().updateCellGrid();
			}
			updateTab();
			animation.pause();
		});
		// button.setLayoutX(xPos);
		// button.setLayoutY(yPos);
		return button;
	}

	private VBox makeSpeedSlider()
	{
		VBox sliderBox = new VBox();

		Label sliderLabel = new Label();
		sliderLabel.setText("Speed:");

		Slider slider = new Slider();
		slider.setMin(0.1);
		slider.setMax(3);
		slider.setValue(1);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		// slider.setLayoutX(xPos);
		// slider.setLayoutY(yPos);
		slider.setOnDragDetected((event) -> {
			animation.pause();
		});
		slider.setOnMouseReleased((event) -> {
			animation.play();
			animation.setRate(defaultAnimationRate * slider.getValue());
		});

		sliderBox.getChildren().addAll(sliderLabel, slider);

		return sliderBox;
	}

	private VBox makeNumSimulationsSlider()
	{
		VBox sliderBox = new VBox();

		Label sliderLabel = new Label();
		sliderLabel.setText("Number of Simulations:");

		Slider slider = new Slider();
		slider.setMin(1);
		slider.setMax(4);
		slider.setValue(1);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		// slider.setLayoutX(xPos);
		// slider.setLayoutY(yPos);
		slider.valueProperty().addListener((obs, oldval, newVal) -> slider.setValue(newVal.intValue()));
		slider.setOnDragDetected((event) -> {
			animation.pause();
		});
		slider.setOnMouseReleased((event) -> {

			while (cellSocieties.size() < (int) slider.getValue()) {
				String defaultRuleName = (String) XMLParser.RULE_MAP.keySet().toArray()[0];
				Rule rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(defaultRuleName));
				cellSocieties.add(new CellSociety(rule));
			}

			while (cellSocieties.size() > (int) slider.getValue()) {
				cellSocieties.remove(cellSocieties.size() - 1);
			}

			cellGroups = makeCellGroups(animation, cellSocieties);

			animation.play();
		});

		sliderBox.getChildren().addAll(sliderLabel, slider);

		return sliderBox;
	}

}
