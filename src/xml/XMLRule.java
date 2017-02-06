package xml;

import java.util.Map;

import rule.Rule;

public interface XMLRule
{
	public Rule makeRule(Map<String, String> stringVars);
}
