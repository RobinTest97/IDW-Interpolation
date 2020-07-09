package application.Libs;

import java.awt.image.BufferedImage;

/**
 * Diese Klasse realisiert die Berechnungen und die Umsetzung des IDW Images
 * 
 * @author robinkopitz
 * u33874@hs-harz.de
 * Marikelnummer: 26263
 *
 */
public class IdwImage extends Idw {
	
	
	private BufferedImage idwImage = null;
	private double[][] weightings = null;
	private int[] rgbColors = null;
	private int imageHeight = 0;
	private int imageWidth = 0;
	private int imageOffset = 0;
	
	public IdwImage(double[][] dataPoints, int imageOffset, int[] rgbColors) {
		super(dataPoints);
		this.imageOffset = imageOffset;
		if(imageOffset < 0)
			imageOffset = 0;
		this.rgbColors = rgbColors;
		calculateImageSizing();
		createBufferedImageWithPreferences();
		calculateWeightingForAllPoints();
		setIdwPixelColors();
	}
	
	
	public BufferedImage getIdwImage() {
		return idwImage;
	}
	
	/**
	 * gibt die berechnete Gewichtung des spezifischen Punkts zurück
	 * 
	 * @param x
	 * @param y
	 * @return gewichtung des übergebenen coord Punkts
	 */
	public double getSpecificWeighting(int x, int y) {
		return weightings[x][y];
	}
	
	       
	/**
	 * 
	 * ermittelt für alle Pixel Werte / Koordinaten Werte das weighting für die weitere Verarbeitung
	 * 
	 * @param idw - Instanz der idw klasse
	 * @param imageWidth
	 * @param imageHeight
	 * @param imageOffset
	 * @return gibt alle weightings zurück
	 */
	private void calculateWeightingForAllPoints(){
		weightings = new double[imageWidth][imageHeight];
		
		for(int x = 0; x < imageWidth; x++){
			for(int y = 0; y < imageHeight; y++) {
				double weightAtPoint = getWeightOfCoord(x + getMinCoordX()+imageOffset
									, y + getMinCoordY());
				weightings[x][y] = weightAtPoint;
			}
		}
	}
	
	/**
	 * bereitet das BufferedImage mit den berechneten Werten vor
	 */
	private void createBufferedImageWithPreferences() {
		idwImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	}
	
	
	/**
	 * berechnet die Bildgröße. Hier wird wirklich nur vom kleinsten xy bis zum größten xy 
	 * aufgespannt (sonst ist das Bild zu groß für den Java Heap und es macht auch keinen Sinn)
	 * 
	 * @param idw
	 * @param imageOffset
	 * @return
	 */
	private void calculateImageSizing() {	
		imageHeight = (int) (getMaxCoordX() - getMinCoordX() + imageOffset);
		imageWidth  = (int) (getMaxCoordY() - getMinCoordY() + imageOffset);
	}
	
	/**
	 * ermittelt die Farbe für jeden Pixel in Abhänigkeit der IDW Gewichtung
	 * und setzt die Pixelfarben im BufferedImage
	 */
	private void setIdwPixelColors() {
		double stepSize = ((getMaxWeight() - getMinWeight())/ rgbColors.length);
		
		for(int x = 0; x < imageWidth; x++){
			for(int y = 0; y < imageHeight; y++) {	
				int colorIndex = (int)((weightings[x][y] - getMinWeight()) / stepSize);				
				idwImage.setRGB(x, y, rgbColors[correctColorIndices(colorIndex)]);
			}
		}	
		
	}
	
	/**
	 * korrigiert die berechneten Werte so das sie im Farbschema liegen (Hilfsfunktion)
	 * @param colorIndex - berechneter Farbwert
	 * @return korrigierer Farbwert
	 */
	private int correctColorIndices(int colorIndex) {
		if(colorIndex >= rgbColors.length)
			colorIndex = rgbColors.length-1;
		if(colorIndex < 0)
			colorIndex = 0;
		return colorIndex;
	}

}
