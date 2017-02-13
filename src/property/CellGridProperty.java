package property;

import cell.Cell;
import cell.RectangularCell;
import cellgrid.CellGrid;
import javafx.scene.Node;
import rule.Rule;

public class CellGridProperty extends Property<CellGrid>
{
	Rule rule;

	public CellGridProperty(String name, Rule rule)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setValue(String stringValue)
	{
		String[] values = stringValue.split("\n");
		int height = values.length;
		int width = values[0].split(" ").length;

		CellGrid myGrid = new CellGrid(width, height);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int currState = Integer.parseInt(values[i].split(" ")[j]);
				Cell addedCell = new RectangularCell(rule, currState, i, j);
				myGrid.setCell(i, j, addedCell);
			}
		}

		this.setValue(myGrid);
	}

	@Override
	public Node makeDynamicUpdater()
	{
		return null;
	}

}
