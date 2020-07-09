
package application.Libs;

import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 * Diese Klasse bietet die Grundlage für die Umsetzung des IDW Verfahrens in Java
 * 
 * @author robinkopitz
 * u33874@hs-harz.de
 * Marikelnummer: 26263
 *
 */

public class Idw {
	
	//Basis Arrays
	private double[][] dataPoints;
	private double[] distanzes;
	
	//Max und Min - Werte aus den eingelesenen Daten ermitteln Default
	//Values gesetzt
	private double maxCoordX = -1;
	private double maxCoordY = -1;
	private double maxWeight = -1;
	private double minCoordX = Double.MAX_VALUE;
	private double minCoordY = Double.MAX_VALUE;
	private double minWeight = Double.MAX_VALUE;
	
	//Index Marken für Array handhabung
	private static int INDEX_X = 0;
	private static int INDEX_Y = 1;
	private static int INDEX_WEIGHT = 2;
	
	/**
	 * Setzen der DataPoints und aufspannen des distanzes Arrays
	 * @param dataPoints
	 */
	public Idw(double[][] dataPoints){
		this.dataPoints = dataPoints;
		distanzes = new double[dataPoints.length];
		calculateMaxAndMinCoordAndWeight();
	}
	
	/**
	 * An diesem Array sollen keine Änderungen vorgenommen werden es dient lediglich zur
	 * Berechnung der Werte.
	 * 
	 * @return Kopie des dataPoints Array
	 */	
	public double[][] getDataPoints(){
		return Arrays.copyOf(dataPoints, dataPoints.length);
	}
	
	public double getMaxCoordX() {
		return maxCoordX;
	}
	
	public double getMaxCoordY() {
		return maxCoordY;
	}
	
	public double getMinCoordX() {
		return minCoordX;
	}
	
	public double getMinCoordY() {
		return minCoordY;
	}
	
	public double getMaxWeight() {
		return maxWeight;
	}
	
	public double getMinWeight() {
		return minWeight;
	}
	
	
	/**
	 * ermittelt die Max und Min Werte aus den eingelesenen Daten
	 */
	public void calculateMaxAndMinCoordAndWeight() {
		
		for(double[] point : dataPoints){
			
			//Kalkulation Max Werte
			if(maxCoordX < point[INDEX_X])
				maxCoordX = point[INDEX_X];
			if(maxCoordY < point[INDEX_Y])
				maxCoordY = point[INDEX_Y];
			if(maxWeight < point[INDEX_WEIGHT])
				maxWeight = point[INDEX_WEIGHT];
			
			//Kalkulation Min Werte
			if(minCoordX > point[INDEX_X])
				minCoordX = point[INDEX_X];
			if(minCoordY > point[INDEX_Y])
				minCoordY = point[INDEX_Y];
			if(minWeight > point[INDEX_WEIGHT])
				minWeight = point[INDEX_WEIGHT];
			
		}
	}
	
	/**
	 * Diese Funktion gibt die Gewichtung eines gewünschten Punktes zurück
	 * 
	 * @param x - Koordinate
	 * @param y - Koordinate
	 * @return Gewichtung des Koordinatenpunkts
	 */
	public double getWeightOfCoord(double x, double y) {
		int coordIndex = lookupPointInArray(x, y);
		if( coordIndex != -1)
			return dataPoints[coordIndex][INDEX_WEIGHT];
		
		calculateAllDistanzesToGivenPoint(x, y);
		return calculateWeightingOfPoint();
	}
	
	
	/**
	 * Sucht Daten punkt im Array dataPoints
	 * 
	 * @param x - Koordinate
	 * @param y - Koordinate
	 * @return i oder -1, falls die Koordinate nicht existiert
	 */
	private int lookupPointInArray(double x, double y) {
		for(int i = 0; i < dataPoints.length; i++) {
			if(dataPoints[i][INDEX_X] == x && dataPoints[i][INDEX_Y] == y)
				return i;
		}
		return -1;
	}
	
	/**
	 * ermöglicht die Kalkulation aller Distanzen zwischen dem gewünschten Punkt und den bereits gewichteten Punkten
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return Distanz Array zwischen 2D Punkten
	 */
	private void calculateAllDistanzesToGivenPoint(double x, double y) {
		int index = 0;
		for(double[] point : dataPoints) {
			distanzes[index] = Point2D.distance(point[INDEX_X], point[INDEX_Y], x, y);
			index++;
		}
	}
	
	/**
	 * beschreibt die Formel: new point weighting = (sum( weighting / distanz) / sum ( 1 / weighting))
	 * @return Ausgerechnete / geschätzte Gewichtung des Punkts
	 */
	private double calculateWeightingOfPoint(){
		double sumWeightingDividedByDistanz = 0.0;
		double sumOneDividedByWeighting = 0.0;
		
		for(int i = 0; i < dataPoints.length; i++) {
			sumWeightingDividedByDistanz += (dataPoints[i][INDEX_WEIGHT] / distanzes[i]);
			sumOneDividedByWeighting += (1 / distanzes[i]);
		}
		return sumWeightingDividedByDistanz / sumOneDividedByWeighting;
	}
	
}
