package xml;

import java.util.Map;

import rule.AntRule;
import rule.Rule;

public class AntXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Ant";

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		return new AntRule(dataValues);
	}

}
