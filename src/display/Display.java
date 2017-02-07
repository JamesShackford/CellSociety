package display;

import java.util.ArrayList;
import java.util.ResourceBundle;

import cellgrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Display
{
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	public static final String LANGUAGE = "English";

	public static final int DISPLAY_HEIGHT = 600;
	public static final int DISPLAY_WIDTH = 600;
	public static final Paint BACKGROUND_COLOR = Color.BLACK;

	private Stage stage;
	private Group root;
	private Scene scene;
    private ResourceBundle myResources;

	public Display(Stage stage)
	{
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
		this.stage = stage;
		this.stage.setScene(setupScene());
		stage.setTitle(myResources.getString("DisplayTitle"));
		stage.show();
	}

	public Scene setupScene()
	{
		root = new Group();
		this.scene = new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT, BACKGROUND_COLOR);
		return scene;
	}

	public void refreshDisplay()
	{
		root.getChildren().clear();
	}

	public void displayGrid(CellGrid cellGrid)
	{
		ArrayList<Node> addedCells = new ArrayList<Node>();

		for (int i = 0; i < cellGrid.getHeight(); i++) {
			for (int j = 0; j < cellGrid.getWidth(); j++) {
				addedCells.add(cellGrid.getCell(i, j).getImage());
			}
		}
		displayShapes(addedCells);
	}

	public void displayShapes(ArrayList<Node> images)
	{
		for (Node shape : images) {
			root.getChildren().add(shape);
		}
	}
}
