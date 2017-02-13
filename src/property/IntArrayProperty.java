package property;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class IntArrayProperty extends Property<List<List<Integer>>>
{
	public IntArrayProperty(String name)
	{
		super(name);
	}

	@Override
	public void setValue(String stringValue)
	{
		String[] values = stringValue.split("\n");
		int height = values.length;
		int width = values[0].split(" ").length;

		List<List<Integer>> states = new ArrayList<List<Integer>>();

		for (int i = 0; i < height; i++) {
			states.add(new ArrayList<Integer>());

			for (int j = 0; j < width; j++) {
				int currState = Integer.parseInt(values[i].split(" ")[j]);
				states.get(i).add(currState);
			}
		}

		this.setValue(states);
	}

	@Override
	public Node makeDynamicUpdater()
	{
		return null;
	}

}
