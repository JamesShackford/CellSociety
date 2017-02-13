package property;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class IntProperty extends Property<Integer>
{

	private int minValue = 0;
	private int maxValue = 30;

	public IntProperty(String name)
	{
		super(name);
	}

	public void setBounds(int minValue, int maxValue)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void setValue(String stringValue)
	{
		this.setValue(Integer.valueOf(stringValue));
	}

	@Override
	public Node makeDynamicUpdater()
	{
		VBox vbox = new VBox();

		Label label = new Label(this.getName());

		Slider slider = new Slider();
		slider.setMin(minValue);
		slider.setMax(maxValue);
		slider.setValue(this.getValue());
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.setOnMouseReleased((event) -> {
			this.setValue((int) slider.getValue());
		});

		vbox.getChildren().add(label);
		vbox.getChildren().add(slider);

		return vbox;
	}

}
