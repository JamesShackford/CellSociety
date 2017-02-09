package display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import cellgrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Display
{
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String LANGUAGE = "English";

	public static final int X_BOUNDARY = 25;
	public static final int Y_BOUNDARY = 25;
	public static final int DISPLAY_HEIGHT = CellGrid.GUI_HEIGHT + 200;
	public static final int DISPLAY_WIDTH = CellGrid.GUI_WIDTH + 50;

	public static final Paint BACKGROUND_COLOR = Color.WHITE;

	private Stage stage;
	private Group root;
	private Scene scene;
	private List<AbstractTab> abstractTabs;
	TabPane tabs;
	private ResourceBundle myResources;

	public Display(Stage stage)
	{
		tabs = new TabPane();
		abstractTabs = new ArrayList<AbstractTab>();
		SimulationTab simulation = new SimulationTab();
		abstractTabs.add(simulation);
		GraphTab graph = new GraphTab();
		abstractTabs.add(graph);
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
		this.stage = stage;
		this.stage.setScene(setupScene());
		stage.setTitle(myResources.getString("DisplayTitle"));
		stage.show();
	}

	public Scene setupScene()
	{
		root = new Group();
		this.scene = new Scene(tabs, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		return scene;
	}

	public void displayGrid(CellGrid cellGrid)
	{
		for (AbstractTab abstractTab : abstractTabs) {
			List<CellGrid> myGrids = Arrays.asList(new CellGrid[] { cellGrid });
			tabs.getTabs().add(abstractTab.updateTab(myGrids));
		}
	}

	public void displayShapes(ArrayList<Node> images)
	{
		for (Node shape : images) {
			root.getChildren().add(shape);
		}
	}
}
