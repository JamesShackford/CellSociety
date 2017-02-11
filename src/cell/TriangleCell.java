package cell;

import java.util.HashMap;
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
		double cellHeight = Math.tan(Math.toRadians(60)) * sideLength / 2.0;
		double xCor = (this.getY() / 2.0) * cellWidth;
		double yCor = (this.getX()+((this.getY()+((this.getX()+1)%2))%2)) * cellHeight;
		Polygon image = new Polygon();
		if ((this.getX() + this.getY()) % 2 == 0) {
			makeUpTri(image, xCor, yCor, sideLength, cellHeight);
		} else {
			makeDownTri(image, xCor, yCor, sideLength, cellHeight);
		}
		image.setFill(this.getRule().getStateMap().get(this.getState()));
		return image;
	}
	
	private void makeUpTri(Polygon image, double xCor, double yCor, double sideLength, double cellHeight) {
		image.getPoints().addAll(
				xCor, yCor, 
				xCor + sideLength/2.0, yCor - cellHeight,
				xCor + sideLength, yCor,
				xCor, yCor);
	}

	private void makeDownTri(Polygon image, double xCor, double yCor, double sideLength, double cellHeight) {
		image.getPoints().addAll(
				xCor, yCor,
				xCor + sideLength, yCor,
				xCor + sideLength/2.0, yCor + cellHeight,
				xCor, yCor);
	}
	
	@Override
	public Map<String, Cell> getNeighbors() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getAround());
		if (this.getX() - 2 >= 0) {
			neighbors.put("Center 2 Left", this.getCellGrid().getCell(this.getX() - 2, this.getY()));
			if (this.getY() - 1 >= 0) {
				neighbors.put("Lower Center 2 Left", this.getCellGrid().getCell(this.getX() - 2, this.getY()-1));
			}else if(this.getY() + 1 < this.getCellGrid().getWidth() && this.getY()%2 == 1 ){
				neighbors.put("Upper Center 2 Left", this.getCellGrid().getCell(this.getX() - 2, this.getY()+1));
			}
		}
		if (this.getX() + 2 < this.getCellGrid().getHeight()) {
			neighbors.put("Center 2 Right", this.getCellGrid().getCell(this.getX() + 2, this.getY()));
			if (this.getY() - 1 >= 0) {
				neighbors.put("Lower Center 2 Right", this.getCellGrid().getCell(this.getX() + 2, this.getY()-1));
			}else if(this.getY() + 1 < this.getCellGrid().getWidth() && this.getY()%2 == 1 ){
				neighbors.put("Upper Center 2 Left", this.getCellGrid().getCell(this.getX() + 2, this.getY()+1));
			}
		}
		return neighbors;
	}

	@Override
	public Map<String, Cell> getNeighborsSides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Cell> getNeighborsWrap() {
		// TODO Auto-generated method stub
		return null;
	}

}
