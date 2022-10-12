package domein.rij;

import domein.PinCode;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import main.Taal;

/**
 * Klasse dat een te kraken code voorstelt.
 *
 * @author groep 31
 */
public class RijKraak extends Rij {

    private static ResourceBundle taal = Taal.getTaal();

    /**
     * Maak een nieuwe te kraken code.
     *
     * @param aantalPinnen Het aantal pinnen in deze rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     */
    public RijKraak(int aantalPinnen, boolean leegMag, boolean uniekMoet) {
        super(aantalPinnen, leegMag, uniekMoet);
        maakTeKrakenCode();
    }

    /**
     * Maak een nieuwe te kraken code met een bestaande code.
     *
     * @param aantalPinnen Het aantal pinnen in deze rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     * @param pinCodes De pincodes van de bestaande code
     */
    public RijKraak(int aantalPinnen, boolean leegMag, boolean uniekMoet, PinCode[] pinCodes) {
        super(aantalPinnen, leegMag, uniekMoet);
        this.pinCodes = pinCodes;
    }

    /**
     * Maak een nieuwe, willekeurige te kraken code.
     *
     */
    private void maakTeKrakenCode() {
        // Maak nieuwe RNG
        SecureRandom random = new SecureRandom();

        // Init kleuren
        int mogelijkheden;
        if (leegMag) {
            mogelijkheden = PinCode.AANTAL_PINCODES;
        } else {
            mogelijkheden = PinCode.AANTAL_PINCODES - 1;
        }

        // Maak code met evt dubbele waarden erin
        if (uniekMoet) {
            Set<Integer> codePinnenUniek = new HashSet<>();
            int i = 0;
            while (i < aantalPinnen) {
                int id = random.nextInt(mogelijkheden);
                if (!codePinnenUniek.contains(id)) {
                    pinCodes[i] = PinCode.PINCODES[id];
                    codePinnenUniek.add(id);
                    i++;
                }
            }
        } else {
            // Init
            int aantalLeeg = 0;
            boolean gelukt;

            // Er mogen maar maximaal 2 lege velden zijn
            for (int i = 0; i < aantalPinnen; i++) {
                gelukt = false;
                while (!gelukt) {
                    int id = random.nextInt(mogelijkheden);
                    if (PinCode.PINCODES[id] == PinCode.LEEG) {
                        if (aantalLeeg < 2) {
                            pinCodes[i] = PinCode.PINCODES[id];
                            aantalLeeg++;
                            gelukt = true;
                        }
                    } else {
                        pinCodes[i] = PinCode.PINCODES[id];
                        gelukt = true;
                    }
                }
            }
        }
    }

    /**
     * Kleine adaptatie van de toString methode zodat de te kraken code
     * afgeprint wordt in het Nederlands, zodat deze correct kan opgeslaan
     * worden in de databank bij uitdagingen.
     *
     * @return Nederlandse string representatie van de te kraken code
     */
    public String getString() {
        String string = "";
        int teller = 0;
        for (PinCode pinCode : pinCodes) {
            string += pinCode.name();
            if (teller < pinCodes.length - 1) {
                string += " ";
            }
            teller++;
        }
        return string;
    }

    @Override
    public String toString() {
        String string = "";
        int teller = 0;
        for (PinCode pinCode : pinCodes) {
            string += taal.getString(pinCode.name());
            if (teller < pinCodes.length - 1) {
                string += " ";
            }
            teller++;
        }
        return string;
    }

}
