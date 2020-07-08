package application.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

public class ErrorView {

	 public static void errorBox( String message, String title) {
         Dialog<?> alert = new Alert(Alert.AlertType.ERROR,message);
         alert.setTitle(title);
         alert.setHeaderText(title);
         alert.setResizable(true);
         alert.show();
      } // ErrorBox
	
	
}
