package main;

import java.io.File;

import cellsociety.CellSociety;
import display.Display;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import rule.Rule;
import xml.XMLParser;

public class Main extends Application
{
	public static final int FRAMES_PER_SECOND = 4;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	Display display;
	CellSociety cellSociety;

	public static final String DATA_FILE_EXTENSION = "*.xml";

	// it is generally accepted behavior that the chooser remembers where user
	// left it last
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);

	@Override
	public void start(Stage stage) throws Exception
	{
		File dataFile = myChooser.showOpenDialog(stage);
		if (dataFile != null) {
			Rule rule = new XMLParser().getRule(dataFile);

			cellSociety = new CellSociety(rule);
			display = new Display(stage);
			KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
			Timeline animation = new Timeline();
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.getKeyFrames().add(frame);
			animation.play();
			// silly trick to select data file multiple times for this demo
			// start(stage);
		} else {
			// nothing selected, so quit the application
			Platform.exit();
		}
	}

	// set some sensible defaults when the FileChooser is created
	private FileChooser makeChooser(String extensionAccepted)
	{
		FileChooser result = new FileChooser();
		result.setTitle("Open Data File");
		// pick a reasonable place to start searching for files
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	public void step(double SECOND_DELAY)
	{
		display.refreshDisplay();
		cellSociety.step(display);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}