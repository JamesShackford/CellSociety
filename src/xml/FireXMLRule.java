package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.FireRule;
import rule.Rule;

public class FireXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Fire";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		double fireProb = Double.parseDouble(dataValues.get(DATA_FIELDS.get(0)));
		return new FireRule(fireProb);
	}

	private static List<String> makeDataFields()
	{
		List<String> dataFields = new ArrayList<String>();
		dataFields.add("fire_probability");
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
