package property;

import java.util.HashMap;
import java.util.Map;

import cell.Cell;
import cell.HexagonCell;
import cell.RectangularCell;
import cell.TriangleCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class CellTypeProperty extends Property<Class<? extends Cell>>
{

	Map<String, Class<? extends Cell>> shapeTypes = makeShapeMap();
	String currStringValue;

	public CellTypeProperty(String name)
	{
		super(name);
	}

	@Override
	public void setValue(String stringValue)
	{
		this.setValue(shapeTypes.get(stringValue));
		currStringValue = stringValue;
	}

	@Override
	public Node makeDynamicUpdater()
	{
		ObservableList<String> shapes = FXCollections.observableArrayList(shapeTypes.keySet());
		ComboBox<String> typeSelector = new ComboBox<String>(shapes);
		typeSelector.setValue(currStringValue);
		typeSelector.valueProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldCell, String newCell)
			{
				setValue(newCell);
			}

		});
		return typeSelector;
	}

	private Map<String, Class<? extends Cell>> makeShapeMap()
	{
		Map<String, Class<? extends Cell>> shapeMap = new HashMap<String, Class<? extends Cell>>();
		shapeMap.put("triangle", TriangleCell.class);
		shapeMap.put("rectangle", RectangularCell.class);
		shapeMap.put("hexagon", HexagonCell.class);
		return shapeMap;
	}

}
