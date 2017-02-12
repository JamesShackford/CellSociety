package display;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import cellgrid.CellGrid;
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
	public static final int DISPLAY_HEIGHT = CellGrid.GUI_HEIGHT + 200;
	public static final int DISPLAY_WIDTH = CellGrid.GUI_WIDTH + 50;

	public static final Paint BACKGROUND_COLOR = Color.WHITE;

	private Stage stage;
	private Group root;
	private Scene scene;
	private List<AbstractTab> abstractTabs;
	private TabPane tabs;
	private CellSociety cellSociety;
	private ResourceBundle myResources;
	private Animation animation;

	public Display(Stage stage, CellSociety cellSociety)
	{
		tabs = new TabPane();
		this.cellSociety = cellSociety;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
		this.stage = stage;
		this.stage.setScene(setupScene());
		stage.setTitle(myResources.getString("DisplayTitle"));
		stage.show();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation = setupAnimation(frame);
		animation.play();

		abstractTabs = new ArrayList<AbstractTab>();
		SimulationTab simulation = new SimulationTab(animation);
		abstractTabs.add(simulation);
		GraphTab graph = new GraphTab();
		abstractTabs.add(graph);
	}

	public void step(double SECOND_DELAY)
	{
		// myStage.getScene().setOnKeyReleased(e -> {
		// try {
		// handleKeyReleased(e.getCode());
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// });
		// display.refreshDisplay();
		cellSociety.step(this);
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

	public void displayGrids(Collection<CellGrid> cellGrids)
	{
		for (AbstractTab abstractTab : abstractTabs) {
			tabs.getTabs().add(abstractTab.updateTab(cellGrids));
		}
	}

	public void displayShapes(ArrayList<Node> images)
	{
		for (Node shape : images) {
			root.getChildren().add(shape);
		}
	}
}
