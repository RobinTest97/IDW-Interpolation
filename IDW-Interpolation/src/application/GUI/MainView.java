package application.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.Constants.Constants;
import application.Libs.IdwImage;
import application.Libs.ReadData;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView {
	
	private Stage mainStage;
	private Label xLabel = new Label(Constants.START_X_LABEL_TEXT);
	private Label yLabel = new Label(Constants.START_X_LABEL_TEXT);
	private HBox mainBox;
	
	private IdwImage idwImage = null;
	private ImageView idwImageView = new ImageView();
	private BorderPane root = null;
	
	public MainView(Stage stage) {
		mainStage = stage;
		this.initializeWindowSturucture();
	}
	
	private void initializeWindowSturucture() {
        root = new BorderPane(); 
        
        root.setTop( setTopElements() );
        
        Scene scene= new Scene(root, 1000, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	private Pane setCenterElements() {
		mainBox = new HBox();		
		mainBox.setAlignment(Pos.CENTER);
		//mainBox.getChildren().add(xLabel);
		//mainBox.getChildren().add(yLabel);
		mainBox.getChildren().add(idwImageView);
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
				idwImage = new IdwImage(dataPoints, 100, rgbColors);
				try {
					ImageIO.write(idwImage.getIdwImage(), "png", new File("/Users/robinkopitz/Desktop/test.png"));
					System.out.println("Done");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Das Bild konnte nicht gespeichert werden!");
				}
				idwImageView.setImage(SwingFXUtils.toFXImage(idwImage.getIdwImage(), null));
				root.setCenter( setCenterElements() );
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
	
    /** Enthält RGB Farbwerte zur Darstellung der unterschiedlichen Werte im Bild */
    private int[]              rgbColors      = {                   //
            (255 << 24) + (255 << 16) + (255 << 8) + (255),         // white
            (255 << 24) + (230 << 16) + (236 << 8) + (255),         //
            (255 << 24) + (204 << 16) + (217 << 8) + (255),         //
            (255 << 24) + (179 << 16) + (198 << 8) + (255),         // ...
            (255 << 24) + (153 << 16) + (179 << 8) + (255),         // from
            (255 << 24) + (128 << 16) + (159 << 8) + (255),         // white
            (255 << 24) + (102 << 16) + (140 << 8) + (255),         // to
            (255 << 24) + (77 << 16) + (121 << 8) + (255),          // dark
            (255 << 24) + (51 << 16) + (102 << 8) + (255),          // blue
            (255 << 24) + (26 << 16) + (83 << 8) + (255),           // ...
            (255 << 24) + (0 << 16) + (64 << 8) + (255),            //
            (255 << 24) + (0 << 16) + (57 << 8) + (230),            //
            (255 << 24) + (0 << 16) + (51 << 8) + (204),            //
            (255 << 24) + (0 << 16) + (45 << 8) + (179)             // dark blue
    };
}
