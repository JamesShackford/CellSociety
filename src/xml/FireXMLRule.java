package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rule.FireRule;
import rule.Rule;

public class FireXMLRule implements XMLRule
{

	public static final String DATA_TYPE = "Fire";

	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "fire_probability" });

	@Override
	public Rule makeRule(Map<String, String> dataValues)
	{
		double fireProb = Double.parseDouble(dataValues.get(DATA_FIELDS.get(0)));
		return new FireRule(fireProb);
	}

}
