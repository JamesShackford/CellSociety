package display;

import java.util.Collection;

import cellgrid.CellGrid;
import javafx.scene.control.Tab;

public abstract class AbstractTab
{
	private Tab tab;

	public AbstractTab()
	{
		this.tab = new Tab();
	}

	public Tab getTab()
	{
		return tab;
	}

	public void setTab(Tab tab)
	{
		this.tab = tab;
	}

	public abstract Tab updateTab(Collection<CellGrid> grids);
}