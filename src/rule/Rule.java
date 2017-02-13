package rule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cell.Cell;
import cellgrid.CellGrid;
import javafx.scene.paint.Paint;
import parameters.Parameter;
import property.DoubleProperty;
import property.IntArrayProperty;
import property.Property;

public abstract class Rule {
	CellGrid cellGrid;
	private Map<Integer, Paint> stateColorMap;
	public static final List<String> GLOBAL_DATA_FIELDS = Arrays
			.asList(new String[] { "initial_states", "width", "height" });
	private IntArrayProperty startingConfiguration = new IntArrayProperty("initial_states");
	private DoubleProperty width = new DoubleProperty("width");
	private DoubleProperty height = new DoubleProperty("height");

	public Rule(Map<String, String> dataValues) {
		// for (Property<?> currProperty : this.getProperties()) {
		// currProperty.setValue(dataValues.get(currProperty.getName()));
		// }
		// CellGrid grid = new
		// CellGrid(this.getStartingConfiguration().getValue(), this);
		// this.setCellGrid(grid);
	}

	public Rule() {
		stateColorMap = makeStateMap();
	}

	public Rule(CellGrid cellGrid) {
		this.cellGrid = cellGrid;
		stateColorMap = makeStateMap();
	}

	public Paint getColor(int state) {
		return stateColorMap.get(state);
	}

	public abstract void determineNextState(Cell cell);

	public abstract Map<Integer, Paint> makeStateMap();

	public CellGrid getCellGrid() {
		return cellGrid;
	}

	public void setCellGrid(CellGrid cellGrid) {
		this.cellGrid = cellGrid;
	}

	public Map<Integer, Paint> getStateMap() {
		return stateColorMap;
	}

	public void duplicateStats(Cell current, Cell newCell) {
		newCell.getParameters().replaceParameters(current.getParameters().getParameterClone());
	}

	public Parameter getParameterType(int intialState) {
		return new Parameter();
	}

	public abstract List<Property<?>> getProperties();

	public List<Property<?>> getGlobalProperties() {
		List<Property<?>> globalProperties = new ArrayList<Property<?>>();
		globalProperties.add(startingConfiguration);
		globalProperties.add(width);
		globalProperties.add(height);
		return globalProperties;
	}

	public IntArrayProperty getStartingConfiguration() {
		return startingConfiguration;
	}

	public void saveConfiguration() {
		String filename = "./data/SaveConfiguration.xml";
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = determineContent();

			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
	
	public abstract String getSimTypeCopy();
	
	public abstract List<Property<?>> getPropertiesNewConfig();
	
	private String determineContent(){
		String file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		file += "<data type=\"" + getSimTypeCopy() + "\">\n";
		List<Property<?>> props = getPropertiesNewConfig();
		for (Property<?> prop: props){
			file += prop.convertToXML() + "\n";
		}
		file += addConfiguration();
		file += "\n</data>";
		return file;
	}
	
	private String addConfiguration(){
		String config = "<initial_states>";
		config += cellGrid.toString();
		config += "</initial_states>";
		return config;
	}
}
