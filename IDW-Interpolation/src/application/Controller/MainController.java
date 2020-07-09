package application.Controller;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.Constants.Constants;
import application.GUI.ErrorView;
import application.GUI.InfoView;
import application.Libs.IdwImage;
import application.Libs.ReadData;

/**
 * steuert sämtliche Kommunikationen mit den Libraries
 * 
 * @author robinkopitz
 * u33874@hs-harz.de
 * Marikelnummer: 26263
 *
 */
public class MainController {
	
	double[][] dataPoints = null;
	IdwImage idwImage = null;

	/**
	 * Liest alle Daten über die zuständige interne Lib ein gibt true bei Erfolg zurück,
	 * sonst false
	 * 
	 * @param file
	 * @return true oder false
	 */
	public boolean readData(File file){ 
		try {
			if(file == null)
				throw new FileNotFoundException();
			dataPoints = ReadData.readTextFile(file);
			return true;
		} catch (FileNotFoundException e) {
			ErrorView.errorBox(Constants.ERROR_READ_DATA , Constants.ERROR_TEXT);
		}
		return false;
	}
	
	/**
	 * started den Idw Prozess und liefert das erstellte Bild an die UI
	 * @return generiertes Buffered Image
	 */
	public BufferedImage generateIdwImage() {
		idwImage = new IdwImage(dataPoints, Constants.IMAGE_OFFSET, Constants.RGB_COLORS);
		return idwImage.getIdwImage();
	}
	
	/**
	 * holt die Daten aus der IdwImage Klasse und generiert einen Ausgabe String
	 * 
	 * @param x - Coord
	 * @param y - Coord
	 * @return Ausgabe String
	 */
	public String gatherAndProcessTooltipData(int x, int y) {
		return String.format("Wert: %.2f (x: %.0f, y: %.0f)", idwImage.getSpecificWeighting(x, y),
                x + idwImage.getMinCoordX() - Constants.IMAGE_OFFSET, y + idwImage.getMinCoordY() - Constants.IMAGE_OFFSET);
	}
	
	/**
	 * speichert das erstellte Bild ab über eine externe Lib
	 * 
	 * @param file
	 * @param idwImage
	 * @return
	 */
	public void saveImage(File file) {
		try {
			if(idwImage == null)
				throw new IOException();
			ImageIO.write(idwImage.getIdwImage(), "png", file);
			InfoView.infoBox(Constants.INFO_SAVE_DATA, Constants.INFO_TEXT);
		} catch (IOException e) {
			ErrorView.errorBox(Constants.ERROR_SAVE_DATA , Constants.ERROR_TEXT);
		}
	}
	
}
