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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView {
	
	private Stage mainStage;
	private HBox mainBox;
	private Tooltip tooltip;
	
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
		tooltip = new Tooltip();
		mainBox = new HBox();		
		mainBox.setAlignment(Pos.CENTER);
		mainBox.getChildren().add(idwImageView);
		setUpTooltipDisplay();
		return mainBox;
	}
	

	/**Top Menu Bar**/
	private Pane setTopElements() {
        VBox vbox = new VBox(22);       
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);

        MenuBar menuBar = new MenuBar();
        Menu menuRead = new Menu(Constants.MENU_NAME);
        MenuItem menuNewData = new MenuItem(Constants.NEW_DATA_INPUT);
        MenuItem menuExit = new MenuItem(Constants.EXIT);
        menuRead.getItems().addAll(menuNewData, menuExit);
        
        Menu menuData = new Menu(Constants.MENU_DATA);
        MenuItem saveData = new MenuItem(Constants.SAVE_DATA);
        menuData.getItems().addAll(saveData);
        
         
        menuBar.getMenus().addAll(menuRead, menuData);
        menuNewData.setOnAction( e->openNewTextData() );
        saveData.setOnAction( e->saveGeneratedImage() );
        menuExit.setOnAction( e->endApplication() );
        vbox.getChildren().add(menuBar);
        return vbox ;
	}//setTopElements
	
    private void setUpTooltipDisplay() {
    	
        // Tooltip beim Betreten des Bildes anzeigen
        idwImageView.setOnMouseEntered(
                e -> tooltip.show(idwImageView, e.getScreenX(), e.getScreenY() + Constants.TOOLTIP_OFFSET));

        // Wert von Position der Maus mittels Tooltip anzeigen
        idwImageView.setOnMouseMoved(event -> {
            tooltip.setAnchorX(event.getScreenX());
            tooltip.setAnchorY(event.getScreenY() + Constants.TOOLTIP_OFFSET);

            int x = (int) event.getX();
            int y = (int) event.getY();
            tooltip.setText(String.format("Wert: %.2f (x: %.0f, y: %.0f)", idwImage.getSpecificWeighting(x, y),
                    x + idwImage.getMinCoordX() - Constants.IMAGE_OFFSET, y + idwImage.getMinCoordY() - Constants.IMAGE_OFFSET));
        });

        // Tooltip verstecken, sobald Bild verlassen wird
        idwImageView.setOnMouseExited(e -> tooltip.hide());
    	
    }

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
				idwImage = new IdwImage(dataPoints, Constants.IMAGE_OFFSET, rgbColors);
				idwImageView.setImage(SwingFXUtils.toFXImage(idwImage.getIdwImage(), null));
				root.setCenter( setCenterElements() );
			} catch (FileNotFoundException e) {
				//TODO: Constants
				ErrorView.errorBox(Constants.ERROR_READ_DATA, Constants.ERROR_TEXT);
			}
			
		}		
	}//openNewTextData
	
	/**
	 * Speichert das Bild unter dem angegebenen Pfad ab
	 */
	private void saveGeneratedImage() {
		if(idwImage == null) {
			ErrorView.errorBox(Constants.ERROR_SAVE_DATA, Constants.ERROR_TEXT);
			return;
		}
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(mainStage);
		//TODO: Controller Case
		if(file != null) {
			try {
				ImageIO.write(idwImage.getIdwImage(), "png", file);
				InfoView.infoBox(Constants.INFO_SAVE_DATA, Constants.INFO_TEXT);
			} catch (IOException e) {
				ErrorView.errorBox(Constants.ERROR_SAVE_DATA , Constants.ERROR_TEXT);
			}
		}
	}

	/** Beenden des Programms**/
	private void endApplication() {
 		Platform.exit();
 		System.exit(0);
	}//endApplication
	
    /** Farbdarstellungsvarianten als Array */
    private int[]              rgbColors      = {                   
            (255 << 24) + (255 << 16) + (255 << 8) + (255),         
            (255 << 24) + (230 << 16) + (236 << 8) + (255),         
            (255 << 24) + (204 << 16) + (217 << 8) + (255),         
            (255 << 24) + (179 << 16) + (198 << 8) + (255),         
            (255 << 24) + (153 << 16) + (179 << 8) + (255),         
            (255 << 24) + (128 << 16) + (159 << 8) + (255),         
            (255 << 24) + (102 << 16) + (140 << 8) + (255),         
            (255 << 24) + (77 << 16) + (121 << 8) + (255),          
            (255 << 24) + (51 << 16) + (102 << 8) + (255),          
            (255 << 24) + (26 << 16) + (83 << 8) + (255),           
            (255 << 24) + (0 << 16) + (64 << 8) + (255),            
            (255 << 24) + (0 << 16) + (57 << 8) + (230),            
            (255 << 24) + (0 << 16) + (51 << 8) + (204),            
            (255 << 24) + (0 << 16) + (45 << 8) + (179)             
    };
    

}
