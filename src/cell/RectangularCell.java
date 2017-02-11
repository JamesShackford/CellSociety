package cell;

import java.util.HashMap;
import java.util.Map;

import display.Display;
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
		int cellHeight = Display.DISPLAY_HEIGHT / this.getCellGrid().getHeight();
		int cellWidth = Display.DISPLAY_WIDTH / this.getCellGrid().getWidth();
		Rectangle image = new Rectangle(cellWidth * this.getX(), cellHeight * this.getY(), cellWidth, cellHeight);
		image.setFill(this.getRule().getStateMap().get(this.getState()));
		return image;
	}
	
	private boolean isValid(int i, int j){
		return (i >= 0 && i < this.getCellGrid().getHeight()) && (j >= 0 && j < this.getCellGrid().getWidth());
	}
	
	@Override
	public Map<String, Cell> getNeighbors() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		for (int i = this.getX() - 1; i <= this.getX() + 1; i++){
			for (int j = this.getY() - 1; j <= this.getY() + 1; j++){
				if (!(i == this.getX() && j == this.getY()) && isValid(i,j)){
					neighbors.put(i + " " + j, this.getCellGrid().getCell(i, j));
				}
			}
		}
		return neighbors;
	}
	
	public Map<String, Cell> getNeighborsSides() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (this.getY() + 1 < this.getCellGrid().getWidth()) {
			neighbors.put("Bottom", this.getCellGrid().getCell(this.getX(), this.getY() + 1));
		}
		if (this.getY() - 1 >= 0) {
			neighbors.put("Top", this.getCellGrid().getCell(this.getX(), this.getY() - 1));
		}
		if (this.getX() - 1 >= 0) {
			neighbors.put("Center Left", this.getCellGrid().getCell(this.getX() - 1, this.getY()));
		}
		if (this.getX() + 1 < this.getCellGrid().getHeight()) {
			neighbors.put("Center Right", this.getCellGrid().getCell(this.getX() + 1, this.getY()));
		}
		return neighbors;
	}
}
