package Display;

import java.util.ArrayList;

import cell.Cell;
import cellGrid.CellGrid;
import javafx.scene.shape.Rectangle;

public class Display
{
	public final double DISPLAY_HEIGHT = 600;
	public final double DISPLAY_WIDTH = 600;

	public void displayGrid(CellGrid cellGrid)
	{
		int height = cellGrid.getHeight();
		int width = cellGrid.getWidth();
		int cellHeight = DISPLAY_HEIGHT / cellGrid.getHeight();
		int cellWidth = DISPLAY_WIDTH / cellGrid.getWidth();

		ArrayList<Rectangle> addedPictures = new ArrayList<Rectangle>();

		for (int i = 0; i < cellGrid.getGrid().length; i++) {
			for (int j = 0; j < cellGrid.getGrid()[i].length; j++) {
				Cell currCell = cellGrid.getGrid()[i][j];
				Rectangle newCell = new Rectangle(cellWidth * j, cellHeight * i, j, i);
				newCell.setFill(cellGrid.getStateMap().get(currCell.getState()));
				addedPictures.add(newCell);
			}
		}
	}
}
