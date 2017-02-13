package cellgrid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import cell.Cell;
import exceptions.ShowExceptions;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import rule.Rule;

/**
 * Hold a 2D array of cell objects where given a specific index can return the
 * neighbors of that cell and also updates the cells
 * 
 * @author Jonathan Rub
 *
 */
public class CellGrid
{
	private List<List<Cell>> myGrid;
	public static final Paint BORDER_COLOR = Color.BLACK;
	private int myHeight;
	private int myWidth;

	/**
	 * Initialized the Cell Grid to the specified sized and sets the height and
	 * width
	 * 
	 * @param size
	 */
	public CellGrid(int size)
	{
		myGrid = new ArrayList<List<Cell>>();
		for (int i = 0; i < size; i++) {
			myGrid.add(i, new ArrayList<Cell>());
			for (int j = 0; j < size; j++) {
				myGrid.get(i).add(j, null);
			}
		}

		myHeight = size;
		myWidth = size;
	}

	public CellGrid(List<List<Integer>> initialConfiguration, Rule rule, int height, int width,
			Class<? extends Cell> cellClass)
	{
		this(height);
		try {
			Constructor<? extends Cell> cellConstructor = cellClass.getConstructor(Rule.class, Integer.TYPE,
					Integer.TYPE, Integer.TYPE);
			rule.setCellGrid(this);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int currState = (initialConfiguration.get(i).get(j));
					Cell addedCell = cellConstructor.newInstance(rule, currState, i, j);
					this.setCell(i, j, addedCell);
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			ShowExceptions alert = new ShowExceptions();
			// alert.getAlert("INVALICELLCREATION");
			e.printStackTrace();
		}
	}

	/**
	 * Initialized the Cell Grid to the specified sized and sets the height and
	 * width for a non-square grid
	 * 
	 * @param size
	 */
	public CellGrid(int height, int width)
	{
		myGrid = new ArrayList<List<Cell>>();
		for (int i = 0; i < height; i++) {
			myGrid.add(i, new ArrayList<Cell>());
			for (int j = 0; j < width; j++) {
				myGrid.get(i).add(j, null);
			}
		}
		myHeight = height;
		myWidth = width;
	}

	public Cell getCell(int x, int y)
	{
		return myGrid.get(x).get(y);
	}

	public void setCell(int x, int y, Cell cell)
	{
		myGrid.get(x).set(y, cell);
	}

	/**
	 * @return the height of the 2D array
	 */
	public int getHeight()
	{
		return myHeight;
	}

	/**
	 * @return the width of the 2D array
	 */
	public int getWidth()
	{
		return myWidth;
	}

	/**
	 * iterates through the grid and updates all of the cells
	 */
	public void updateCellGrid()
	{
		for (int i = 0; i < myGrid.size(); i++) {
			for (int j = 0; j < myGrid.get(i).size(); j++) {
				myGrid.get(i).get(j).getRule().determineNextState(myGrid.get(i).get(j));
			}
		}
		for (int i = 0; i < myGrid.size(); i++) {
			for (int j = 0; j < myGrid.get(i).size(); j++) {
				myGrid.get(i).get(j).updateCell();
			}
		}
	}
}