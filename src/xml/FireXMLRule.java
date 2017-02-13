package xml;

import java.util.Map;

import rule.FireRule;
import rule.Rule;

public class FireXMLRule extends XMLRule
{

	public static final String DATA_TYPE = "Fire";

	// public static final List<String> DATA_FIELDS = makeDataFields();

	@Override
	public Rule getRule(Map<String, String> dataValues)
	{
		// double fireProb = 0.0;
		// Exception invalidFireProbException = new Exception();
		// try{
		// fireProb = Double.parseDouble(dataValues.get(DATA_FIELDS.get(0)));
		// if (fireProb < 0 || fireProb>1){
		// throw invalidFireProbException;
		// }
		// }catch(Exception e){
		// alert("INVALIDFIREPROB");
		// }
		return new FireRule(dataValues);
	}
	//
	// private static List<String> makeDataFields()
	// {
	// List<String> dataFields = new ArrayList<String>();
	// dataFields.add("fire_probability");
	// dataFields.addAll(XMLRule.DATA_FIELDS);
	// return dataFields;
	// }

}
