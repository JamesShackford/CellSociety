package property;

import javafx.scene.Node;

public class StringProperty extends Property<String>
{

	public StringProperty(String name)
	{
		super(name);
	}

	@Override
	public void setValue(String stringValue)
	{
		this.setValue(stringValue);
	}

	@Override
	public Node makeDynamicUpdater()
	{
		return null;
	}

}
