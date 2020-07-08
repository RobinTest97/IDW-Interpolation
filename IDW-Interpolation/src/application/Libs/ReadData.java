package application.Libs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Diese Klasse simuliert die in der Aufgabenstellung beschriebene Klasse zum einlesen der Daten.
 * Sie zählt nicht zur Aufgabe, untersützt aber den hier präsentierten Prototypen
 * 
 * @author robinkopitz
 * u33874@hs-harz.de
 * Marikelnummer: 26263
 *
 */



public class ReadData {
	
	//Read Data wird Static und soll nicht instanziert werden
	private ReadData() {
		
	}
	
	/**
	 * Liest die vom FileChooser übergebene Datei ein und 
	 * wandelt die eingelesenen Daten in ein 2D Array um.
	 * ACHTUNG: funktioniert nur in der vorgegebenen Form.
	 * 
	 * @param file 
	 * @return Eingelesene Datenpunkte
	 * @throws FileNotFoundException 
	 * **/
	public static double[][] readTextFile(File file) throws FileNotFoundException	
	{
		double dataPoints[][];
		
		Scanner stdin = new Scanner(file);
		
		//Größe einlesen um Array größe festzulegen liest x Datensätze mit je 3 Attributen
		int size = 0;
		dataPoints = new double[size = stdin.nextInt()][3];
		
		//Textreihe ausschneiden
		stdin.next();
		stdin.next();
		stdin.next();
		
		//Einlesen der Messwerte und Koordinaten in das erstellte Array
		for(int i = 0; i < size; i++) {
			// Koordinaten und Mess werte in 2D-Array einlesen 
			dataPoints[i][0] = stdin.nextDouble();	//X-Werte
			dataPoints[i][1] = stdin.nextDouble();	//Y-Werte
			dataPoints[i][2] = stdin.nextDouble();	//Messwerte
		}
		
		//Schließen des Scanners nach Verwendung
		stdin.close();
		return dataPoints;
	}

}
