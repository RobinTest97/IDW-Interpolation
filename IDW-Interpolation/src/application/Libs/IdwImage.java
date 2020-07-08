package application.Libs;

/**
 * Diese Klasse ermöglicht die Berechnungen für die Umsetzungen in einem Image
 * 
 * @author robinkopitz
 * u33874@hs-harz.de
 * Marikelnummer: 26263
 *
 */


public class IdwImage {
	
	
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
	public static double[][] calculateWeightingForAllPoints(Idw idw, int imageWidth, int imageHeight, int imageOffset){
		double[][] weightings = new double[imageWidth][imageHeight];
		
		for(int x = 0; x < imageWidth; x++){
			for(int y = 0; y < imageHeight; x++) {
				double weightAtPoint = idw.getWeightOfCoord(x + idw.getMinCoordX()+imageOffset
						, y + idw.getMinCoordY());
				weightings[x][y] = weightAtPoint;
			}
		}
		return weightings;
	}
	
	
	
	

}
