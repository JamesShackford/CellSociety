package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cell.HexagonCell;
import cell.RectangularCell;
import cell.TriangleCell;
import cellgrid.CellGrid;
import rule.Rule;

public abstract class XMLRule
{
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "width", "height", "initial_states" });

	public Rule makeRule(Map<String, String> dataValues)
	{
		Rule myRule = getRule(dataValues);
		int width = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
		int height = Integer.parseInt(dataValues.get(DATA_FIELDS.get(1)));
		String[] initialValues = dataValues.get(DATA_FIELDS.get(2)).split("\n");

		CellGrid myGrid = new CellGrid(width, height);
		myRule.setCellGrid(myGrid);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int currState = Integer.parseInt(initialValues[i].split(" ")[j]);
				Cell addedCell = new HexagonCell(myRule, currState, i, j);
				myGrid.setCell(i, j, addedCell);
			}
		}
		return myRule;
	}

	public abstract Rule getRule(Map<String, String> dataValues);
}
