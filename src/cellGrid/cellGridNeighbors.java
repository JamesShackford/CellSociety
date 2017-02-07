package cellGrid;

import java.util.HashMap;
import java.util.Map;

import cell.Cell;

public class cellGridNeighbors
{
	private Cell[][] myGrid;
	private int x;
	private int y;
	private boolean isCorner;
	private boolean isEdge;

	public cellGridNeighbors(Cell[][] grid, int curX, int curY)
	{
		myGrid = grid;
		x = curX;
		y = curY;
		isCorner = ((x - 1 < 0) && ((y - 1 < 0) || (y + 1 >= myGrid[y].length)))
				|| ((x + 1 >= myGrid.length) && ((y - 1 < 0) || (y + 1 >= myGrid[y].length)));
		isEdge = (x - 1 < 0) || (x + 1 >= myGrid.length) || (y - 1 < 0) || (y + 1 >= myGrid[y].length);
	}

	public Map<String, Cell> getNeighbors()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (isEdge) {
			if (x - 1 < 0) {
				if (!isCorner) {
					putCenterCol(neighbors, x, y);
					putRight(neighbors, x, y);
				} else if (y - 1 < 0) {
					putBottomRight(neighbors, x, y);
				} else {
					putTopRight(neighbors, x, y);
				}
			} else if (x + 1 >= myGrid.length) {
				if (!isCorner) {
					putLeft(neighbors, x, y);
					putCenterCol(neighbors, x, y);
				} else if (y - 1 < 0) {
					putBottomLeft(neighbors, x, y);
				} else {
					putTopLeft(neighbors, x, y);
				}
			} else if (y - 1 < 0) {
				if (!isCorner) {
					putCenterRow(neighbors, x, y);
					putBottom(neighbors, x, y);
				} else if (x - 1 < 0) {
					putBottomRight(neighbors, x, y);
				} else {
					putBottomLeft(neighbors, x, y);
				}
			} else {
				if (!isCorner) {
					putCenterRow(neighbors, x, y);
					putTop(neighbors, x, y);
				} else if (x - 1 < 0) {
					putTopRight(neighbors, x, y);
				} else {
					putTopLeft(neighbors, x, y);
				}
			}
		} else {
			putAll(neighbors, x, y);
		}
		return neighbors;
	}

	public Map<String, Cell> getNeighborsWrap()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		neighbors.put("Bottom Left", myGrid[Math.floorMod(x - 1, myGrid.length)][Math.floorMod(y + 1, myGrid.length)]);
		neighbors.put("Bottom", myGrid[Math.floorMod(x, myGrid.length)][Math.floorMod(y + 1, myGrid.length)]);
		neighbors.put("Bottom Right", myGrid[Math.floorMod(x + 1, myGrid.length)][Math.floorMod(y + 1, myGrid.length)]);
		neighbors.put("Top Left", myGrid[Math.floorMod(x - 1, myGrid.length)][Math.floorMod(y - 1, myGrid.length)]);
		neighbors.put("Top", myGrid[Math.floorMod(x, myGrid.length)][Math.floorMod(y - 1, myGrid.length)]);
		neighbors.put("Top Right", myGrid[Math.floorMod(x + 1, myGrid.length)][Math.floorMod(y - 1, myGrid.length)]);
		neighbors.put("Center Left", myGrid[Math.floorMod(x - 1, myGrid.length)][Math.floorMod(y, myGrid.length)]);
		neighbors.put("Bottom Right", myGrid[Math.floorMod(x + 1, myGrid.length)][Math.floorMod(y, myGrid.length)]);
		return neighbors;
	}

	public Map<String, Cell> getNeighborsSides()
	{
		Map<String, Cell> neighbors = new HashMap<String, Cell>();
		if (y + 1 < myGrid[y].length) {
			neighbors.put("Bottom", myGrid[x][y + 1]);
		}
		if (y - 1 >= 0) {
			neighbors.put("Top", myGrid[x][y - 1]);
		}
		if (x - 1 >= 0) {
			neighbors.put("Center Left", myGrid[x - 1][y]);
		}
		if (x + 1 < myGrid.length) {
			neighbors.put("Center Right", myGrid[x + 1][y]);
		}
		return neighbors;
	}

	private void putBottomRight(Map<String, Cell> neighbors, int x2, int y2)
	{
		neighbors.put("Bottom", myGrid[x][y + 1]);
		neighbors.put("Bottom Right", myGrid[x + 1][y + 1]);
		// neighbors.put("Center", myGrid[x][y]);
		neighbors.put("Center Right", myGrid[x + 1][y]);
	}

	private void putBottomLeft(Map<String, Cell> neighbors, int x2, int y2)
	{
		neighbors.put("Bottom Left", myGrid[x - 1][y + 1]);
		neighbors.put("Bottom", myGrid[x][y + 1]);
		neighbors.put("Center Left", myGrid[x - 1][y]);
		// neighbors.put("Center", myGrid[x][y]);
	}

	private void putTopLeft(Map<String, Cell> neighbors, int x2, int y2)
	{
		neighbors.put("Top Left", myGrid[x - 1][y - 1]);
		neighbors.put("Top", myGrid[x][y - 1]);
		neighbors.put("Center Left", myGrid[x - 1][y]);
		// neighbors.put("Center", myGrid[x][y]);
	}

	private void putTopRight(Map<String, Cell> neighbors, int x2, int y2)
	{
		neighbors.put("Top", myGrid[x][y - 1]);
		neighbors.put("Top Right", myGrid[x + 1][y - 1]);
		// neighbors.put("Center", myGrid[x][y]);
		neighbors.put("Center Right", myGrid[x + 1][y]);
	}

	private void putAll(Map<String, Cell> neighbors, int x, int y)
	{
		putTop(neighbors, x, y);
		putCenterRow(neighbors, x, y);
		putBottom(neighbors, x, y);
	}

	private void putBottom(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Bottom Left", myGrid[x - 1][y + 1]);
		neighbors.put("Bottom", myGrid[x][y + 1]);
		neighbors.put("Bottom Right", myGrid[x + 1][y + 1]);
	}

	private void putTop(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Top Left", myGrid[x - 1][y - 1]);
		neighbors.put("Top", myGrid[x][y - 1]);
		neighbors.put("Top Right", myGrid[x + 1][y - 1]);
	}

	private void putCenterRow(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Center Left", myGrid[x - 1][y]);
		// neighbors.put("Center", myGrid[x][y]);
		neighbors.put("Center Right", myGrid[x + 1][y]);
	}

	private void putRight(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Top Right", myGrid[x + 1][y - 1]);
		neighbors.put("Center Right", myGrid[x + 1][y]);
		neighbors.put("Bottom Right", myGrid[x + 1][y + 1]);
	}

	private void putLeft(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Top Left", myGrid[x - 1][y - 1]);
		neighbors.put("Center Left", myGrid[x - 1][y]);
		neighbors.put("Bottom Left", myGrid[x - 1][y + 1]);
	}

	private void putCenterCol(Map<String, Cell> neighbors, int x, int y)
	{
		neighbors.put("Top", myGrid[x][y - 1]);
		// neighbors.put("Center", myGrid[x][y]);
		neighbors.put("Bottom", myGrid[x][y + 1]);
	}

}
