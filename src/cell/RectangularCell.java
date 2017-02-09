package cell;

import cellgrid.CellGrid;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import rule.Rule;

public class RectangularCell extends Cell
{
	public RectangularCell(Rule rule, int initialState, int x, int y)
	{
		super(rule, initialState, x, y);
	}

	@Override
	public Node getImage()
	{
		int cellHeight = CellGrid.GUI_HEIGHT / this.getCellGrid().getHeight();
		int cellWidth = CellGrid.GUI_WIDTH / this.getCellGrid().getWidth();
		Rectangle image = new Rectangle(cellWidth * this.getX(), cellHeight * this.getY(), cellWidth, cellHeight);
		image.setFill(this.getRule().getStateMap().get(this.getState()));
		return image;
	}
}
