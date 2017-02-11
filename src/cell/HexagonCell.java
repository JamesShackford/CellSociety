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
		double sideLength = cellWidth/2;
		double cellHeight = 2.0*Math.sin(Math.toRadians(60))*sideLength;
		double xCor = ((this.getY()*3.0)/4.0)*cellWidth;
		double yCor = ((this.getX()+1.0)-((this.getY()%2)/2.0))*cellHeight;
		Polygon image = new Polygon();
		image.getPoints().addAll(
				xCor, 						yCor,
				xCor+ sideLength/2.0, 		yCor - cellHeight/2.0,
				xCor+ 3.0*sideLength/2.0, 	yCor - cellHeight/2.0,
				xCor+ 2.0*sideLength, 		yCor,
				xCor+ 3.0*sideLength/2.0, 	yCor + cellHeight/2.0,					
				xCor+ sideLength/2.0, 		yCor + cellHeight/2.0,
				xCor, 						yCor);
		image.setStroke(Color.LIGHTBLUE);
		image.setFill(this.getRule().getStateMap().get(this.getState()));
		return image;
	}

	@Override
	public Map<String, Cell> getNeighbors() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (this.getY()+ 1 < this.getCellGrid().getWidth()) {
			neighbors.put("Three", this.getCellGrid().getCell(this.getX(), this.getY()+1));
		}
		if (this.getY()- 1 >= 0) {
			neighbors.put("One", this.getCellGrid().getCell(this.getX(), this.getY()-1));
		}
		String r1;
		String l1;
		String r2 = null;
		String l2 = null;
		int yVal = this.getY();
		if (this.getY()% 2 == 1) {
			r1 = "Four";
			l1 = "Five";
			if (this.getY()- 1 >= 0) {
			yVal = this.getY()-1;
				if (this.getX()- 1 >= 0) {
					l2 = "Two";
				}
				if (this.getX()+ 1 < this.getCellGrid().getHeight()) {
					r2 = "Zero";
				}
			}
		}else{
			r1 = "Two";
			l1 = "Zero";
			if (this.getY()+ 1 < this.getCellGrid().getWidth()) {
				yVal = this.getY()+ 1;
				if (this.getX()- 1 >= 0) {
					l2 = "Five";
				}
				if (this.getX()+ 1 < this.getCellGrid().getHeight()) {
					r2 = "Four";
				}
			}
		}
		if (this.getX()- 1 >= 0) {
			neighbors.put(r1, this.getCellGrid().getCell(this.getX()-1, this.getY()));
		}
		if (this.getX()+ 1 < this.getCellGrid().getHeight()) {
			neighbors.put(l1, this.getCellGrid().getCell(this.getX()+ 1, this.getY()));
		}
		if (yVal < this.getCellGrid().getWidth() && yVal >= 0) {
			if (this.getX()- 1 >= 0) {
				neighbors.put(l2, this.getCellGrid().getCell(this.getX()-1, yVal));
			}
			if (this.getX()+ 1 < this.getCellGrid().getHeight()) {
				neighbors.put(r2, this.getCellGrid().getCell(this.getX()+1, yVal));
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
		// TODO Auto-generated method stub
		return null;
	}

}
