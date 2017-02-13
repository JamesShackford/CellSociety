package main;

import java.util.ArrayList;
import java.util.List;

import cellsociety.CellSociety;
import display.AbstractTab;
import display.Display;
import display.SimulationGroup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import rule.Rule;
import xml.XMLParser;

public class PaneExample extends Application
{
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	Display display;
	List<CellSociety> cellSocieties;
	List<AbstractTab> abstractTabs;
	private TabPane tabs;
	Tab myTab;

	public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final String LANGUAGE = "English";

	@Override
	public void start(Stage stage) throws Exception
	{
		GridPane flow = new GridPane();

		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setStyle("-fx-background-color: DAE6F3;");

		List<SimulationGroup> simulationGroups = new ArrayList<SimulationGroup>();

		String ruleName = (String) XMLParser.RULE_MAP.keySet().toArray()[0];
		Rule rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(ruleName));

		CellSociety cellSociety = new CellSociety(rule);
		cellSocieties = new ArrayList<CellSociety>();
		cellSocieties.add(cellSociety);

		ruleName = (String) XMLParser.RULE_MAP.keySet().toArray()[1];
		rule = new XMLParser().getRule(XMLParser.FILE_MAP.get(ruleName));

		cellSociety = new CellSociety(rule);
		cellSocieties.add(cellSociety);

		TabPane tabs = new TabPane();
		stage.setScene(new Scene(tabs, Display.DISPLAY_WIDTH, Display.DISPLAY_HEIGHT));
		stage.show();

		Tab myTab = new Tab();
		myTab.setContent(flow);

		tabs.getTabs().add(myTab);
		KeyFrame frame = new KeyFrame(Duration.millis(Display.MILLISECOND_DELAY), e -> step(Display.SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

		int i = 0;
		for (CellSociety cellSociety1 : cellSocieties) {
			simulationGroups.add(new SimulationGroup(cellSociety1, animation, i, cellSocieties.size()));
			i++;
		}

		i = 0;
		for (SimulationGroup simGroup : simulationGroups) {
			flow.add(simGroup.getSimulationImage(), i, i);
			i++;
		}

	}

	public void step(double SECOND_DELAY)
	{
		// for (CellSociety society : cellSocieties) {
		// society.getCellGrid().updateCellGrid();
		// }
		// displaySocieties();
	}

	public void displaySocieties()
	{
		// for (AbstractTab abstractTab : abstractTabs) {
		// tabs.getTabs().add(abstractTab.updateTab());
		// }
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}