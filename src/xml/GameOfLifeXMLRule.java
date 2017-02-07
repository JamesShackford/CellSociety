package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.GameOfLifeRule;
import rule.Rule;

public class GameOfLifeXMLRule extends XMLRule
{
	public static final String DATA_TYPE = "GameOfLife";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> stringVars)
	{
		return new GameOfLifeRule();
	}

	private static List<String> makeDataFields()
	{
		List<String> dataFields = new ArrayList<String>();
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
