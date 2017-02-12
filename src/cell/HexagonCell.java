package cell;

import java.util.HashMap;
import java.util.Map;

import display.Display;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import rule.Rule;

public class HexagonCell extends Cell {

	public HexagonCell(Rule rule, int intialState, int x, int y) {
		super(rule, intialState, x, y);
	}

	@Override
	public Node getImage() {
		double cellWidth = Display.DISPLAY_WIDTH / this.getCellGrid().getWidth();
		double sideLength = cellWidth / 2;
		double cellHeight = 2.0 * Math.sin(Math.toRadians(60)) * sideLength;
		double xCor = ((this.getY() * 3.0) / 4.0) * cellWidth;
		double yCor = ((this.getX() + 1.0) - ((this.getY() % 2) / 2.0)) * cellHeight;
		Polygon image = new Polygon();
		image.getPoints().addAll(xCor, yCor, xCor + sideLength / 2.0, yCor - cellHeight / 2.0,
				xCor + 3.0 * sideLength / 2.0, yCor - cellHeight / 2.0, xCor + 2.0 * sideLength, yCor,
				xCor + 3.0 * sideLength / 2.0, yCor + cellHeight / 2.0, xCor + sideLength / 2.0,
				yCor + cellHeight / 2.0, xCor, yCor);
		image.setStroke(Color.LIGHTBLUE);
		image.setFill(this.getRule().getColor(this.getState()));
		return image;
	}

	@Override
	public Map<String, Cell> getNeighbors() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getSides());
		if (this.getX() % 2 == 1) {
			if (isValid(this.getX() - 1, this.getY() - 1)) {
				neighbors.put((this.getX() - 1) + " " + (this.getY() - 1),
						this.getCellGrid().getCell(this.getX() - 1, this.getY() - 1));
			}
			if (isValid(this.getX() - 1, this.getY() + 1)) {
				neighbors.put((this.getX() - 1) + " " + (this.getY() + 1),
						this.getCellGrid().getCell(this.getX() - 1, this.getY() + 1));
			}
		} else {
			if (isValid(this.getX() + 1, this.getY() + 1)) {
				neighbors.put((this.getX() + 1) + " " + (this.getY() + 1),
						this.getCellGrid().getCell(this.getX() + 1, this.getY() + 1));
			}
			if (isValid(this.getX() + 1, this.getY() - 1)) {
				neighbors.put((this.getX() - 1) + " " + (this.getY() + 1),
						this.getCellGrid().getCell(this.getX() + 1, this.getY() - 1));
			}
		}
		return neighbors;
	}

	@Override
	public Map<String, Cell> getNeighborsSides() {
		return getNeighbors();
	}

	@Override
	public Map<String, Cell> getNeighborsWrap() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getAllAround());
		if (this.getY() % 2 == 0) {
			neighbors.remove((this.getX() - 1) + " " + (this.getY() - 1));
			neighbors.remove((this.getX() - 1) + " " + (this.getY() + 1));
		} else {
			neighbors.remove((this.getX() + 1) + " " + (this.getY() + 1));
			neighbors.remove((this.getX() + 1) + " " + (this.getY() - 1));
		}
		return neighbors;
	}

}
