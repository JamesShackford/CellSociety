package exceptions;

import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowExceptions{
	/**
	 * 
	 */
	public static final String LANGUAGE = "English";
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private Alert myAlert;

	public ShowExceptions(){
		myAlert = new Alert(AlertType.ERROR);
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);
	}
	
	public void showAlert(String invalid){
		myAlert.setTitle(myResources.getString(invalid));
		myAlert.setHeaderText(myResources.getString(invalid));
		myAlert.showAndWait();
		Platform.exit();
		System.exit(1);
	}
	
	public void showAlert(String invalid, Animation animation){
		animation.stop();
		showAlert(invalid);
	}
}
