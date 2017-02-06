package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rule.GameOfLifeRule;
import rule.Rule;

public class GameOfLifeXMLRule implements XMLRule
{
	public static final String DATA_TYPE = "GameOfLife";

	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {});

	@Override
	public Rule makeRule(Map<String, String> stringVars)
	{
		return new GameOfLifeRule();
	}

}
