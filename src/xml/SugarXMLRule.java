package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.Rule;
import rule.SugarRule;

public class SugarXMLRule extends XMLRule {

	public static final String DATA_TYPE = "Sugar";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues) {
		int sugarGrowBackRate = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
		int sugarGrowBackInterval = Integer.parseInt(dataValues.get(DATA_FIELDS.get(1)));
		int upperMetabolismLimit = Integer.parseInt(dataValues.get(DATA_FIELDS.get(2)));
		int lowerMetabolismLimit = Integer.parseInt(dataValues.get(DATA_FIELDS.get(3)));
		int upperVisionLimit = Integer.parseInt(dataValues.get(DATA_FIELDS.get(4)));
		int lowerVisionLimit = Integer.parseInt(dataValues.get(DATA_FIELDS.get(5)));
		int upperAgentStartSugar = Integer.parseInt(dataValues.get(DATA_FIELDS.get(6)));
		int lowerAgentStartSugar = Integer.parseInt(dataValues.get(DATA_FIELDS.get(7)));
		return new SugarRule(sugarGrowBackRate, sugarGrowBackInterval, upperMetabolismLimit, lowerMetabolismLimit,
				upperVisionLimit, lowerVisionLimit, upperAgentStartSugar, lowerAgentStartSugar);
	}

	private static List<String> makeDataFields() {
		List<String> dataFields = new ArrayList<String>();
		dataFields.add("sugar_grow_back_rate");
		dataFields.add("sugar_grow_back_interval");
		dataFields.add("upper_met_limit");
		dataFields.add("lower_met_limit");
		dataFields.add("upper_vision_limit");
		dataFields.add("lower_vision_limit");
		dataFields.add("upper_agent_start_sugar");
		dataFields.add("lower_agent_start_sugar");
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
