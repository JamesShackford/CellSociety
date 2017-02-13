package xml;

import java.util.Map;

import rule.Rule;
import rule.SegregationRule;

public class SegregationXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Segregation";

	// public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		return new SegregationRule(dataValues);
	}

}
