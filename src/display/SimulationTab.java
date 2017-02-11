package display;

import java.util.Collection;

import cellgrid.CellGrid;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;

public class SimulationTab extends AbstractTab
{
	private final String TAB_NAME = "Simulation";
	private Animation animation;
	private Button pauseButton = makePauseButton(CellGrid.GUI_WIDTH / 2, CellGrid.GUI_HEIGHT + 50);
	private Button startButton = makeStartButton(CellGrid.GUI_WIDTH / 2, CellGrid.GUI_HEIGHT + 75);
	private Button stepButton = makeStepButton(CellGrid.GUI_WIDTH / 2, CellGrid.GUI_HEIGHT + 100);
	private Slider speedSlider = makeSpeedSlider(CellGrid.GUI_WIDTH / 2, CellGrid.GUI_HEIGHT + 125);
	private double defaultAnimationRate;

	public SimulationTab(Animation animation)
	{
		this.setTab(new Tab());
		this.getTab().setText(TAB_NAME);
		this.animation = animation;
		defaultAnimationRate = animation.getCurrentRate();
	}

	@Override
	public Tab updateTab(Collection<CellGrid> grids)
	{
		this.getTab().setContent(makeSimulation(grids));
		return this.getTab();
	}

	private Group makeSimulation(Collection<CellGrid> grids)
	{
		Group simulation = new Group();
		int i = 0;
		for (CellGrid grid : grids) {
			Group gridImage = CellGridMaker.makeGridImage(grid, i, grids.size());
			// gridImage.setLayoutX(gridImage.getLayoutX() +
			// Display.X_BOUNDARY);
			// gridImage.setLayoutY(gridImage.getLayoutY() +
			// Display.Y_BOUNDARY);
			simulation.getChildren().add(gridImage);
		}
		simulation.getChildren().add(pauseButton);
		simulation.getChildren().add(startButton);
		simulation.getChildren().add(stepButton);
		simulation.getChildren().add(speedSlider);
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

}
