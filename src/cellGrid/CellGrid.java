package cellGrid;

import java.util.*;

import cell.Cell;

public class CellGrid
{
	private Cell[][] myGrid;

	public CellGrid(int size)
	{
		myGrid = new Cell[size][size];
	}

	public Cell[][] getGrid()
	{
		return myGrid;
	}

	public Map getNeighbors(int x, int y){
		Map neighbors = new HashMap();
		if (myGrid[x][y].getIsEdge){
			if (){
				
			}else if(){
				
			}else if(){
				
			}
		}else{
			putAll(neighbors);
		}
	}

	private void putAll(Map neighbors)
	{
		putBottom(neighbors);
		putTop(neighbors);
	}

	private void putBottom(Map neighbors){
		neighbors.put("Center Left", myGrid[x-1][y])
		neighbors.put("Center", myGrid[x][y])
		neighbors.put("Center Right", myGrid[x+1][y])
		neighbors.put("Bottom Left", myGrid[x-1][y+1])
		neighbors.put("Bottom", myGrid[x][y+1])
		neighbors.put("Bottom Right", myGrid[x+1][y+1])
	}

	private void putTop(Map neighbors){
		neighbors.put("Top Left", myGrid[x-1][y-1])
		neighbors.put("Top", myGrid[x][y-1])
		neighbors.put("Top Right", myGrid[x+1][y-1])
		neighbors.put("Center Left", myGrid[x-1][y])
		neighbors.put("Center", myGrid[x][y])
		neighbors.put("Center Right", myGrid[x+1][y])
	}

	private void putRight(Map neighbors){
		neighbors.put("Top", myGrid[x][y-1])
		neighbors.put("Top Right", myGrid[x+1][y-1])
		neighbors.put("Center", myGrid[x][y])
		neighbors.put("Center Right", myGrid[x+1][y])
		neighbors.put("Bottom", myGrid[x][y+1])
		neighbors.put("Bottom Right", myGrid[x+1][y+1])
	}

	private void putLeft(Map neighbors){
		neighbors.put("Top Left", myGrid[x-1][y-1])
		neighbors.put("Top", myGrid[x][y-1])
		neighbors.put("Center Left", myGrid[x-1][y])
		neighbors.put("Center", myGrid[x][y])
		neighbors.put("Bottom Left", myGrid[x-1][y+1])
		neighbors.put("Bottom", myGrid[x][y+1])
	}
}
