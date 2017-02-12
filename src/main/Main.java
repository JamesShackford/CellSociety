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

		ruleName = (String) XMLParser.RULE_MAP.keySet().toArray()[1];
		rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(ruleName));
		cellSociety = new CellSociety(rule);
		cellSocieties.add(cellSociety);

		ruleName = (String) XMLParser.RULE_MAP.keySet().toArray()[2];
		rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(ruleName));
		cellSociety = new CellSociety(rule);
		cellSocieties.add(cellSociety);

		display = new Display(stage, cellSocieties);
	}

	// private void handleKeyReleased(KeyCode code) throws Exception
	// {
	// if (code == KeyCode.S) {
	// myAnimation.stop();
	// }
	// if (code == KeyCode.P) {
	// myAnimation.pause();
	// }
	// if (code == KeyCode.B) {
	// myAnimation.play();
	// }
	// if (code == KeyCode.F) {
	// myAnimation.setRate(myAnimation.getCurrentRate() + 0.25);
	// }
	// if (code == KeyCode.D) {
	// if (myAnimation.getCurrentRate() > 0) {
	// myAnimation.setRate(myAnimation.getCurrentRate() - 0.25);
	// }
	// }
	// if (code == KeyCode.Q) {
	// // step(SECOND_DELAY);
	// }
	// if (code == KeyCode.N) {
	// myAnimation.stop();
	// start(myStage);
	// }
	// }

	public static void main(String[] args)
	{
		launch(args);
	}
}