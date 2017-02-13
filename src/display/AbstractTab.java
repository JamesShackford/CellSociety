package display;

import java.util.List;

import cellsociety.CellSociety;
import javafx.scene.control.Tab;

public abstract class AbstractTab
{
	private Tab tab;
	private List<CellSociety> cellSocieties;

	public AbstractTab(List<CellSociety> cellSocieties)
	{
		this.tab = new Tab();
		this.cellSocieties = cellSocieties;
	}

	public Tab getTab()
	{
		return tab;
	}

	public List<CellSociety> getSocieties()
	{
		return this.cellSocieties;
	}

	public void setTab(Tab tab)
	{
		this.tab = tab;
	}

	public abstract Tab updateTab();
}
