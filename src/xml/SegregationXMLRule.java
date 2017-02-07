package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rule.Rule;
import rule.SegregationRule;

public class SegregationXMLRule implements XMLRule
{

	public static final String DATA_TYPE = "Segregation";

	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "threshold" });

	@Override
	public Rule makeRule(Map<String, String> dataValues)
	{
		double threshold = Double.parseDouble(dataValues.get(DATA_FIELDS.get(0)));
		return new SegregationRule(threshold);
	}

}
