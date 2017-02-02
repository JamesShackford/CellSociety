package cellGrid;

import java.util.*;

import cell.Cell;
/**
 * Hold a 2D array pf cell objects where given a specific index
 * can return the neighbors of that cell and also updates the cells
 * 
 * @author Jonathan Rub
 *
 */
public class CellGrid {
	private Cell[][] myGrid;
	private int myHieght;
	private int myWidth;
	
	/**
	 * Initalized the Cell Grid to the specified sized and 
	 * sets the hieght and width
	 * 
	 * @param size
	 */
	public CellGrid(int size) {
		myGrid = new Cell[size][size];
		myHieght = size;
		myWidth = size;
	}
	/**
	 * @return the 2D array of Cells 
	 */
	public Cell[][] getGrid() {
		return myGrid;
	}
	/**
	 * @return the hieght of the 2D array
	 */
	public int getHieght() {
		return myHieght;
	}
	/**
	 * @return the width of the 2D array
	 */
	public int getWidth() {
		return myWidth;
	}
	/**
	 * Uses the cellGridNeighbors helper class to get the neighbors
	 * of the cells
	 * 
	 * @param x is the x index of the desired cell
	 * @param y is the y index of the desired cell
	 * @return a Hashmap consisting of the neighbor cells with the 
	 * keys being strings representing the location and the value 
	 * value being the cell
	 * 
	 */
	public Map<String, Cell> getNeighbors(int x, int y) {
		cellGridNeighbors cell = new cellGridNeighbors(myGrid, x, y);
		return cell.getNeighbors();
	}
	/**
	 * iterates throught the grid and updates all of the cells
	 */
	public void updateCellGrid(){
		for (int i = 0; i < myGrid.length; i++){
			for (int j = 0; j < myGrid.length; j++){
				myGrid[i][j].updateCell();
			}
		}
	}
}
