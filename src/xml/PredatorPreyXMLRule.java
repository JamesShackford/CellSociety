package xml;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rule.PredatorPreyRule;
import rule.Rule;

public class PredatorPreyXMLRule implements XMLRule
{

	public static final String DATA_TYPE = "WaTor";

	public static final List<String> DATA_FIELDS = Arrays.asList(new String[] { "starve_time", "breed_time" });

	@Override
	public Rule makeRule(Map<String, String> dataValues)
	{
		int starveTime = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
		int breedTime = Integer.parseInt(dataValues.get(DATA_FIELDS.get(1)));
		return new PredatorPreyRule(starveTime, breedTime);
	}

}
