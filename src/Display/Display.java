package Display;

import java.util.ArrayList;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Display
{
	public static final double DISPLAY_HEIGHT = 600;
	public static final double DISPLAY_WIDTH = 600;
	public static final Paint BACKGROUND_COLOR = Color.BLACK;
	public static final String TITLE = "CellSociety";

	private Stage stage;
	private Group root;
	private Scene scene;
	private CellGrid cellGrid;

	public Display(Stage stage)
	{
		this.stage = stage;
		this.scene = setupScene();
		this.stage.setScene(scene);
		stage.setTitle(TITLE);
		stage.show();
	}

	public Scene setupScene()
	{
		root = new Group();
		return new Scene(root, DISPLAY_WIDTH, DISPLAY_HEIGHT, BACKGROUND_COLOR);
	}

	public void refreshDisplay()
	{
		root.getChildren().clear();
	}

	public void displayGrid()
	{
		int height = cellGrid.getHeight();
		int width = cellGrid.getWidth();
		int cellHeight = DISPLAY_HEIGHT / cellGrid.getHeight();
		int cellWidth = DISPLAY_WIDTH / cellGrid.getWidth();
		ArrayList<Shape> addedCells = new ArrayList<Shape>();

		for (int i = 0; i < cellGrid.getGrid().length; i++) {
			for (int j = 0; j < cellGrid.getGrid()[i].length; j++) {
				Cell currCell = cellGrid.getGrid()[i][j];
				Rectangle newCell = new Rectangle(cellWidth * j, cellHeight * i, j, i);
				newCell.setFill(currCell.getStateMap().get(currCell.getState()));
				addedCells.add(newCell);
			}
		}
		refreshDisplay();
		displayShapes(addedCells);
	}

	public void displayShapes(ArrayList<Shape> shapes)
	{
		for (Shape shape : shapes) {
			root.getChildren().add(shape);
		}
	}
}
