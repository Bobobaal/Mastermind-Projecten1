package main;

import cui.ConsoleApplicatie;
import domein.DomeinController;

/**
 * De main klasse van de volledige applicatie waar het spel initieel wordt
 * opgestart.
 *
 * @author groep 31
 */
public class StartUp {

    /**
     * De main methode van deze applicatie.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Initalize dc
        DomeinController dc = new DomeinController();

        // Start consoleapplicatie
        new ConsoleApplicatie(dc).startApplicatie();
    }

}
