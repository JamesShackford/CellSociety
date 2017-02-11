package cellgrid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cell.Cell;

public class cellGridNeighbors {
	private List<List<Cell>> myGrid;
	private int x;
	private int y;
	private boolean isCorner;
	private boolean isEdge;

	public cellGridNeighbors(List<List<Cell>> grid, int curX, int curY) {
		myGrid = grid;
		x = curX;
		y = curY;
		isCorner = ((x - 1 < 0) && ((y - 1 < 0) || (y + 1 >= myGrid.get(y).size())))
				|| ((x + 1 >= myGrid.size()) && ((y - 1 < 0) || (y + 1 >= myGrid.get(y).size())));
		isEdge = (x - 1 < 0) || (x + 1 >= myGrid.size()) || (y - 1 < 0) || (y + 1 >= myGrid.get(y).size());
	}
	
	private boolean isValid(int i, int j){
		return (i >= 0 && i < myGrid.size()) && (j >= 0 && j < myGrid.size());
	}
	
	public Map<String, Cell> getNeighbors() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		for (int i = x - 1; i <= x + 1; i++){
			for (int j = y - 1; j <= y + 1; j++){
				if (i != x && j != y && isValid(i,j)){
					neighbors.put(i + " " + j, myGrid.get(i).get(j));
				}
			}
		}
		return neighbors;
	}

	public Map<String, Cell> getNeighborsWrap() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		for (int i = x - 1; i <= x + 1; i++){
			for (int j = y - 1; j <= y + 1; j++){
				if (!(i == x && j == y)){
					neighbors.put(i + " " + j, myGrid.get(Math.floorMod(i, myGrid.size())).get(Math.floorMod(j, myGrid.size())));				}
			}
		}
		return neighbors;
	}

	public Map<String, Cell> getNeighborsSides() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (y + 1 < myGrid.get(y).size()) {
			neighbors.put("Bottom", myGrid.get(x).get(y + 1));
		}
		if (y - 1 >= 0) {
			neighbors.put("Top", myGrid.get(x).get(y - 1));
		}
		if (x - 1 >= 0) {
			neighbors.put("Center Left", myGrid.get(x - 1).get(y));
		}
		if (x + 1 < myGrid.size()) {
			neighbors.put("Center Right", myGrid.get(x + 1).get(y));
		}
		return neighbors;
	}

	public Map<String, Cell> getNeighborsHex() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (y + 1 < myGrid.get(y).size()) {
			neighbors.put("Three", myGrid.get(x).get(y + 1));
		}
		if (y - 1 >= 0) {
			neighbors.put("One", myGrid.get(x).get(y - 1));
		}
		String r1;
		String l1;
		String r2 = null;
		String l2 = null;
		int yVal = y;
		if (y % 2 == 1) {
			r1 = "Four";
			l1 = "Five";
			if (y - 1 >= 0) {
			yVal = y-1;
				if (x - 1 >= 0) {
					l2 = "Two";
				}
				if (x + 1 < myGrid.size()) {
					r2 = "Zero";
				}
			}
		}else{
			r1 = "Two";
			l1 = "Zero";
			if (y + 1 < myGrid.size()) {
				yVal = y + 1;
				if (x - 1 >= 0) {
					l2 = "Five";
				}
				if (x + 1 < myGrid.size()) {
					r2 = "Four";
				}
			}
		}
		if (x - 1 >= 0) {
			neighbors.put(r1, myGrid.get(x - 1).get(y));
		}
		if (x + 1 < myGrid.size()) {
			neighbors.put(l1, myGrid.get(x + 1).get(y));
		}
		if (yVal < myGrid.size() && yVal >= 0) {
			if (x - 1 >= 0) {
				neighbors.put(l2, myGrid.get(x - 1).get(yVal));
			}
			if (x + 1 < myGrid.size()) {
				neighbors.put(r2, myGrid.get(x + 1).get(yVal));
			}
		}
		return neighbors;
	}
	
	public Map<String, Cell> getNeighborsTri() {
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.putAll(getNeighbors());
		if (x - 2 >= 0) {
			neighbors.put("Center 2 Left", myGrid.get(x - 2).get(y));
			if (y - 1 >= 0) {
				neighbors.put("Lower Center 2 Left", myGrid.get(x - 2).get(y-1));
			}else if(y + 1 < myGrid.size() && y%2 == 1 ){
				neighbors.put("Upper Center 2 Left", myGrid.get(x - 2).get(y+1));
			}
		}
		if (x + 2 < myGrid.size()) {
			neighbors.put("Center 2 Right", myGrid.get(x + 2).get(y));
			if (y - 1 >= 0) {
				neighbors.put("Lower Center 2 Right", myGrid.get(x + 2).get(y-1));
			}else if(y + 1 < myGrid.size() && y%2 == 1 ){
				neighbors.put("Upper Center 2 Left", myGrid.get(x + 2).get(y+1));
			}
		}
		return neighbors;
	}
}
