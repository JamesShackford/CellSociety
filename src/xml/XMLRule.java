package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import exceptions.ShowExceptions;
import rule.Rule;

public abstract class XMLRule
{
	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "width", "height", "initial_states" });

	public Rule makeRule(Map<String, String> dataValues)
	{
		Rule myRule = getRule(dataValues);
		Exception invalidParam = new Exception();
		return myRule;
	}

	public void alert(String alertMess)
	{
		ShowExceptions alert = new ShowExceptions();
		alert.showAlert(alertMess);
	}

	public abstract Rule getRule(Map<String, String> dataValues);

}
