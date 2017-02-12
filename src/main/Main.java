package main;

import java.util.ArrayList;
import java.util.List;

import cellsociety.CellSociety;
import display.Display;
import javafx.application.Application;
import javafx.stage.Stage;
import rule.Rule;
import xml.XMLParser;

public class Main extends Application
{
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	Display display;
	CellSociety cellSociety;

	public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final String LANGUAGE = "English";

	@Override
	public void start(Stage stage) throws Exception
	{
		String ruleName = (String) XMLParser.RULE_MAP.keySet().toArray()[0];
		Rule rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(ruleName));

		cellSociety = new CellSociety(rule);
		List<CellSociety> cellSocieties = new ArrayList<CellSociety>();
		cellSocieties.add(cellSociety);

		display = new Display(stage, cellSocieties);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}