package application.Constants;

public class Constants {

	/**
	 * Top Element Hauptmenu
	 */
	public static String NEW_DATA_INPUT = "Neue Punkt Daten laden";
	public static String MENU_NAME = "Einlesen";
	public static String EXIT = "Programm beenden";
	public static String MENU_DATA = "Datei";
	public static String SAVE_DATA = "Bild speichern";
	
	/**
	 * Fehlermeldungen 
	 */
	public static String ERROR_TEXT = "Error";
	public static String ERROR_READ_DATA = "Ein ist ein Fehler beim Laden der Datei aufgetreten";
	public static String ERROR_SAVE_DATA = "Eine Speicherung ist derzeit nicht möglich";
	
	/**
	 * Infomeldungen
	 */
	public static String INFO_TEXT = "Info";
	public static String INFO_SAVE_DATA = "Die Datei wurde gepspeichert";
	
	
	/**
	 * Offsets
	 */
	public static int IMAGE_OFFSET = 1000;
	public static int TOOLTIP_OFFSET = 20;
	
    /** Farbdarstellungsvarianten als Array */
    public static int[]              RGB_COLORS      = {                   
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


