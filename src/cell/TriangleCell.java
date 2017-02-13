package cell;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import rule.Rule;

public class TriangleCell extends Cell
{

	private int mySum = this.getX() + this.getY();

	public TriangleCell(Rule rule, int intialState, int x, int y)
	{
		super(rule, intialState, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Node getImage(double gridWidth, double gridHeight)
	{
		double cellWidth = gridWidth / this.getCellGrid().getWidth();
		double sideLength = cellWidth;
		double cellHeight = Math.tan(Math.toRadians(60)) * sideLength / 2.0;
		double xCor = (this.getY() / 2.0) * cellWidth;
		double yCor = (this.getX() + ((this.getY() + ((this.getX() + 1) % 2)) % 2)) * cellHeight;
		Polygon image = new Polygon();
		if ((this.getX() + this.getY()) % 2 == 0) {
			makeUpTri(image, xCor, yCor, sideLength, cellHeight);
		} else {
			makeDownTri(image, xCor, yCor, sideLength, cellHeight);
		}
		image.setFill(this.getRule().getColor(this.getState()));
		return image;
	}

	private void makeUpTri(Polygon image, double xCor, double yCor, double sideLength, double cellHeight)
	{
		image.getPoints().addAll(xCor, yCor, xCor + sideLength / 2.0, yCor - cellHeight, xCor + sideLength, yCor, xCor,
				yCor);
	}

	private void makeDownTri(Polygon image, double xCor, double yCor, double sideLength, double cellHeight)
	{
		image.getPoints().addAll(xCor, yCor, xCor + sideLength, yCor, xCor + sideLength / 2.0, yCor + cellHeight, xCor,
				yCor);
	}

	@Override
	public Map<String, Cell> getNeighbors()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getAround());
		if (isValid(this.getX(), this.getY() - 2)) {
			neighbors.put("Center 2 Left", this.getCellGrid().getCell(this.getX(), this.getY() - 2));
			if (isValid(this.getX() - 1, this.getY() - 2) && mySum % 2 == 1) {
				neighbors.put("Upper 2 Left", this.getCellGrid().getCell(this.getX() - 1, this.getY() - 2));
			}
			if (isValid(this.getX() + 1, this.getY() - 2) && mySum % 2 == 0) {
				neighbors.put("Upper 2 Right", this.getCellGrid().getCell(this.getX() + 1, this.getY() - 2));
			}
		}
		if (isValid(this.getX(), this.getY() + 2)) {
			neighbors.put("Center 2 Right", this.getCellGrid().getCell(this.getX(), this.getY() + 2));
			if (isValid(this.getX() - 1, this.getY() + 2) && mySum % 2 == 1) {
				neighbors.put("Lower 2 Right", this.getCellGrid().getCell(this.getX() - 1, this.getY() + 2));
			}
			if (isValid(this.getX() + 1, this.getY() + 2) && mySum % 2 == 0) {
				neighbors.put("Upper 2 Left", this.getCellGrid().getCell(this.getX() + 1, this.getY() + 2));
			}
		}
		return neighbors;
	}

	@Override
	public Map<String, Cell> getNeighborsSides()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getSides());
		if ((this.getX() + this.getY()) % 2 == 0) {
			neighbors.remove("Top");
		} else {
			neighbors.remove("Bottom");
		}
		return neighbors;
	}

	@Override
	public Map<String, Cell> getNeighborsWrap()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getAllAround());
		neighbors.put("Center 2 Left",
				this.getCellGrid().getCell(Math.floorMod(this.getX(), this.getCellGrid().getHeight()),
						Math.floorMod(this.getY() - 2, this.getCellGrid().getWidth())));
		neighbors.put("Center 2 Right",
				this.getCellGrid().getCell(Math.floorMod(this.getX(), this.getCellGrid().getHeight()),
						Math.floorMod(this.getY() + 2, this.getCellGrid().getWidth())));
		if (mySum % 2 == 1) {
			neighbors.put("Upper 2 Left",
					this.getCellGrid().getCell(Math.floorMod(this.getX() - 1, this.getCellGrid().getHeight()),
							Math.floorMod(this.getY() - 2, this.getCellGrid().getWidth())));
			neighbors.put("Lower 2 Right",
					this.getCellGrid().getCell(Math.floorMod(this.getX() - 1, this.getCellGrid().getHeight()),
							Math.floorMod(this.getY() + 2, this.getCellGrid().getWidth())));
		} else {
			neighbors.put("Upper 2 Right",
					this.getCellGrid().getCell(Math.floorMod(this.getX() + 1, this.getCellGrid().getHeight()),
							Math.floorMod(this.getY() - 2, this.getCellGrid().getWidth())));
			neighbors.put("Upper 2 Left",
					this.getCellGrid().getCell(Math.floorMod(this.getX() + 1, this.getCellGrid().getHeight()),
							Math.floorMod(this.getY() + 2, this.getCellGrid().getWidth())));
		}
		return neighbors;
	}
}
