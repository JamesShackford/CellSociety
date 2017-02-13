package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rule.PredatorPreyRule;
import rule.Rule;

public class PredatorPreyXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "WaTor";

	public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		int breedTime = 0;
		int starveTime = 0;
		Exception invalidPreyException = new Exception();
		try{
		starveTime = Integer.parseInt(dataValues.get(DATA_FIELDS.get(0)));
		breedTime = Integer.parseInt(dataValues.get(DATA_FIELDS.get(1)));
		if (starveTime < 0 || breedTime < 0){
			throw invalidPreyException;
		}
		}catch(Exception e){
			alert("INVALIDINITALSTATES");
		}
		
		return new PredatorPreyRule(starveTime, breedTime);
	}

	private static List<String> makeDataFields()
	{
		List<String> dataFields = new ArrayList<String>();
		dataFields.add("starve_time");
		dataFields.add("breed_time");
		dataFields.addAll(XMLRule.DATA_FIELDS);
		return dataFields;
	}

}
