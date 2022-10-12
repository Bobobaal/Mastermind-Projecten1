package main;

import java.util.ResourceBundle;

/**
 * Klasse dat de ResourceBundle van de applicatie bijhoudt.
 *
 * @author groep 31
 */
public class Taal {

    /**
     * De ResourceBundle waarmee we de taal van de applicatie kunnen kiezen.
     *
     */
    private static ResourceBundle taal;

    /**
     * Geef de taal (als resourcebundle) terug.
     * 
     * @return De taal (als resourcebundle)
     */
    public static ResourceBundle getTaal() {
        return taal;
    }

    /**
     * Verander de taal.
     * 
     * @param taal De taal (als resourcebundle)
     */
    public static void setTaal(ResourceBundle taal) {
        if (taal != null) {
            Taal.taal = taal;
        }
    }

}
