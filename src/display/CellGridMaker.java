package display;

import cellgrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.Node;

public class CellGridMaker
{
	public static Group makeGridImage(CellGrid cellGrid)
	{
		Group group = new Group();

		for (int i = 0; i < cellGrid.getHeight(); i++) {
			for (int j = 0; j < cellGrid.getWidth(); j++) {
				Node addedCell = cellGrid.getCell(i, j).getImage();
				group.getChildren().add(addedCell);
			}
		}
		return group;
	}
}
