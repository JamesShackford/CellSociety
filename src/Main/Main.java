package Main;

import Display.Display;
import cellsociety.CellSociety;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{
	public static final int FRAMES_PER_SECOND = 4;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

	Display display;
	CellSociety cellSociety;

	@Override
	public void start(Stage stage) throws Exception
	{
		cellSociety = new CellSociety();
		display = new Display(stage);
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
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