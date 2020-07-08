package application.GUI;

import java.io.File;
import java.io.FileNotFoundException;

import application.Constants.Constants;
import application.Libs.ReadData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView {
	
	private Stage mainStage;
	private Label xLabel = new Label(Constants.START_X_LABEL_TEXT);
	private Label yLabel = new Label(Constants.START_X_LABEL_TEXT);
	private VBox mainBox;
	
	
	public MainView(Stage stage) {
		mainStage = stage;
		this.initializeWindowSturucture();
	}
	
	private void initializeWindowSturucture() {
        BorderPane root = new BorderPane(); 
        
        root.setTop( setTopElements() );
        root.setCenter( setCenterElements() );
        
        Scene scene= new Scene(root, 1000, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	private Pane setCenterElements() {
		mainBox = new VBox();		
		mainBox.setAlignment(Pos.CENTER);
		mainBox.getChildren().add(xLabel);
		mainBox.getChildren().add(yLabel);
		activateCoordSystem();
		return mainBox;
	}
	
	private void activateCoordSystem() {
		mainBox.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouse) {
				xLabel.setText(String.valueOf(mouse.getScreenX()));
				yLabel.setText(String.valueOf(mouse.getScreenY()));
			}
		});
	}
	

	/**Top Menu Bar**/
	private Pane setTopElements() {
        VBox vbox = new VBox(22);       
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        MenuBar menuBar = new MenuBar();
        Menu menuDatei = new Menu(Constants.MENU_NAME);
        menuBar.getMenus().add(menuDatei);
        MenuItem menuNewData = new MenuItem(Constants.NEW_DATA_INPUT);
        MenuItem menuExit = new MenuItem(Constants.EXIT);
        menuDatei.getItems().addAll(menuNewData, menuExit);
        menuNewData.setOnAction( e->openNewTextData() );
        menuExit.setOnAction( e->endApplication() );
        vbox.getChildren().add(menuBar);
        return vbox ;
	}//setTopElements

	/** Einlesen der Text Datei
	 * @throws FileNotFoundException **/
	private void openNewTextData() {
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainStage);	
		
		//TODO: Controller Case
		if(file != null){
			try {
				double[][] dataPoints = ReadData.readTextFile(file);
			} catch (FileNotFoundException e) {
				//TODO: Constants
				ErrorView.errorBox("Ein ist ein Fehler beim Laden der Datei aufgetreten", "Error");
			}
			
		}		
	}//openNewTextData

	/** Beenden des Programms**/
	private void endApplication() {
 		Platform.exit();
 		System.exit(0);
	}//endApplication
	
}
