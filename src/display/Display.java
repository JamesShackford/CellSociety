package display;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cellsociety.CellSociety;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Display
{
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String LANGUAGE = "English";

	public static final int FRAMES_PER_SECOND = 4;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	public static final int X_BOUNDARY = 25;
	public static final int Y_BOUNDARY = 25;
	public static final int DISPLAY_HEIGHT = 800;
	public static final int DISPLAY_WIDTH = 700;

	public static final Paint BACKGROUND_COLOR = Color.WHITE;

	private Stage stage;
	private Group root;
	private Scene scene;
	private List<AbstractTab> abstractTabs;
	private TabPane tabs;
	private List<CellSociety> cellSocieties;
	private ResourceBundle myResources;
	private Animation animation;

	public Display(Stage stage, List<CellSociety> cellSocieties)
	{
		tabs = new TabPane();
		this.cellSocieties = cellSocieties;
		// cellSocieties = new ArrayList<CellSociety>();
		// cellSocieties.add(cellSociety);
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
		this.stage = stage;
		this.stage.setScene(setupScene());
		stage.setTitle(myResources.getString("DisplayTitle"));
		stage.show();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = setupAnimation(frame);
		animation.play();

		abstractTabs = new ArrayList<AbstractTab>();
		SimulationTab simulation = new SimulationTab(animation, cellSocieties);
		abstractTabs.add(simulation);
		GraphTab graph = new GraphTab(cellSocieties);
		abstractTabs.add(graph);
	}

	public void step(double SECOND_DELAY)
	{
		for (CellSociety society : cellSocieties) {
			society.getCellGrid().updateCellGrid();
		}
		displaySocieties();
	}

	private Animation setupAnimation(KeyFrame frame)
	{
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		return animation;
	}

	private Scene setupScene()
	{
		root = new Group();
		this.scene = new Scene(tabs, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		return scene;
	}

	public void displaySocieties()
	{
		for (AbstractTab abstractTab : abstractTabs) {
			tabs.getTabs().add(abstractTab.updateTab());
		}
	}

	public void displayShapes(ArrayList<Node> images)
	{
		for (Node shape : images) {
			root.getChildren().add(shape);
		}
	}
}
