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
	public static final int DISPLAY_HEIGHT = 600;
	public static final int DISPLAY_WIDTH = 600;
	public static final Paint BACKGROUND_COLOR = Color.BLACK;
	public static final String TITLE = "CellSociety";

	private Stage stage;
	private Group root;
	private Scene scene;

	public Display(Stage stage)
	{
		this.stage = stage;
		this.stage.setScene(setupScene());
		stage.setTitle(TITLE);
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
		int height = cellGrid.getHeight();
		int width = cellGrid.getWidth();
		int cellHeight = DISPLAY_HEIGHT / cellGrid.getHeight();
		int cellWidth = DISPLAY_WIDTH / cellGrid.getWidth();
		ArrayList<Shape> addedCells = new ArrayList<Shape>();

		for (int i = 0; i < cellGrid.getGrid().length; i++) {
			for (int j = 0; j < cellGrid.getGrid()[i].length; j++) {
				Cell currCell = cellGrid.getGrid()[i][j];
				Rectangle newCell = new Rectangle(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
				newCell.setFill(currCell.getRule().getStateMap().get(currCell.getState()));
				addedCells.add(newCell);
			}
		}
		displayShapes(addedCells);
	}

	public void displayShapes(ArrayList<Shape> shapes)
	{
		for (Shape shape : shapes) {
			root.getChildren().add(shape);
		}
	}
}
