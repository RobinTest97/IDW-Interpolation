package application;
	
import application.GUI.MainView;
import javafx.application.Application;
import javafx.stage.Stage;



public class Start extends Application {
		
	@Override
	public void start(Stage stage) {
		new MainView(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
