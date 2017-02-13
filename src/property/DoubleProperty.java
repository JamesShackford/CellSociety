package property;

import javafx.scene.Node;
import javafx.scene.control.Slider;

public class DoubleProperty extends Property<Double>
{

	private double minValue;
	private double maxValue;

	public DoubleProperty(String name)
	{
		super(name);
	}

	public void setBounds(double minValue, double maxValue)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void setValue(String stringValue)
	{
		this.setValue(Double.valueOf(stringValue));
	}

	@Override
	public Node makeDynamicUpdater()
	{
		Slider slider = new Slider();
		slider.setMin(minValue);
		slider.setMax(maxValue);
		slider.setValue(this.getValue());
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.setOnMouseReleased((event) -> {
			this.setValue(slider.getValue());
		});
		return slider;
	}

}
