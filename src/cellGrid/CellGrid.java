package cellGrid;

import java.util.*;

import cell.Cell;

public class CellGrid {
	private Cell[][] myGrid;
	private int myHeight;
	private int myWidth;

	public CellGrid(int size) {
		myGrid = new Cell[size][size];
		myHeight = size;
		myWidth = size;
	}

	public Cell[][] getGrid() {
		return myGrid;
	}

	public int getHeight() {
		return myHeight;
	}

	public int getWidth() {
		return myWidth;
	}
	
	public Map<String, Cell> getNeighbors(int x, int y) {
		cellGridNeighbors cell = new cellGridNeighbors(myGrid, x, y);
		return cell.getNeighbors();
	}
}
