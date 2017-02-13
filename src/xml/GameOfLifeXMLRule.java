package xml;

import java.util.Map;

import rule.GameOfLifeRule;
import rule.Rule;

public class GameOfLifeXMLRule extends XMLRule
{
	public static final String DATA_TYPE = "GameOfLife";

	@Override
	public Rule getRule(Map<String, String> stringVars)
	{
		return new GameOfLifeRule(stringVars);
	}

}
