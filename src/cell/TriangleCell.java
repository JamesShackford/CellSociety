package cell;

import java.util.Map;

import display.Display;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import rule.Rule;

public class TriangleCell extends Cell {

	public TriangleCell(Rule rule, int intialState, int x, int y) {
		super(rule, intialState, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Node getImage() {
		double cellWidth = Display.DISPLAY_WIDTH / this.getCellGrid().getWidth();
		double sideLength = cellWidth;
		double cellHeight = Math.tan(Math.toRadians(60))*sideLength/2.0;

		Polygon image = new Polygon();
		image.getPoints().addAll(sideLength/2.0, 0.0,
								2*sideLength, cellHeight,
								0.0, cellHeight,
								sideLength/2.0, 0.0);
		image.setFill(this.getRule().getStateMap().get(this.getState()));
		return image;
	}

	@Override
	public Map<String, Cell> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}

}
