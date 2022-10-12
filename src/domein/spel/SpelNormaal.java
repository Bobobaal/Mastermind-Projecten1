package domein.spel;

import domein.PinCode;
import domein.PinEvaluatie;
import domein.rij.RijKraak;
import domein.rij.RijVulbaar;
import java.util.HashMap;
import java.util.Map;
import domein.rij.RijNormaal;

/**
 * Klasse dat een normaal spel voorstelt.
 *
 * @author groep 31
 */
public class SpelNormaal extends Spel {

    /**
     * Maak een nieuw, 'normaal' spel aan.
     *
     */
    public SpelNormaal() {
        super(2, 12, 4, false, false);
    }

    /**
     * Bereken de evaluatiepinnen van een rij op een bepaalde index.
     *
     * @param index De positie van de te evalueren rij
     */
    @Override
    public void berekenEvaluatiePinnen(int index) {
        // Init
        RijVulbaar rij = spelBord.getRij(index);
        RijKraak teKrakenCode = spelBord.getTeKrakenCode();
        Map<PinCode, Integer> onjuisteKleuren = new HashMap<>();
        boolean[] zwartePosities = new boolean[aantalPinnenPerRij]; // False
        int zwart = 0;
        int wit = 0;
        int column = 0;

        // Zwarte pinnen berekenen
        for (int i = 0; i < aantalPinnenPerRij; i++) {
            if (rij.getPinCode(i).equals(teKrakenCode.getPinCode(i))) {
                // Controleren op zwart
                zwart++;
                zwartePosities[i] = true;
            } else {
                // Map van onjuiste onjuisteKleuren maken om de witte te controleren
                if (onjuisteKleuren.containsKey(teKrakenCode.getPinCode(i))) {
                    // Aantal voorkomens van de kleur vermeerderen
                    onjuisteKleuren.put(teKrakenCode.getPinCode(i), onjuisteKleuren.get(teKrakenCode.getPinCode(i)) + 1);
                } else {
                    // Kleur van te kraken code zit er nog niet in ==> in de map steken
                    onjuisteKleuren.put(teKrakenCode.getPinCode(i), 1);
                }
            }
        }

        // Witte pinnen berekenen
        for (int i = 0; i < aantalPinnenPerRij; i++) {
            if (!zwartePosities[i] && onjuisteKleuren.containsKey(rij.getPinCode(i))) {
                // Pin was niet zwart & zit nog in de te kraken code
                wit++;

                if (onjuisteKleuren.get(rij.getPinCode(i)) > 1) {
                    onjuisteKleuren.put(rij.getPinCode(i), onjuisteKleuren.get(rij.getPinCode(i)) - 1);
                } else {
                    onjuisteKleuren.remove(rij.getPinCode(i));
                }
            }
        }

        // Zwarte pinnen uitschrijven
        while (zwart != 0) {
            spelBord.setPinEvaluatie(PinEvaluatie.W, index, column);
            column++;
            zwart--;
        }

        // Witte pinnen uitschrijven
        while (wit != 0) {
            spelBord.setPinEvaluatie(PinEvaluatie.W, index, column);
            column++;
            wit--;
        }
    }

    /**
     * Geef een lege rij terug (factory).
     *
     * @return  Een nieuwe, lege rij
     */
    @Override
    public RijVulbaar rijFactory() {
        return new RijNormaal();
    }

    @Override
    public String toString() {
        // Header opmaken
        String string = "\n==================================================================================\n";
        if (naam != null && !naam.isEmpty()) {
            string += TAAL.getString("naam") + ": " + naam + "\n";
        }
        string += TAAL.getString("moeilijkheidsgraad") + ": " + TAAL.getString("normaal");
        string += "\n" + TAAL.getString("codepinnen") + ": ";
        int i = 0;
        for (String kleur : kleuren) {
            string += TAAL.getString(kleur) + "";
            if (i != kleuren.size() - 1) {
                string += ", ";
            }
            i++;
        }
        string += "\n" + TAAL.getString("evaluatiepinnen") + ": " + TAAL.getString("Z") + " (" + TAAL.getString("ZWART") + "), "
                + TAAL.getString("W") + " (" + TAAL.getString("WIT") + ")";

        // Header + spel afprinten
        return string + super.toString();
    }

}
