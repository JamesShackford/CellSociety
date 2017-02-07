package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.Rule;
import rule.SegregationRule;

public class SegregationXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Segregation";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		double threshold = Double.parseDouble(dataValues.get(DATA_FIELDS.get(0)));
		return new SegregationRule(threshold);
	}

	private static List<String> makeDataFields()
	{
		List<String> dataFields = new ArrayList<String>();
		dataFields.add("threshold");
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
