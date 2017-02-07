package main;

import java.io.File;
import java.util.ResourceBundle;

import cellsociety.CellSociety;
import display.Display;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import rule.Rule;
import xml.XMLParser;

public class Main extends Application
{
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	public static final int FRAMES_PER_SECOND = 4;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	Display display;
	CellSociety cellSociety;

	public static final String DATA_FILE_EXTENSION = "*.xml";
	public static final String LANGUAGE = "English";

	// it is generally accepted behavior that the chooser remembers where user
	// left it last
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);;
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private Stage myStage; 
	private Animation myAnimation;
	
	
	@Override
	public void start(Stage stage) throws Exception
	{	
		myStage = stage;
		File dataFile = myChooser.showOpenDialog(stage);
		if (dataFile != null) {
			Rule rule = new XMLParser().getRule(dataFile);

			cellSociety = new CellSociety(rule);
			display = new Display(stage);
			KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
			Timeline animation = new Timeline();
			myAnimation = animation;
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
		result.setTitle(myResources.getString("OpenFileTitle"));
		// pick a reasonable place to start searching for files
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	public void step(double SECOND_DELAY)
	{	
		myStage.getScene().setOnKeyReleased(e -> {
			try {
				handleKeyReleased(e.getCode());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		display.refreshDisplay();
		cellSociety.step(display);
	}

	private void handleKeyReleased(KeyCode code) throws Exception {
		if (code == KeyCode.S) {
			myAnimation.stop();
		}
		if (code == KeyCode.P){
			myAnimation.pause();
		}
		if (code == KeyCode.B){
			myAnimation.play();
		}
		if (code == KeyCode.F){
			myAnimation.setRate(myAnimation.getCurrentRate()+0.25);
		}
		if (code == KeyCode.D){
			if (myAnimation.getCurrentRate() > 0){
				myAnimation.setRate(myAnimation.getCurrentRate()-0.25);
			}
		}
		if (code == KeyCode.Q){
			step(SECOND_DELAY);
		}
		if (code == KeyCode.N){
			myAnimation.stop();
			start(myStage);
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}