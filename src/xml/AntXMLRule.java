package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.AntRule;
import rule.Rule;
import rule.SegregationRule;

public class AntXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Ant";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		int maxAntsPerCell = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
		double evapRate = Double.parseDouble(dataValues.get(DATA_FIELDS.get(1)));
		int startFoodLevel = Integer.parseInt(dataValues.get(DATA_FIELDS.get(2)));
		int startingAnts = Integer.parseInt(dataValues.get(DATA_FIELDS.get(3)));
		int maxPher = Integer.parseInt(dataValues.get(DATA_FIELDS.get(4)));;
		return new AntRule(maxAntsPerCell, startFoodLevel, startingAnts, maxPher, evapRate);
	}

	private static List<String> makeDataFields()
	{
		List<String> dataFields = new ArrayList<String>();
		dataFields.add("max_ants");
		dataFields.add("evap_rate");
		dataFields.add("start_food");
		dataFields.add("start_ants");
		dataFields.add("max_pher");
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
