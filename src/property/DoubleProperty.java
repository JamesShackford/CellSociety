package property;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class DoubleProperty extends Property<Double>
{

	private double minValue = 0;
	private double maxValue = 1;

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
		VBox vbox = new VBox();

		Label label = new Label(this.getName());

		Slider slider = new Slider();
		slider.setMin(minValue);
		slider.setMax(maxValue);
		slider.setValue(this.getValue());
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
			{
				setValue((double) new_val);
			}
		});
		vbox.getChildren().add(label);
		vbox.getChildren().add(slider);
		return vbox;
	}

}
