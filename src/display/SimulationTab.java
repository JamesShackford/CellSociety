package display;

import java.util.List;

import cellgrid.CellGrid;
import javafx.scene.Group;
import javafx.scene.control.Tab;

public class SimulationTab extends AbstractTab
{
	private final String TAB_NAME = "Simulation";
	Tab tab;

	public SimulationTab()
	{
		this.tab = new Tab();
		tab.setText(TAB_NAME);
	}

	@Override
	public Tab updateTab(List<CellGrid> grids)
	{
		tab.setContent(makeSimulation(grids));
		return tab;
	}

	private Group makeSimulation(List<CellGrid> grids)
	{
		Group simulation = new Group();
		for (CellGrid grid : grids) {
			simulation.getChildren().add(CellGridMaker.makeGridImage(grid));
		}
		return simulation;
	}

}
