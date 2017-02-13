package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cell.RectangularCell;
import cellgrid.CellGrid;
import exeptions.ShowExceptions;
import rule.Rule;

public abstract class XMLRule {
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "width", "height", "initial_states" });

	public Rule makeRule(Map<String, String> dataValues) {
		Rule myRule = getRule(dataValues);
		Exception invalidParam = new Exception();
		int width = 0;
		int height = 0;
		try {
			width = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
			height = Integer.parseInt(dataValues.get(DATA_FIELDS.get(1)));
			if (width <= 0 || height <= 0) {
				throw invalidParam;
			}
		} catch (Exception e) {
			alert("INVALIDDIM");
		}
		String[] initialValues = dataValues.get(DATA_FIELDS.get(2)).split("\n");

		CellGrid myGrid = new CellGrid(width, height);
		myRule.setCellGrid(myGrid);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int currState = 0;
				try {
					currState = Integer.parseInt(initialValues[i].split(" ")[j]);
					if (myRule.getColor(currState) == null){
						throw new NullPointerException();
					}
				} catch (Exception e) {
					alert("INVALIDINITALSTATES");
				}
				Cell addedCell = new RectangularCell(myRule, currState, i, j);
				myGrid.setCell(i, j, addedCell);
			}
		}
		return myRule;
	}
	
	public void alert(String alertMess){
		ShowExceptions alert = new ShowExceptions();
		alert.showAlert(alertMess);
	}
	
	public abstract Rule getRule(Map<String, String> dataValues);
}
