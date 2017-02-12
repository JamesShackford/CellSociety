package cell;

import java.util.Map;

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
	public Node getImage(double gridWidth, double gridHeight)
	{
		double cellHeight = gridHeight / this.getCellGrid().getHeight();
		double cellWidth = gridWidth / this.getCellGrid().getWidth();
		Rectangle image = new Rectangle(cellWidth * this.getX(), cellHeight * this.getY(), cellWidth, cellHeight);
		image.setFill(this.getRule().getColor(this.getState()));
		return image;
	}

	@Override
	public Map<String, Cell> getNeighbors()
	{
		return getAround();
	}

	@Override
	public Map<String, Cell> getNeighborsSides()
	{
		return getSides();
	}

	@Override
	public Map<String, Cell> getNeighborsWrap()
	{
		return getAllAround();
	}
}
