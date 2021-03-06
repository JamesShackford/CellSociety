package xml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.ShowExceptions;
import rule.AntRule;
import rule.FireRule;
import rule.GameOfLifeRule;
import rule.PredatorPreyRule;
import rule.Rule;
import rule.SegregationRule;
import rule.SugarRule;

public class XMLParser
{
	// name of root attribute that notes the type of file expecting to parse
	public static final String TYPE_ATTRIBUTE = "type";
	public static final Map<String, List<String>> FIELD_MAP = makeFieldMap();
	public static final Map<String, XMLRule> RULE_MAP = makeRuleMap();
	public static final Map<String, File> FILE_MAP = makeFileMap();

	// keep only one documentBuilder because it is expensive to make and can
	// reset it before parsing
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();

	/**
	 * Get the data contained in this XML file as an object
	 */
	public Rule getRule(File dataFile)
	{
		ShowExceptions alert = new ShowExceptions();
		Element root = getRootElement(dataFile);
		String dataType = getAttribute(root, TYPE_ATTRIBUTE);
		// if (!isValidFile(root, Music.DATA_TYPE)) {
		// // throw error
		// // throw new XMLException("XML file does not represent %s",
		// // Music.DATA_TYPE);
		// }
		// read data associated with the fields given by the object
		Map<String, String> results = new HashMap<>();
		try {
			for (String field : FIELD_MAP.get(dataType)) {
				results.put(field, getTextValue(root, field));
			}
		} catch (NullPointerException e) {
			alert.showAlert("INVALIDRULE");
		}

		return RULE_MAP.get(dataType).makeRule(results);
	}

	// Returns if this is a valid XML file for the specified object type
	private boolean isValidFile(Element root, String type)
	{
		return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
	}

	// Get value of Element's attribute
	private String getAttribute(Element e, String attributeName)
	{
		return e.getAttribute(attributeName);
	}

	// Get root element of an XML file
	private Element getRootElement(File xmlFile)
	{
		try {
			DOCUMENT_BUILDER.reset();
			Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
			return xmlDocument.getDocumentElement();
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e);
			// throw new XMLException(e);
		}
	}

	private static DocumentBuilder getDocumentBuilder()
	{
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
			// throw new XMLException(e);
		}
	}

	// Get value of Element's text
	private String getTextValue(Element e, String tagName)
	{
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		} else {
			// FIXME: empty string or null, is it an error to not find the text
			// value?
			return "";
		}
	}

	private static Map<String, XMLRule> makeRuleMap()
	{
		Map<String, XMLRule> ruleMap = new HashMap<String, XMLRule>();
		ruleMap.put(GameOfLifeXMLRule.DATA_TYPE, new GameOfLifeXMLRule());
		ruleMap.put(FireXMLRule.DATA_TYPE, new FireXMLRule());
		ruleMap.put(PredatorPreyXMLRule.DATA_TYPE, new PredatorPreyXMLRule());
		ruleMap.put(SegregationXMLRule.DATA_TYPE, new SegregationXMLRule());
		ruleMap.put(SugarXMLRule.DATA_TYPE, new SugarXMLRule());
		ruleMap.put(AntXMLRule.DATA_TYPE, new AntXMLRule());
		return ruleMap;
	}

	private static Map<String, List<String>> makeFieldMap()
	{
		Map<String, List<String>> fieldMap = new HashMap<String, List<String>>();
		fieldMap.put(GameOfLifeXMLRule.DATA_TYPE, GameOfLifeRule.DATA_FIELDS);
		fieldMap.put(FireXMLRule.DATA_TYPE, FireRule.DATA_FIELDS);
		fieldMap.put(PredatorPreyXMLRule.DATA_TYPE, PredatorPreyRule.DATA_FIELDS);
		fieldMap.put(SegregationXMLRule.DATA_TYPE, SegregationRule.DATA_FIELDS);
		fieldMap.put(SugarXMLRule.DATA_TYPE, SugarRule.DATA_FIELDS);
		fieldMap.put(AntXMLRule.DATA_TYPE, AntRule.DATA_FIELDS);
		return fieldMap;
	}

	private static Map<String, File> makeFileMap()
	{
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put(GameOfLifeXMLRule.DATA_TYPE, new File("./data/GameOfLife.xml"));
		fileMap.put(FireXMLRule.DATA_TYPE, new File("./data/Fire.xml"));
		fileMap.put(PredatorPreyXMLRule.DATA_TYPE, new File("./data/WaTor.xml"));
		fileMap.put(SegregationXMLRule.DATA_TYPE, new File("./data/Segregation.xml"));
		fileMap.put(SugarXMLRule.DATA_TYPE, new File("./data/Sugar.xml"));
		fileMap.put(AntXMLRule.DATA_TYPE, new File("./data/Ant.xml"));
		return fileMap;
	}
}
