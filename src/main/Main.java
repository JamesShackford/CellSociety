package main;

import java.io.File;
import java.util.ResourceBundle;

import cellsociety.CellSociety;
import display.Display;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

	// it is generally accepted behavior that the chooser remembers where user
	// left it last
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);

	@Override
	public void start(Stage stage) throws Exception
	{
		File dataFile = myChooser.showOpenDialog(stage);
		if (dataFile != null) {
			Rule rule = new XMLParser().getRule(dataFile);

			cellSociety = new CellSociety(rule);
			display = new Display(stage, cellSociety);
		} else {
			// nothing selected, so quit the application
			Platform.exit();
		}
	}

	// set some sensible defaults when the FileChooser is created
	private FileChooser makeChooser(String extensionAccepted)
	{
		FileChooser result = new FileChooser();
		result.setTitle(myResources.getString("OpenFileTitle"));
		// pick a reasonable place to start searching for files
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
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