package display;

import java.util.ArrayList;
import java.util.List;

import cellsociety.CellSociety;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import rule.Rule;
import xml.XMLParser;

public class SimulationTab extends AbstractTab
{
	public static final int SIMULATIONS_HEIGHT = Display.DISPLAY_HEIGHT - 200;
	public static final int SIMULATIONS_WIDTH = Display.DISPLAY_WIDTH - 100;

	private final String TAB_NAME = "Simulation";
	private Animation animation;
	private Button pauseButton = makePauseButton(Display.DISPLAY_WIDTH / 2, Display.DISPLAY_HEIGHT - 150);
	private Button startButton = makeStartButton(Display.DISPLAY_WIDTH / 2, Display.DISPLAY_HEIGHT - 125);
	private Button stepButton = makeStepButton(Display.DISPLAY_WIDTH / 2, Display.DISPLAY_HEIGHT - 100);
	private Slider speedSlider = makeSpeedSlider(Display.DISPLAY_WIDTH / 2, Display.DISPLAY_HEIGHT - 75);
	private Slider numSimulationsSlider = makeNumSimulationsSlider(Display.DISPLAY_WIDTH - 100,
			Display.DISPLAY_HEIGHT - 50);
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

	private Group makeSimulation()
	{
		Group simulation = new Group();
		for (SimulationGroup simulationGroup : cellGroups) {
			simulation.getChildren().add(simulationGroup.getSimulationImage());
		}
		simulation.getChildren().add(pauseButton);
		simulation.getChildren().add(startButton);
		simulation.getChildren().add(stepButton);
		simulation.getChildren().add(speedSlider);
		simulation.getChildren().add(numSimulationsSlider);
		// simulation.setScaleX(simulation.getScaleX() * 0.5);
		// simulation.setScaleY(simulation.getScaleY() * 0.5);
		return simulation;
	}

	private Button makePauseButton(int xPos, int yPos)
	{
		Button button = new Button();
		button.setText("Pause");
		button.setOnAction((event) -> {
			animation.pause();
		});
		button.setLayoutX(xPos);
		button.setLayoutY(yPos);
		return button;
	}

	private Button makeStartButton(int xPos, int yPos)
	{
		Button button = new Button();
		button.setText("Start");
		button.setOnAction((event) -> {
			animation.play();
		});
		button.setLayoutX(xPos);
		button.setLayoutY(yPos);
		return button;
	}

	private Button makeStepButton(int xPos, int yPos)
	{
		Button button = new Button();
		button.setText("Step");
		button.setOnAction((event) -> {
			animation.pause();
			animation.jumpTo(animation.getCycleDuration());
		});
		button.setLayoutX(xPos);
		button.setLayoutY(yPos);
		return button;
	}

	private Slider makeSpeedSlider(int xPos, int yPos)
	{
		Slider slider = new Slider();
		slider.setMin(0.1);
		slider.setMax(3);
		slider.setValue(1);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.setLayoutX(xPos);
		slider.setLayoutY(yPos);
		slider.setOnDragDetected((event) -> {
			animation.pause();
		});
		slider.setOnMouseReleased((event) -> {
			animation.play();
			animation.setRate(defaultAnimationRate * slider.getValue());
		});
		return slider;
	}

	private Slider makeNumSimulationsSlider(int xPos, int yPos)
	{
		Slider slider = new Slider();
		slider.setMin(1);
		slider.setMax(4);
		slider.setValue(1);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.setLayoutX(xPos);
		slider.setLayoutY(yPos);
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
		return slider;
	}

}
