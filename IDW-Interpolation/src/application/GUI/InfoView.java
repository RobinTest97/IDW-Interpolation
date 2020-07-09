package application.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

public class InfoView {

	 public static void infoBox( String message, String title) {
	     Dialog<?> alert = new Alert(Alert.AlertType.INFORMATION,message);
	     alert.setTitle(title);
	     alert.setHeaderText(title);
	     alert.setResizable(true);
	     alert.show();
	  } // infoBox
	
}
