package display;

import java.util.List;

import cellsociety.CellSociety;
import javafx.scene.control.Tab;

/**
 * The AbstractTab class is used to abstractly define a tab for cellsociety. The
 * AbstractTab class defines the cellSocieties that it displays, and the
 * javafx.scene.control.Tab that is what it displays.
 * 
 * @author jimmy
 *
 */
public abstract class AbstractTab
{
	private Tab tab;
	private List<CellSociety> cellSocieties;

	/**
	 * Construct an AbstractTab that displays the given cellSocieties in some
	 * form.
	 * 
	 * @param cellSocieties
	 */
	public AbstractTab(List<CellSociety> cellSocieties)
	{
		this.tab = new Tab();
		this.cellSocieties = cellSocieties;
	}

	/**
	 * Gets the javafx.scene.control.Tab representation of this tab
	 * 
	 * @return javafx.scene.control.Tab representation of tab
	 */
	public Tab getTab()
	{
		return tab;
	}

	/**
	 * Gets the societies that this tab is displaying.
	 * 
	 * @return List<CellSociety>
	 */
	public List<CellSociety> getSocieties()
	{
		return this.cellSocieties;
	}

	/**
	 * Sets the javafx.scene.control.Tab to the given tab
	 * 
	 * @param tab
	 *            javafx.scene.control.Tab
	 */
	public void setTab(Tab tab)
	{
		this.tab = tab;
	}

	/**
	 * Update the tab (what is called after every interval of time for
	 * animation)
	 * 
	 * @return updated Tab
	 */
	public abstract Tab updateTab();
}
