package cellGrid;

import java.util.*;

import cell.Cell;

public class CellGrid {
	private Cell[][] myGrid;
	private int myHieght;
	private int myWidth;

	public CellGrid(int size) {
		myGrid = new Cell[size][size];
		myHieght = size;
		myWidth = size;
	}

	public Cell[][] getGrid() {
		return myGrid;
	}

	public int getHieght() {
		return myHieght;
	}

	public int getWidth() {
		return myWidth;
	}
	
	public Map<String, Cell> getNeighbors(int x, int y) {
		cellGridNeighbors cell = new cellGridNeighbors(myGrid, x, y);
		return cell.getNeighbors();
	}
}
