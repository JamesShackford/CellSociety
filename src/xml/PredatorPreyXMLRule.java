package xml;

import java.util.Map;

import rule.PredatorPreyRule;
import rule.Rule;

public class PredatorPreyXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "WaTor";

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		return new PredatorPreyRule(dataValues);
	}

}
